/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.maker;

/**
 *
 * @author Ayslan
 */
public class BeanConfiguration {

    private String managedBean;
    private String businessObject;
    private String dao;
    private String daoImpl;
    private String baseDAO;
    private String author;
    private String resourceBundle;
    private String template;
    private String viewPath;
    
    //location attributes
    private String managedBeanLocation;
    private String businessObjectLocation;
    private String daoLocation;
    private String daoImplLocation;
    private String viewLocation;

    public String getManagedBeanLocation() {
        return managedBeanLocation;
    }

    public void setManagedBeanLocation(String managedBeanLocation) {
        this.managedBeanLocation = managedBeanLocation;
    }

    public String getBusinessObjectLocation() {
        return businessObjectLocation;
    }

    public void setBusinessObjectLocation(String businessObjectLocation) {
        this.businessObjectLocation = businessObjectLocation;
    }

    public String getDaoLocation() {
        return daoLocation;
    }

    public void setDaoLocation(String daoLocation) {
        this.daoLocation = daoLocation;
    }

    public String getDaoImplLocation() {
        return daoImplLocation;
    }

    public void setDaoImplLocation(String daoImplLocation) {
        this.daoImplLocation = daoImplLocation;
    }

    public String getViewLocation() {
        return viewLocation;
    }

    public void setViewLocation(String viewLocation) {
        this.viewLocation = viewLocation;
    }
    

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public String getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(String baseDAO) {
        this.baseDAO = baseDAO;
    }

    public String getBaseDAOSimpleName() {
        if (baseDAO != null && baseDAO.lastIndexOf(".") > -1) {
            return baseDAO.substring(baseDAO.lastIndexOf(".") + 1, baseDAO.length());
        }
        return baseDAO;
    }

    public String getBaseDAOPackage() {
        if (baseDAO != null && baseDAO.lastIndexOf(".") > -1) {
            return baseDAO.substring(0, baseDAO.lastIndexOf("."));
        }
        return "";
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(String resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(String businessObject) {
        this.businessObject = businessObject;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }

    public String getDaoImpl() {
        return daoImpl;
    }

    public void setDaoImpl(String daoImpl) {
        this.daoImpl = daoImpl;
    }

    public String getManagedBean() {
        return managedBean;
    }

    public void setManagedBean(String managedBean) {
        this.managedBean = managedBean;
    }
}
