package com.xpert.faces.bean;

import com.xpert.Configuration;
import com.xpert.faces.utils.FacesUtils;
import com.xpert.maker.MappedBean;
import com.xpert.maker.PackageInfo;
import com.xpert.maker.PersistenceMappedBean;
import com.xpert.maker.BeanCreator;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@SessionScoped
public class BeanMaker implements Serializable {

    private static final SimpleDateFormat ZIP_NAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH'h'-mm'm'");
    private static final Logger logger = Logger.getLogger(BeanMaker.class.getName());
    private EntityManager entityManager;
    private List<MappedBean> mappedBeans;
    private List<Class> classes;
    private List<String> nameSelectedClasses = new ArrayList<String>();
    private PersistenceMappedBean persistenceMappedBean;
    private PackageInfo packageInfo = new PackageInfo();
    private String author;
    private String resourceBundle;
    private String classBean;

    @PostConstruct
    public void init() {
        entityManager = Configuration.getEntityManager();
        persistenceMappedBean = new PersistenceMappedBean(entityManager);
        classes = persistenceMappedBean.getMappedClasses();
        classBean = persistenceMappedBean.getClassBean("");
    }

    public void make() {
        List<Class> selectedClasses = new ArrayList<Class>();
        for (String className : nameSelectedClasses) {
            try {
                selectedClasses.add(Class.forName(className, true, Thread.currentThread().getContextClassLoader()));
            } catch (ClassNotFoundException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        classBean = persistenceMappedBean.getClassBean(packageInfo.getManagedBean());
        mappedBeans = persistenceMappedBean.getMappedBeans(selectedClasses, packageInfo, author, resourceBundle);
    }

    public void makeAll() {
        classBean = persistenceMappedBean.getClassBean(packageInfo.getManagedBean());
        mappedBeans = persistenceMappedBean.getMappedBeans(packageInfo, author, resourceBundle);
        for (Class clazz : classes) {
            nameSelectedClasses.add(clazz.getName());
        }
    }

    public void download() {
        try {
            FacesUtils.download(BeanCreator.createBeanZipFile(mappedBeans, classBean), "application/zip", getDownloadFileName());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getMessage(), null));
        }
    }

    public String getDownloadFileName() {
        return "beans_" + ZIP_NAME_DATE_FORMAT.format(new Date()) + ".zip";
    }

    public void reset() {
        nameSelectedClasses = new ArrayList<String>();
        mappedBeans = new ArrayList<MappedBean>();
    }

    public String getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(String resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public List<String> getNameSelectedClasses() {
        return nameSelectedClasses;
    }

    public void setNameSelectedClasses(List<String> nameSelectedClasses) {
        this.nameSelectedClasses = nameSelectedClasses;
    }

    public List<MappedBean> getMappedBeans() {
        return mappedBeans;
    }

    public void setMappedBeans(List<MappedBean> mappedBeans) {
        this.mappedBeans = mappedBeans;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getClassBean() {
        return classBean;
    }

    public void setClassBean(String classBean) {
        this.classBean = classBean;
    }
}
