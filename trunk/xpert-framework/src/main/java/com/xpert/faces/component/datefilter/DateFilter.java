package com.xpert.faces.component.datefilter;

import com.xpert.faces.component.security.*;
import javax.faces.component.UIComponentBase;

/**
 *
 * @author ayslan
 */
public class DateFilter extends UIComponentBase {

    private static final String COMPONENT_FAMILY = "com.xpert.component";
    private boolean added;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
    
    protected enum PropertyKeys {

        value;
        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        @Override
        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getValue() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(String _value) {
        getStateHelper().put(PropertyKeys.value, _value);
    }
}
