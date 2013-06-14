package com.xpert.faces.component.datefilter;

import com.xpert.i18n.I18N;
import com.xpert.i18n.XpertResourceBundle;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author ayslan
 */
public class DateFilterRenderer extends Renderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

        DateFilter dateFilter = (DateFilter) component;
        if (component.getParent() != null) {

            String dateFormat = I18N.getDatePattern();

            Calendar calendarStart = new Calendar();
            calendarStart.setStyleClass("calendar-filter calendar-filter-start");
            calendarStart.setNavigator(true);
            calendarStart.setShowOn("button");
            calendarStart.setShowButtonPanel(true);
            calendarStart.setPattern(dateFormat);

            Calendar calendarEnd = new Calendar();
            calendarEnd.setStyleClass("calendar-filter calendar-filter-end");
            calendarEnd.setNavigator(true);
            calendarEnd.setShowOn("button");
            calendarEnd.setShowButtonPanel(true);
            calendarEnd.setPattern(dateFormat);

            Column column = (Column) component.getParent().getParent();
            column.setFilterStyle("display: none;");
            DataTable dataTable = (DataTable) column.getParent();
            String widgetVar = dataTable.resolveWidgetVar();

            AjaxBehavior ajaxBehavior = new AjaxBehavior();
            ajaxBehavior.setProcess(":" + dataTable.getClientId());
            ajaxBehavior.setUpdate(":" + dataTable.getClientId());

            String filterScript = "Xpert.dateFilter('" + calendarEnd.getClientId() + "');" + widgetVar + ".filter(); return false;";
            ajaxBehavior.setOnstart(filterScript);

            calendarStart.addClientBehavior("dateSelect", ajaxBehavior);
            calendarEnd.addClientBehavior("dateSelect", ajaxBehavior);

            HtmlOutputLabel htmlOutputLabelFrom = new HtmlOutputLabel();
            htmlOutputLabelFrom.setValue(XpertResourceBundle.get("from"));

            HtmlOutputLabel htmlOutputLabelTo = new HtmlOutputLabel();
            htmlOutputLabelTo.setValue(XpertResourceBundle.get("to"));

            HtmlPanelGrid panelGrid = new HtmlPanelGrid();
            panelGrid.setColumns(2);
            panelGrid.getChildren().add(htmlOutputLabelFrom);
            panelGrid.getChildren().add(calendarStart);
            panelGrid.getChildren().add(htmlOutputLabelTo);
            panelGrid.getChildren().add(calendarEnd);
            component.getParent().getChildren().add(panelGrid);


//            HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
//            htmlOutputLabel.setValue("teste teste");
//            ResponseWriter writer = context.getResponseWriter();
//            writer.startElement("span", dateFilter);
//            writer.writeAttribute("class", "span-filter", "class");
//            writer.endElement("span");
//            dateFilter.getChildren().add(htmlOutputLabel);
        }

    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
