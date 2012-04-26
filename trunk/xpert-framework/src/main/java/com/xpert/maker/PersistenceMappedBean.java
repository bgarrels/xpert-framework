/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.maker;

import com.xpert.maker.BeanCreator;
import com.xpert.utils.HumaniseCamelCase;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

/**
 *
 * @author Ayslan
 */
public class PersistenceMappedBean {

    private static final Logger logger = Logger.getLogger(PersistenceMappedBean.class.getName());
    private Session session;
    private EntityManager entityManager;

    public PersistenceMappedBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<MappedBean> getMappedBeans(List<Class> classes, PackageInfo packageInfo, String author, String resourceBundle) {
        List<MappedBean> mappedBeans = new ArrayList<MappedBean>();
        for (Class clazz : classes) {
            try {
                MappedBean mappedBean = new MappedBean(clazz);
                mappedBean.setI18n(getI18N(clazz));
                mappedBean.setManagedBean(BeanCreator.createBean(new Bean(clazz, BeanType.MANAGED_BEAN), packageInfo, author, resourceBundle));
                mappedBean.setBusinnesObject(BeanCreator.createBean(new Bean(clazz, BeanType.BUSINESS_OBJECT), packageInfo, author, resourceBundle));
                mappedBean.setDao(BeanCreator.createBean(new Bean(clazz, BeanType.DAO), packageInfo, author, resourceBundle));
                mappedBean.setDaoImpl(BeanCreator.createBean(new Bean(clazz, BeanType.DAO_IMPL), packageInfo, author, resourceBundle));
                mappedBean.setView(BeanCreator.createBean(new Bean(clazz, BeanType.VIEW), packageInfo, author, resourceBundle));
                mappedBeans.add(mappedBean);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            } catch (TemplateException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        sortMappedBean(mappedBeans);
        return mappedBeans;
    }

    public void sortMappedBean(List<MappedBean> mappedBeans) {
        Collections.sort(mappedBeans, new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((MappedBean) o1).getClass().getName().compareTo(((MappedBean) o2).getClass().getName());
            }
        });
    }

    public void sortClass(List<Class> classes) {
        Collections.sort(classes, new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Class) o1).getName().compareTo(((Class) o2).getName());
            }
        });
    }

    public List<MappedBean> getMappedBeans(PackageInfo packageInfo, String author, String resourceBundle) {
        return getMappedBeans(getMappedClasses(), packageInfo, author, resourceBundle);
    }

    public List<Class> getMappedClasses() {
        return getMappedClasses(false);
    }

    public List<Class> getMappedClasses(boolean includeEnum) {
        SessionFactory sessionFactory = getSessionFactory();
        Map<String, ClassMetadata> map = (Map<String, ClassMetadata>) sessionFactory.getAllClassMetadata();
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
        List<Class> classes = new ArrayList<Class>();
        for (String entityName : map.keySet()) {
            Class entity = ((AbstractEntityPersister) sessionFactoryImpl.getEntityPersister(entityName)).getConcreteProxyClass();
            classes.add(entity);
            //add enum
            if (includeEnum) {
                for (Field field : entity.getDeclaredFields()) {
                    if (field.getType().isEnum() && !classes.contains(field.getType())) {
                        classes.add(field.getType());
                    }
                }
            }
        }
        sortClass(classes);
        return classes;
    }

    public String getClassBean(String beanPackage) {

        try {

            Template template = BeanCreator.getTemplate("class-bean.ftl");
            StringWriter writer = new StringWriter();
            Map attributes = new HashMap();
            attributes.put("classes", getMappedClasses(true));
            attributes.put("package", beanPackage == null ? "" : beanPackage);
            template.process(attributes, writer);

            writer.flush();
            writer.close();

            return writer.toString();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (TemplateException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return "";

    }

    public String getI18N(Class clazz) {

        Field[] fields = clazz.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        String className = getLowerFirstLetter(clazz.getSimpleName());
        builder.append("\n\n#").append(clazz.getSimpleName());
        builder.append("\n").append(className).append("=").append(new HumaniseCamelCase().humanise(clazz.getSimpleName()));
        boolean first = false;
        for (Field field : fields) {
            if (!first) {
                builder.append("\n");
            } else {
                first = true;
            }
            builder.append(className);
            builder.append(".");
            builder.append(field.getName());
            builder.append("=");
            builder.append(new HumaniseCamelCase().humanise(field.getName()));
        }


        return builder.toString();

    }

    public static String getLowerFirstLetter(String string) {
        if (string.length() == 1) {
            return string.toLowerCase();
        }
        if (string.length() > 1) {
            return string.substring(0, 1).toLowerCase() + "" + string.substring(1, string.length());
        }
        return "";
    }

    public SessionFactory getSessionFactory() {
        return getSession().getSessionFactory();
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
}
