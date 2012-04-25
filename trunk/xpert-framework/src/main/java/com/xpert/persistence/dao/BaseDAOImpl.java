package com.xpert.persistence.dao;

import com.xpert.audit.Audit;
import com.xpert.configuration.Configuration;
import com.xpert.persistence.exception.DeleteException;
import com.xpert.persistence.query.QueryBuilderOld;
import com.xpert.persistence.query.QueryBuilder;
import com.xpert.persistence.query.QueryType;
import com.xpert.persistence.query.Restriction;
import com.xpert.persistence.utils.EntityUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public abstract class BaseDAOImpl<T> implements BaseDAO<T> {

    private Class entityClass;
    private EntityManager entityManager;
    private Session session;
    private static final Logger logger = Logger.getLogger(BaseDAOImpl.class.getName());

    /**
     * Set here your EntityManager
     */
    public abstract void init();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public BaseDAOImpl() {
        try {
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (genericSuperclass != null && genericSuperclass instanceof ParameterizedType) {
                Type[] arguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                if (arguments != null && arguments.length > 0) {
                    Object object = arguments[0];
                    if (object instanceof Class<?>) {
                        entityClass = (Class<T>) object;
                    } else {
                        if (object instanceof Class) {
                            entityClass = (Class) object;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Class getEntityClass() {
        return entityClass;
    }

    @Override
    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
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

    @Override
    public QueryBuilder getQueryBuilder() {
        return new QueryBuilder(entityManager);
    }

    @Override
    public Query getNativeQueryFromFile(String path, Class daoClass, Class resultClass) {
        return QueryBuilderOld.createNativeQueryFromFile(entityManager, path, daoClass, resultClass);
    }

    @Override
    public Query getNativeQueryFromFile(String path, Class daoClass) {
        return QueryBuilderOld.createNativeQueryFromFile(entityManager, path, daoClass);
    }

    private Audit getNewAudit() {
        return new Audit(getEntityManager());
    }

    @Override
    public void save(T object) {
        save(object, Configuration.isAudit());
    }

    @Override
    public void save(T object, boolean audit) {
        getSession().save(object);
        if (audit) {
            getNewAudit().insert(object);
        }
    }

    @Override
    public void update(T object) {
        update(object, Configuration.isAudit());
    }

    @Override
    public void update(T object, boolean audit) {
        if (audit) {
            getNewAudit().update(object);
        }
        getSession().update(object);
    }

    @Override
    public void saveOrUpdate(T object) {
        saveOrUpdate(object, Configuration.isAudit());
    }

    @Override
    public void saveOrUpdate(T object, boolean audit) {
        boolean persisted = EntityUtils.getId(object) != null;
        if (persisted && audit) {
            getNewAudit().update(object);
        }
        getSession().saveOrUpdate(object);

        if (!persisted && audit) {
            getNewAudit().insert(object);
        }
    }

    @Override
    public T merge(T object) {
        return (T) getSession().merge(object);
    }

    @Override
    public void delete(Object id) throws DeleteException {
        delete(id, true);
    }

    @Override
    public void delete(Object id, boolean audit) throws DeleteException {

        try {

            if (audit) {
                getNewAudit().delete(id, entityClass);
            }

            Query query = getEntityManager().createQuery("DELETE FROM " + entityClass.getName() + " WHERE " + EntityUtils.getIdFieldName(entityClass) + " = ? ");
            query.setParameter(1, id);
            query.executeUpdate();
        } catch (Throwable t) {
            throw new DeleteException("Object from class " + getEntityClass() + " with ID: " + id + " cannot be deleted");
        }

    }

    @Override
    public T find(Object id) {
        return (T) getEntityManager().find(entityClass, id);
    }

    @Override
    public T find(Class entityClass, Object id) {
        return (T) getEntityManager().find(entityClass, id);
    }

    @Override
    public List<T> listAll() {
        return listAll(null);
    }

    @Override
    public List<T> listAll(String order) {
        return listAll(entityClass, order);
    }

    @Override
    public List<T> listAll(Class clazz, String order) {
        Query query = new QueryBuilder(entityManager).from(clazz).orderBy(order).createQuery();
        return query.getResultList();
    }

    @Override
    public Object findAttribute(String attributeName, Long id) {

        Query query = new QueryBuilder(entityManager).from(entityClass).type(QueryType.SELECT, attributeName).createQuery();

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Object findAttribute(String attributeName, Object object) {
        return findAttribute(attributeName, EntityUtils.getId(object));
    }

    @Override
    public T unique(Map<String, Object> args) {
        Query query = new QueryBuilder(entityManager).from(entityClass).add(args).createQuery();
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public T unique(Restriction restriction) {
        return unique(getRestrictions(restriction), entityClass);
    }

    @Override
    public T unique(List<Restriction> restrictions) {
        return unique(restrictions, entityClass);
    }

    @Override
    public T unique(Restriction restrictions, Class clazz) {
        return unique(getRestrictions(restrictions), clazz);
    }

    @Override
    public T unique(List<Restriction> restrictions, Class clazz) {
        Query query = new QueryBuilder(entityManager).from(clazz).add(restrictions).createQuery();
        query.setMaxResults(1);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<T> list(Map<String, Object> restrictions, String order) {
        return list(restrictions, order, null, null);
    }

    @Override
    public List<T> list(Map<String, Object> args) {
        return list(args, null);
    }

    @Override
    public List<T> list(Map<String, Object> restrictions, String order, Integer firstResult, Integer maxResults) {
        Query query = new QueryBuilder(entityManager).from(entityClass).add(restrictions).createQuery();

        if (firstResult != null) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            query.setMaxResults(maxResults);
        }

        return query.getResultList();
    }

    @Override
    public List<T> list(Class clazz, List<Restriction> restrictions) {
        return list(clazz, restrictions, null);
    }

    @Override
    public List<T> list(List<Restriction> restrictions) {
        return list(entityClass, restrictions);
    }

    @Override
    public List<T> list(Class clazz, Restriction restriction) {
        return list(clazz, getRestrictions(restriction), null);
    }

    @Override
    public List<T> list(Restriction restriction) {
        return list(entityClass, getRestrictions(restriction));
    }

    @Override
    public List<T> listAttributes(String attributes) {
        return listAttributes((String) null, attributes);
    }

    @Override
    public List<T> listAttributes(String attributes, String order) {
        return list(entityClass, (List) null, order, null, null, attributes);
    }

    @Override
    public List<T> listAttributes(Map<String, Object> args, String attributes, String order) {
        return list(entityClass, getRestrictionsFromMap(args), order, null, null, attributes);
    }

    @Override
    public List<T> listAttributes(Map<String, Object> args, String attributes) {
        return list(entityClass, getRestrictionsFromMap(args), null, null, null, attributes);
    }

    @Override
    public List<T> listAttributes(List<Restriction> restrictions, String attributes, String order) {
        return list(entityClass, restrictions, order, null, null, attributes);
    }

    @Override
    public List<T> listAttributes(List<Restriction> restrictions, String attributes) {
        return list(entityClass, restrictions, null, null, null, attributes);
    }

    @Override
    public List<T> listAttributes(Restriction restriction, String attributes, String order) {
        return list(entityClass, getRestrictions(restriction), order, null, null, attributes);
    }

    @Override
    public List<T> listAttributes(Restriction restriction, String attributes) {
        return list(entityClass, getRestrictions(restriction), null, null, null, attributes);
    }

    @Override
    public List<T> list(List<Restriction> restrictions, String order, Integer firstResult, Integer maxResults) {
        return list(entityClass, restrictions, order, firstResult, maxResults);
    }

    @Override
    public List<T> list(Class clazz, List<Restriction> restrictions, String order, Integer firstResult, Integer maxResults) {
        return list(clazz, restrictions, order, firstResult, maxResults, null);
    }

    @Override
    public List<T> list(Restriction restriction, String order, Integer firstResult, Integer maxResults) {
        return list(entityClass, getRestrictions(restriction), order, firstResult, maxResults);
    }

    @Override
    public List<T> list(Class clazz, Restriction restriction, String order, Integer firstResult, Integer maxResults) {
        return list(clazz, getRestrictions(restriction), order, firstResult, maxResults, null);
    }

    @Override
    public List<T> list(List<Restriction> restrictions, String order) {
        return list(entityClass, restrictions, order);
    }

    @Override
    public List<T> list(Class clazz, List<Restriction> restrictions, String order) {
        return list(clazz, restrictions, order, null, null);
    }

    @Override
    public List<T> list(Restriction restriction, String order) {
        return list(entityClass, getRestrictions(restriction), order);
    }

    @Override
    public List<T> list(Class clazz, Restriction restriction, String order) {
        return list(clazz, getRestrictions(restriction), order, null, null);
    }

    @Override
    public List<T> list(Class clazz, Restriction restriction, String order, Integer firstResult, Integer maxResults, String attributes) {
        return list(clazz, getRestrictions(restriction), order, firstResult, maxResults, attributes);
    }

    @Override
    public List<T> list(Class clazz, List<Restriction> restrictions, String order, Integer firstResult, Integer maxResults, String attributes) {

        QueryBuilder queryBuilder = new QueryBuilder(entityManager).from(clazz).type(QueryType.SELECT, attributes).orderBy(order).add(restrictions);

        Query query = queryBuilder.createQuery();
        if (maxResults != null) {
            query.setMaxResults(maxResults);
        }
        if (firstResult != null) {
            query.setFirstResult(firstResult);
        }

        if (attributes != null && attributes.split(",").length > 0) {
            List<Object> objects = query.getResultList();
            List result = new ArrayList();
            String[] fields = attributes.split(",");
            for (Object object : objects) {
                try {
                    Object entity = clazz.newInstance();
                    for (int i = 0; i < fields.length; i++) {
                        String property = fields[i].trim().replaceAll("/s", "");
                        initializeCascade(property, entity);
                        if (object instanceof Object[]) {
                            PropertyUtils.setProperty(entity, property, ((Object[]) object)[i]);
                        } else {
                            PropertyUtils.setProperty(entity, property, object);
                        }
                    }
                    result.add(entity);
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
            return result;
        }

        return query.getResultList();
    }

    public void initializeCascade(String property, Object bean) {
        int index = property.indexOf(".");
        if (index > -1) {
            try {
                String field = property.substring(0, property.indexOf("."));
                Object propertyToInitialize = PropertyUtils.getPropertyDescriptor(bean, field).getPropertyType().newInstance();
                PropertyUtils.setProperty(bean, field, propertyToInitialize);
                String afterField = property.substring(index + 1, property.length());
                if (afterField != null && afterField.indexOf(".") > -1) {
                    initializeCascade(afterField, propertyToInitialize);
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Long count(Map<String, Object> restrictions) {
        Query query = new QueryBuilder(entityManager).from(entityClass).type(QueryType.COUNT).add(restrictions).createQuery();
        try {
            return (Long) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Long count(Restriction restriction) {
        return count(getRestrictions(restriction));
    }

    @Override
    public Long count(List<Restriction> restrictions) {
        Query query = new QueryBuilder(entityManager).from(entityClass).type(QueryType.COUNT).add(restrictions).createQuery();
        try {
            return (Long) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Long count() {
        return count(entityClass);
    }

    @Override
    public Long count(Class clazz) {
        return count((List) null);
    }

    @Override
    public Object getInitialized(Object object) {
        if (object != null) {
            if (object instanceof HibernateProxy || object instanceof PersistentBag || object instanceof PersistentSet) {

                if (object instanceof HibernateProxy) {

                    LazyInitializer lazyInitializer = ((HibernateProxy) object).getHibernateLazyInitializer();

                    if (Hibernate.isInitialized(object)) {
                        return lazyInitializer.getImplementation();
                    }
                    return getEntityManager().find(lazyInitializer.getPersistentClass(), lazyInitializer.getIdentifier());
                }

                if (object instanceof PersistentCollection) {

                    String role = ((PersistentCollection) object).getRole();
                    Object owner = ((PersistentCollection) object).getOwner();

                    Collection collection = null;
                    if (object instanceof PersistentBag) {
                        collection = new ArrayList();
                        if (Hibernate.isInitialized(object)) {
                            collection.addAll((PersistentBag) object);
                            return collection;
                        }
                    }
                    if (object instanceof PersistentSet) {
                        collection = new HashSet();
                        if (Hibernate.isInitialized(object)) {
                            collection.addAll((PersistentSet) object);
                            return collection;
                        }
                    }
                    StringBuilder queryString = new StringBuilder();
                    queryString.append(" SELECT ").append(role.substring(role.lastIndexOf(".") + 1, role.length()));
                    queryString.append(" FROM ").append(owner.getClass().getName()).append(" o ");
                    queryString.append(" WHERE o = ?1 ");

                    Query query = getEntityManager().createQuery(queryString.toString());
                    query.setParameter(1, owner);

                    collection.addAll(query.getResultList());

                    return collection;
                }

            }
        }
        return object;
    }

    private List<Restriction> getRestrictionsFromMap(Map<String, Object> args) {
        List<Restriction> restrictions = new ArrayList<Restriction>();
        for (Entry e : args.entrySet()) {
            restrictions.add(new Restriction(e.getKey().toString(), e.getValue()));
        }
        return restrictions;
    }

    private List<Restriction> getRestrictions(Restriction restriction) {
        List<Restriction> restrictions = null;
        if (restriction != null) {
            restrictions = new ArrayList<Restriction>();
            restrictions.add(restriction);
        }
        return restrictions;
    }
}
