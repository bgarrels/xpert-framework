package com.xpert.maker;

/**
 *
 * @author Ayslan
 */
public class Bean {

    private Class entity;
    private BeanType beanType;
    private PackageInfo packageInfo;
    private String author;
    private String resourceBundle;
    
    public Bean(Class entity, BeanType beanType) {
        this.entity = entity;
        this.beanType = beanType;
    }
    
    public String getNameLower() {
        String name = getName();
        if (name != null && name.length() > 2) {
            return name.substring(0, 2).toLowerCase() + "" + name.substring(2, name.length());
        }
        return "";
    }

    public String getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(String resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    
    public BeanType getBeanType() {
        return beanType;
    }

    public void setBeanType(BeanType beanType) {
        this.beanType = beanType;
    }
    
    public Class getEntity() {
        return entity;
    }

    public void setEntity(Class entity) {
        this.entity = entity;
    }

    public String getName() {
        if (entity != null) {
            return entity.getSimpleName();
        }
        return null;
    }
}
