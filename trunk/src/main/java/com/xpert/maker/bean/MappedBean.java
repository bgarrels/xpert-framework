package com.xpert.maker.bean;

/**
 *
 * @author Ayslan
 */
public class MappedBean {
    
    private Class entityClass;
    private String className;
    private String i18n;
    private String managedBean;
    private String businnesObject;
    private String dao;
    private String daoImpl;
    private String view;

    public MappedBean() {
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
    
    public String getDaoImpl() {
        return daoImpl;
    }

    public void setDaoImpl(String daoImpl) {
        this.daoImpl = daoImpl;
    }
    
    public String getBusinnesObject() {
        return businnesObject;
    }

    public void setBusinnesObject(String businnesObject) {
        this.businnesObject = businnesObject;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }
    
    public String getManagedBean() {
        return managedBean;
    }

    public void setManagedBean(String managedBean) {
        this.managedBean = managedBean;
    }
    
    
    public MappedBean(Class entityClass) {
        this.entityClass = entityClass;
    }
    
    public String getClassName() {
        if(entityClass != null){
            return entityClass.getName();
        }
        return className;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }
    
}
