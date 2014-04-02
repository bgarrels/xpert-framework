package com.xpert.faces.component.searchentity;

import com.xpert.persistence.query.RestrictionType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayslan
 */
public class EntitySearchField implements Serializable {
    
    private String propertyName;
    private String property;
    private String options;
    private RestrictionType restrictionType;
    private Object value;
    private boolean required;
    private String requiredMessage;
    private List filterOptions;
    private String filterOptionsLabel;

    public EntitySearchField() {
    }
    

    public EntitySearchField(String propertyName, String property, String options, boolean required, String requiredMessage, List filterOptions, String filterOptionsLabel) {
        this.propertyName = propertyName;
        this.property = property;
        this.options = options;
        this.required = required;
        this.requiredMessage = requiredMessage;
        this.filterOptions = filterOptions;
        this.filterOptionsLabel = filterOptionsLabel;
    }
    
    public List<RestrictionType> getRestrictionTypes() {
        if (options != null) {
            List<RestrictionType> types = new ArrayList<RestrictionType>();
            for (String s : options.split(",")) {
                RestrictionType restrictionType = RestrictionType.getByAcronym(s);
                if(restrictionType != null){
                    types.add(restrictionType);
                }
            }
            return types;
        }
        return null;
    }

    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    public String getProperty() {
        return property;
    }
    
    public void setProperty(String property) {
        this.property = property;
    }
    
    public String getOptions() {
        return options;
    }
    
    public void setOptions(String options) {
        this.options = options;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getRequiredMessage() {
        return requiredMessage;
    }

    public void setRequiredMessage(String requiredMessage) {
        this.requiredMessage = requiredMessage;
    }

    public List getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(List filterOptions) {
        this.filterOptions = filterOptions;
    }

    public String getFilterOptionsLabel() {
        return filterOptionsLabel;
    }

    public void setFilterOptionsLabel(String filterOptionsLabel) {
        this.filterOptionsLabel = filterOptionsLabel;
    }
    
    
    
}
