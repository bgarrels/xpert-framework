package com.xpert.persistence.dao;

import com.xpert.persistence.exception.DeleteException;
import com.xpert.persistence.query.QueryBuilder;
import com.xpert.persistence.query.Restriction;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;

public interface BaseDAO<T> {

    public EntityManager getEntityManager();

    public Session getSession();

    public QueryBuilder getQueryBuilder();

    public Query getNativeQueryFromFile(String path, Class daoClass, Class resultClass);

    public Query getNativeQueryFromFile(String path, Class daoClass);

    public Class getEntityClass();

    public void setEntityClass(Class entityClass);

    public void save(T object);

    public void save(T object, boolean audit);

    public void saveOrUpdate(T object);

    public void saveOrUpdate(T object, boolean audit);

    public void update(T object);

    public void update(T object, boolean audit);

    public void delete(Object id) throws DeleteException;

    public void delete(Object id, boolean audit) throws DeleteException;

    public void remove(Object object) throws DeleteException;

    public void remove(Object object, boolean audit) throws DeleteException;

    public T merge(T object);

    public T merge(T object, boolean audit);

    public List<T> listAll();

    public List<T> listAll(String order);

    public List<T> listAll(Class clazz, String order);

    public Long count();

    public Long count(Class clazz);

    public Long count(Map<String, Object> parameters);

    public Long count(List<Restriction> restrictions);

    public Long count(Restriction restriction);

    public Long count(String property, Object value);

    public T find(Object id);

    public T find(Class entityClass, Object id);

    public T unique(Map<String, Object> args);

    public T unique(List<Restriction> restrictions);

    public T unique(List<Restriction> restrictions, Class clazz);

    public T unique(Restriction restriction);

    public T unique(Restriction restriction, Class clazz);

    public T unique(String property, Object value);

    public Object findAttribute(String attributeName, Long id);

    public Object findAttribute(String attributeName, Object object);

    public List<T> list(Map<String, Object> parameters);

    public List<T> list(Map<String, Object> parameters, String order);

    public List<T> list(List<Restriction> restrictions);

    public List<T> list(Restriction restriction);

    public List<T> list(Class clazz, List<Restriction> restrictions);

    public List<T> list(List<Restriction> restrictions, String order);

    public List<T> list(Class clazz, Restriction restriction);

    public List<T> list(Restriction restriction, String order);

    public List<T> list(Class clazz, List<Restriction> restrictions, String order);

    public List<T> list(Class clazz, Restriction restriction, String order);

    public List<T> list(Map<String, Object> args, String order, Integer firstResult, Integer maxResults);

    public List<T> list(Class clazz, Restriction restriction, String order, Integer firstResult, Integer maxResults);

    public List<T> list(Class clazz, Restriction restriction, String order, Integer firstResult, Integer maxResults, String attributes);

    public List<T> list(Restriction restrictions, String order, Integer firstResult, Integer maxResults);

    public List<T> list(Class clazz, List<Restriction> restrictions, String order, Integer firstResult, Integer maxResults);

    public List<T> list(Class clazz, List<Restriction> restrictions, String order, Integer firstResult, Integer maxResults, String attributes);

    public List<T> list(List<Restriction> restrictions, String order, Integer firstResult, Integer maxResults);

    public List<T> list(String property, Object value);
    
    public List<T> list(String property, Object value, String order);

    public Object getInitialized(Object object);

    public List<T> listAttributes(String attributes);

    public List<T> listAttributes(String attributes, String order);

    public List<T> listAttributes(String property, Object value, String attributes);
    
    public List<T> listAttributes(String property, Object value, String attributes, String order);

    public List<T> listAttributes(Map<String, Object> args, String attributes, String order);

    public List<T> listAttributes(Map<String, Object> args, String attributes);

    public List<T> listAttributes(List<Restriction> restrictions, String attributes, String order);

    public List<T> listAttributes(List<Restriction> restrictions, String attributes);

    public List<T> listAttributes(Restriction restriction, String attributes, String order);

    public List<T> listAttributes(Restriction restriction, String attributes);
}
