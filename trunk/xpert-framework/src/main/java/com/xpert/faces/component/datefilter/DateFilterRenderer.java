package com.xpert.faces.component.datefilter;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author ayslan
 */
public class DateFilterRenderer extends Renderer {

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        DateFilter dateFilter = (DateFilter) component;

        String clientId = dateFilter.getClientId(context);
        String startCalendarValue = (String) context.getExternalContext().getRequestParameterMap().get(clientId+"_calendar-start_input");
        String endCalendarValue = (String) context.getExternalContext().getRequestParameterMap().get(clientId+"_calendar-end_input");
        
        dateFilter.setCalendarStartValue(startCalendarValue);
        dateFilter.setCalendarEndValue(endCalendarValue);
        System.out.println("startCalendarValue: "+startCalendarValue);
        System.out.println("endCalendarValue: "+endCalendarValue);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        DateFilter.addDateFilterChildren(component, context);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
