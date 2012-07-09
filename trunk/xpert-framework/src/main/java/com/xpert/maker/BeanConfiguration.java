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
