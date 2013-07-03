package com.xpert.faces.component.datefilter;

import com.xpert.i18n.I18N;
import com.xpert.i18n.XpertResourceBundle;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.calendar.CalendarRenderer;
import org.primefaces.component.column.Column;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.commandbutton.CommandButtonRenderer;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.util.ComponentUtils;

/**
 *
 * @author ayslan
 */
public class DateFilter extends HtmlPanelGroup {

    private static final String COMPONENT_FAMILY = "com.xpert.component";
    private boolean added;
    private Object calendarStartValue;
    private Object calendarEndValue;

    public DateFilter() {
    }

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

    public Object getCalendarStartValue() {
        return calendarStartValue;
    }

    public void setCalendarStartValue(Object calendarStartValue) {
        this.calendarStartValue = calendarStartValue;
    }

    public Object getCalendarEndValue() {
        return calendarEndValue;
    }

    public void setCalendarEndValue(Object calendarEndValue) {
        this.calendarEndValue = calendarEndValue;
    }
}
