package com.xpert.faces.component.searchentity;

import com.xpert.persistence.query.RestrictionType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayslan
 */
public class EntitySearchOrder implements Serializable {
    
    private String orderBy;
    private String propertyName;

    public EntitySearchOrder() {
    }

    public EntitySearchOrder(String orderBy, String propertyName) {
        this.orderBy = orderBy;
        this.propertyName = propertyName;
    }
    

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    
    
}
