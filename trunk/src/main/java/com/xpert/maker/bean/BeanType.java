package com.xpert.maker.bean;

/**
 *
 * @author Ayslan
 */
public enum BeanType {

    MANAGED_BEAN("managed-bean.ftl", "java"),
    BUSINESS_OBJECT("business-object.ftl", "java"),
    VIEW("view.ftl", "xhtml"),
    DAO("dao.ftl", "java"),
    DAO_IMPL("dao-impl.ftl", "java");
    
    private String template;
    private String extension;

    private BeanType(String template, String extension) {
        this.template = template;
        this.extension = extension;
    }

    public String getTemplate() {
        return template;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
