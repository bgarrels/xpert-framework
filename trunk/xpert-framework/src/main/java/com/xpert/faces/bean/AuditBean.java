package com.xpert.faces.bean;

import com.xpert.DAO;
import com.xpert.faces.primefaces.LazyDataModelImpl;
import com.xpert.audit.Audit;
import com.xpert.audit.model.AbstractAuditing;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.query.JoinBuilder;
import com.xpert.persistence.query.Restriction;
import com.xpert.persistence.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
    private LazyDataModel<AbstractAuditing> auditings;
    private BaseDAO baseDAO;

    @PostConstruct
    public void init() {
        try {
            baseDAO = new DAO(Audit.getAuditingClass());
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void detail(Object object) {
        this.object = object;
        detail();
    }

    public void detail() {

        if (object != null) {
            Object id = EntityUtils.getId(object);
            if (id != null) {
                auditings = new LazyDataModelImpl<AbstractAuditing>("eventDate DESC", getRestrictions(id), baseDAO, new JoinBuilder("a").leftJoin("a.metadatas"));
            }
        }

    }

    private List<Restriction> getRestrictions(Object id) {
        List<Restriction> restrictions = new ArrayList<Restriction>();

        restrictions.add(new Restriction("identifier", id));
        restrictions.add(new Restriction("entity", object.getClass().getName()));

        return restrictions;
    }

    public boolean isPersisted(Object object) {
        if (EntityUtils.getId(object) != null) {
            return true;
        }
        return false;
    }

    public LazyDataModel<AbstractAuditing> getAuditings() {
        return auditings;
    }

    public void setAuditings(LazyDataModel<AbstractAuditing> auditings) {
        this.auditings = auditings;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
