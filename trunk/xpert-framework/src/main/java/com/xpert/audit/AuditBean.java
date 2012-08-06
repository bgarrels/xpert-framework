package com.xpert.audit;

import com.xpert.DAO;
import com.xpert.Configuration;
import com.xpert.audit.model.AbstractAuditing;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.utils.EntityUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@ViewScoped
public class AuditBean {

    private static final Logger logger = Logger.getLogger(AuditBean.class.getName());
    private Object object;
    private Map<BeanModel, DetailAuditBean> beans = new HashMap<BeanModel, DetailAuditBean>();
    private BaseDAO baseDAO;

    @PostConstruct
    public void init() {
        baseDAO = new DAO(Configuration.AUDITING_IMPL);
    }
    
    public void detail(Object object){
        this.object = object;
        detail();
    }

    public void detail() {
        BeanModel beanModel = getBeanModel(object);
        DetailAuditBean detail = beans.get(beanModel);
        if (detail == null && beanModel != null) {
            detail = new DetailAuditBean(beanModel, baseDAO);
            detail.load();
            beans.put(beanModel, detail);
        }
    }

    private BeanModel getBeanModel(Object object) {
        if (object == null) {
            return null;
        }
        Object id = null;
        String entity = null;
        if (object instanceof AbstractAuditing) {
            id = ((AbstractAuditing) object).getIdentifier();
            entity = ((AbstractAuditing) object).getEntity();
        } else {
            id = EntityUtils.getId(object);
            entity = Audit.getEntityName(object.getClass());
        }
        return new BeanModel(id, entity);
    }

    public DetailAuditBean getDetailAuditBean(Object object) {
        System.out.println("object; " + object);
        BeanModel beanModel = getBeanModel(object);

        if (beanModel != null) {
            System.out.println("beanModel != null");
            DetailAuditBean detail = getBeans().get(beanModel);
            if (detail != null) {
                return detail;
            }
        }
        System.out.println("return null");
        //evict nullpointer
        return new DetailAuditBean();
    }

    public LazyDataModel<AbstractAuditing> getAuditing(Object object) {
        System.out.println("object; " + object);
        BeanModel beanModel = getBeanModel(object);

        if (beanModel != null) {
            System.out.println("beanModel != null");
            DetailAuditBean detail = getBeans().get(beanModel);
            if (detail != null) {
                System.out.println("detail != null");
                return detail.getAuditings();

            }
        }
        System.out.println("return null");
        return null;
    }

    public boolean isPersisted(Object object) {
        if (EntityUtils.getId(object) != null) {
            return true;
        }
        return false;
    }

    public Map<BeanModel, DetailAuditBean> getBeans() {
        return beans;
    }

    public void setBeans(Map<BeanModel, DetailAuditBean> beans) {
        this.beans = beans;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
