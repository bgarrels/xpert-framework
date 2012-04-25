package com.xpert.audit;

import com.xpert.audit.model.AbstractAuditing;
import com.xpert.audit.model.AbstractMetadata;
import com.xpert.audit.model.AuditingType;
import com.xpert.Configuration;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author Ayslan
 */
public class Audit {

    private static final Logger logger = Logger.getLogger(Audit.class.getName());
    private static final String[] EXCLUDED_FIELDS = {"notifyAll", "notify", "getClass", "wait", "hashCode", "toString", "equals"};
    private static final SimpleDateFormat AUDIT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Session session;
    private EntityManager entityManager;

    public Audit(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Object getId(Object object) {
        if (object == null) {
            return null;
        }
        return getAnnotadedWithId(object, object.getClass());
    }

    public Object getAnnotadedWithId(Object object, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    return field.get(object);
                }
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(Id.class)) {
                    return method.invoke(object);
                }
            }
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
                return getAnnotadedWithId(object, clazz.getSuperclass());
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object getPersisted(Object object) {
        Object id = getId(object);
        if (id != null) {
            return entityManager.find(object.getClass(), id);
        }
        return null;
    }

    public Object getPersistedById(Object id, Class clazz) {
        if (id != null) {
            return entityManager.find(clazz, id);
        }
        return null;
    }

    public String getMethodName(Method method) {
        if (method.getName().startsWith("is")) {
            return method.getName().substring(2, 3).toLowerCase() + method.getName().substring(3);
        } else if (method.getName().startsWith("get")) {
            return method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
        }
        return null;
    }

    public Session getSession() {
        if (session == null) {
            if (entityManager.getDelegate() instanceof EntityManagerImpl) {
                EntityManagerImpl entityManagerImpl = (EntityManagerImpl) entityManager.getDelegate();
                return entityManagerImpl.getSession();
            } else {
                return (Session) entityManager.getDelegate();
            }
        } else {
            return session;
        }
    }

    public SessionFactory getSessionFactory() {
        return getSession().getSessionFactory();
    }

    public void insert(Object object) {
        audit(object, null, AuditingType.INSERT);
    }

    public void update(Object object) {
        Object persisted = getPersisted(object);
        if (persisted != null) {
            audit(object, persisted, AuditingType.UPDATE);
            entityManager.detach(persisted);
        } else {
            logger.log(Level.SEVERE, "Entity passed to update in Xpert Audit, is a transient instance");
        }
    }

    public void delete(Object id, Class clazz) {
        audit(getPersistedById(id, clazz), null, AuditingType.DELETE);
    }

    public void audit(Object object, Object persisted, AuditingType auditingType) {

        try {

            if (isEntity(object)) {

                Field[] fields = object.getClass().getDeclaredFields();
                Method[] methods = object.getClass().getDeclaredMethods();
                Method.setAccessible(methods, true);
                Field.setAccessible(fields, true);

                AbstractAuditing auditing = Configuration.getAbstractAuditing();
                auditing.setIdentifier(Long.valueOf(getId(object).toString()));
                auditing.setEntity(object.getClass().getName());
                auditing.setAuditingType(auditingType);
                auditing.setEventDate(new Date());
                
                AbstractAuditingListener listener = Configuration.getAuditingListener();
                if(listener != null){
                    listener.onSave(auditing);
                }

                List<AbstractMetadata> metadatas = null;
                if (auditingType.equals(AuditingType.INSERT) || auditingType.equals(AuditingType.DELETE)) {
                    entityManager.persist(auditing);
                    metadatas = getMetadata(object, null, auditing);
                } else if (auditingType.equals(AuditingType.UPDATE)) {
                    metadatas = getMetadata(object, persisted, auditing);
                    if (metadatas != null && !metadatas.isEmpty()) {
                        entityManager.persist(auditing);
                    }
                }

                if (metadatas != null && !metadatas.isEmpty()) {
                    for (AbstractMetadata metadata : metadatas) {
                        entityManager.persist(metadata);
                    }
                }

            }
        } catch (Throwable t) {
            logger.log(Level.SEVERE, t.getMessage(), t);
        }

    }

    public List<AbstractMetadata> getMetadata(Object object, Object persisted, AbstractAuditing auditing) throws Exception {
        ArrayList<Method> methodsGet = getMethods(object);
        List<AbstractMetadata> metadatas = new ArrayList<AbstractMetadata>();
        for (Method method : methodsGet) {
            try {
                Object fieldValue = method.invoke(object);
                Object fieldOld = null;
                if (persisted != null) {
                    fieldOld = method.invoke(persisted);
                }
                AbstractMetadata metadata = Configuration.getAbstractMetadata();
                if (fieldValue != null && fieldValue.getClass().isAnnotationPresent(Embeddable.class)) {
                    List<AbstractMetadata> embedableMetadata = getMetadata(fieldValue, persisted, auditing);
                    if (embedableMetadata != null && embedableMetadata.isEmpty()) {
                        metadatas.addAll(embedableMetadata);
                    }
                } else {
                    boolean addMetadata = persisted == null;
                    if (fieldValue instanceof Collection) { //para as coleções
                        Collection collectionNew = (Collection<Object>) fieldValue;
                        Collection collectionOld = (Collection<Object>) fieldOld;
                        StringBuilder newValue = new StringBuilder();
                        if (fieldOld == null) {
                            for (Object item : collectionNew) {
                                newValue.append(item.toString()).append("; ");
                            }
                            addMetadata = true;
                        } else {
                            StringBuilder oldValue = new StringBuilder();
                            if (!(collectionNew instanceof PersistentBag) && !(collectionNew instanceof PersistentCollection)) {
                                if ((collectionNew == null && collectionOld != null) || (collectionNew != null && collectionOld == null) || (collectionNew.size() != collectionOld.size())) {
                                    addMetadata = true;
                                } else {
                                    for (Object current : collectionNew) {
                                        if (collectionOld != null && !collectionOld.contains(current)) {
                                            addMetadata = true;
                                            break;
                                        }
                                    }
                                }
                                for (Object old : collectionOld) {
                                    oldValue.append(old).append("; ");
                                }
                                for (Object item : collectionNew) {
                                    newValue.append(item.toString()).append("; ");
                                }
                                metadata.setOldValue(oldValue.toString());
                            }
                        }
                        metadata.setNewValue(newValue.toString());
                    } else if (isEntity(method.getReturnType())) {
                        Object newId = getId(fieldValue);
                        if (!(fieldValue instanceof HibernateProxy)) {
                            Object oldId = null;
                            if (fieldOld instanceof HibernateProxy) {
                                oldId = ((HibernateProxy) fieldOld).getHibernateLazyInitializer().getIdentifier();
                            } else {
                                oldId = getId(fieldOld);
                            }
                            metadata.setOldIdentifier(oldId == null ? null : Long.valueOf(oldId.toString()));
                            metadata.setOldValue(fieldOld == null ? "" : fieldOld.toString());
                            if ((oldId == null && newId != null) || (oldId != null && newId == null) || (oldId != null && !oldId.equals(newId))) {
                                addMetadata = true;
                            }
                        }
                        metadata.setEntity(method.getDeclaringClass().getName());
                        metadata.setNewIdentifier(newId == null ? null : Long.valueOf(newId.toString()));
                        metadata.setNewValue(fieldValue == null ? "" : fieldValue.toString());
                    } else {
                        if (fieldOld != null) {
                            metadata.setOldValue(getToString(fieldOld));
                        }
                        if (fieldValue != null) {
                            metadata.setNewValue(getToString(fieldValue));
                        }
                        if ((fieldOld == null && fieldValue != null) || (fieldOld != null && fieldValue == null) || (fieldOld != null && !fieldOld.equals(fieldValue))) {
                            addMetadata = true;
                        }
                    }
                    metadata.setField(getMethodName(method));
                    metadata.setAuditing(auditing);
                    if (addMetadata) {
                        metadatas.add(metadata);
                    }
                }
            } catch (Throwable t) {
                logger.log(Level.SEVERE, t.getMessage(), t);
            }
        }
        return metadatas;
    }

    private String getToString(Object object) {
        if (object instanceof Date || object instanceof Calendar) {
            return AUDIT_DATE_FORMAT.format(object);
        } else {
            return object.toString();
        }
    }

    public ArrayList<Method> getMethods(Object objeto) {

        ArrayList<Method> methodGet = new ArrayList<Method>();
        Method methods[] = objeto.getClass().getMethods();

        List exclude = Arrays.asList(EXCLUDED_FIELDS);

        try {
            Field field;
            String fieldName;
            for (int j = 0; j < methods.length; j++) {
                field = null;
                fieldName = "";
                try {
                    if (methods[j] != null && !methods[j].isAnnotationPresent(Transient.class)
                            && !methods[j].isAnnotationPresent(NotAudited.class)
                            && !methods[j].isAnnotationPresent(Id.class) && !exclude.contains(methods[j].getName())) {
                        fieldName = getMethodName(methods[j]);
                    }
                    if (fieldName != null && !fieldName.equals("")) {
                        field = getDeclaredField(objeto.getClass(), fieldName);
                        if (field != null && !field.isAnnotationPresent(Transient.class) && !field.isAnnotationPresent(NotAudited.class)
                                && !field.isAnnotationPresent(Id.class)) {
                            methodGet.add(methods[j]);
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return methodGet;
    }

    public Field getDeclaredField(Class clazz, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        if (field == null && clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
            return getDeclaredField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    /**
     * Verifica se o objeto passado é uma entidade de persistencia
     *
     * @param objeto
     * @return boolean
     */
    public static boolean isEntity(Object objeto) {
        return isEntity(objeto.getClass());
    }

    /**
     * Verifica se o objeto passado é uma entidade de persistencia
     *
     * @param objeto
     * @return boolean
     */
    public static boolean isEntity(Class clazz) {
        if (clazz.isAnnotationPresent(Entity.class)) {
            return true;
        }
        return false;
    }
}
