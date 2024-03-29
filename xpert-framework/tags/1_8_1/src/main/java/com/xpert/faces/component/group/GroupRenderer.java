package com.xpert.faces.component.group;

import com.xpert.faces.component.renderkit.CoreRenderer;
import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author ayslan
 */
public class GroupRenderer extends CoreRenderer {

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        //Do Nothing
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

        Group group = (Group) component;

        int rowCount = group.getRowCount();

        String rowIndexVar = group.getRowIndexVar();
        Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

        for (int i = 0; i < rowCount; i++) {
            if (rowIndexVar != null) {
                requestMap.put(rowIndexVar, i);
            }
            group.setRowIndex(i);
            if (rowIndexVar != null) {
                requestMap.put(rowIndexVar, i);
            }
            if (group.isRowAvailable()) {
                renderChildren(context, group);
            }
        }

        //cleanup
        group.setRowIndex(-1);

        if (rowIndexVar != null) {
            requestMap.remove(rowIndexVar);
        }

    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

}
