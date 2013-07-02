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

    public static void addDateFilterChildren(UIComponent component, FacesContext context) throws IOException {

        DateFilter dateFilter = (DateFilter) component;
        String dateFormat = I18N.getDatePattern();

        Calendar calendarStart = new Calendar();
        calendarStart.setStyleClass("calendar-filter calendar-filter-start");
        calendarStart.setNavigator(true);
        calendarStart.setShowOn("button");
        calendarStart.setShowButtonPanel(true);
        calendarStart.setPattern(dateFormat);
        calendarStart.setReadonly(true);

            System.out.println("dateFilter.getCalendarStartValue(): "+dateFilter.getCalendarStartValue());
        if (dateFilter.getCalendarStartValue() != null) {
            calendarStart.setSubmittedValue(dateFilter.getCalendarStartValue());
        }

        Calendar calendarEnd = new Calendar();
        calendarEnd.setStyleClass("calendar-filter calendar-filter-end");
        calendarEnd.setNavigator(true);
        calendarEnd.setShowOn("button");
        calendarEnd.setShowButtonPanel(true);
        calendarEnd.setPattern(dateFormat);
        calendarEnd.setReadonly(true);
        
           calendarStart.setId(component.getId() + "_calendar-start");
        calendarEnd.setId(component.getId() + "_calendar-end");

        if (dateFilter.getCalendarEndValue() != null) {
            calendarEnd.setSubmittedValue(dateFilter.getCalendarEndValue());
        }

        Column column = (Column) component.getParent().getParent();
        // column.setFilterStyle("display: none;");
        DataTable dataTable = (DataTable) column.getParent();
        String widgetVar = dataTable.resolveWidgetVar();

        AjaxBehavior ajaxBehavior = new AjaxBehavior();
        ajaxBehavior.setProcess(":" + dataTable.getClientId());
        ajaxBehavior.setUpdate(":" + dataTable.getClientId());

        String filterScript = "Xpert.dateFilter('" + calendarEnd.getClientId() + "');" + widgetVar + ".filter(); return false;";
        ajaxBehavior.setOnstart(filterScript);

        calendarStart.addClientBehavior("dateSelect", ajaxBehavior);
        calendarEnd.addClientBehavior("dateSelect", ajaxBehavior);

        calendarStart.setParent(component);
        calendarEnd.setParent(component);

        //writer response
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("table", component);
        //first line
        writer.startElement("tr", component);
        //first column
        writer.startElement("td", component);
        writer.write(XpertResourceBundle.get("from"));
        writer.endElement("td");
        //second column
        writer.startElement("td", component);

        CalendarRenderer calendarRenderer = new CalendarRenderer();
        calendarRenderer.encodeEnd(context, calendarStart);

        writer.endElement("td");
        writer.endElement("tr");
        //second line
        writer.startElement("tr", component);
        //first column
        writer.startElement("td", component);
        writer.write(XpertResourceBundle.get("to"));
        writer.endElement("td");
        //second column

        writer.startElement("td", component);
        calendarRenderer = new CalendarRenderer();
            calendarStart.setId(component.getId() + "_calendar-end");
        calendarRenderer.encodeEnd(context, calendarStart);
        writer.endElement("td");
        writer.endElement("tr");
        writer.endElement("table");
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        //writer.write("$(function(){");
        //writer.write("var calendarValue = $(this).closest('th').find('.ui-column-filter').val(); alert(calendarValue); ");
        //writer.write("});");

        writer.endElement("script");
        //clear parent and prevent to add to tree
        calendarStart.setParent(null);
        calendarEnd.setParent(null);

     



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
