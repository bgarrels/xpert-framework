package com.xpert.audit.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Ayslan
 */
@MappedSuperclass
public abstract class AbstractMetadata {

    private String field;
    private String oldValue;
    private String newValue;
    private String entity;
    private Long newIdentifier;
    private Long oldIdentifier;

    public abstract Object getId();

    public abstract AbstractAuditing getAuditing();

    public abstract void setAuditing(AbstractAuditing auditing);

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getNewIdentifier() {
        return newIdentifier;
    }

    public void setNewIdentifier(Long newIdentifier) {
        this.newIdentifier = newIdentifier;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Long getOldIdentifier() {
        return oldIdentifier;
    }

    public void setOldIdentifier(Long oldIdentifier) {
        this.oldIdentifier = oldIdentifier;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
}
