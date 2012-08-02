package com.xpert.faces.bean;

import com.xpert.Configuration;
import com.xpert.DAO;
import com.xpert.audit.Audit;
import com.xpert.audit.model.AbstractAuditing;
import com.xpert.audit.model.AbstractMetadata;
import com.xpert.audit.model.AuditingType;
import com.xpert.faces.primefaces.LazyDataModelImpl;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.query.JoinBuilder;
import com.xpert.persistence.query.Restriction;
import java.util.ArrayList;
import java.util.List;
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
public class AuditDeleteBean {

    private Class entity;
    private LazyDataModel<AbstractAuditing> auditings;
    private BaseDAO baseDAO;

    @PostConstruct
    public void init() {
        baseDAO = new DAO(Configuration.AUDITING_IMPL);
    }

    public void load(Class entity) {
        this.entity = entity;
        load();
    }

    public void load() {
        if (entity != null) {
            List<Restriction> restrictions = new ArrayList<Restriction>();
            restrictions.add(new Restriction("entity", Audit.getEntityName(entity)));
            restrictions.add(new Restriction("auditingType", AuditingType.DELETE));
            auditings = new LazyDataModelImpl<AbstractAuditing>("eventDate DESC", restrictions, baseDAO, new JoinBuilder("a").leftJoin("a.metadatas"));
        }
    }

    public String getObjectDescripton(List<AbstractMetadata> metadatas) {
        StringBuilder builder = new StringBuilder();

        if (metadatas != null && !metadatas.isEmpty()) {
            builder.append("[");
            boolean comma = false;
            for (AbstractMetadata metadata : metadatas) {
                if (comma) {
                    builder.append(", ");
                }
                builder.append("<b>").append(metadata.getField());
                builder.append(": ").append("</b>");
                builder.append(metadata.getNewValue());
                if (comma == false) {
                    comma = true;
                }
            }
            builder.append("[");
        }

        return builder.toString();
    }

    public Class getEntity() {
        return entity;
    }

    public void setEntity(Class entity) {
        this.entity = entity;
    }

    public LazyDataModel<AbstractAuditing> getAuditings() {
        return auditings;
    }

    public void setAuditings(LazyDataModel<AbstractAuditing> auditings) {
        this.auditings = auditings;
    }
}
