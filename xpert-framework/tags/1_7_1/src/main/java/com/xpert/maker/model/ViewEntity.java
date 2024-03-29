package com.xpert.maker.model;

import com.xpert.faces.primefaces.PrimeFacesUtils;
import com.xpert.faces.primefaces.PrimeFacesVersion;
import com.xpert.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayslan
 */
public class ViewEntity {

    private String name;
    private String idFieldName;
    private PrimeFacesVersion primeFacesVersion;
    private List<ViewField> fields = new ArrayList<ViewField>();

    public ViewEntity() {
        primeFacesVersion = PrimeFacesVersion.VERSION_3;
    }

    
    
    public String getAppendTo(){
        if(primeFacesVersion.isUseAppendTo()){
            return "appendTo=\"@(body)\"";
        }else{
            return "appendToBody=\"true\"";
        }
    }
    
    public String getWidgetVarDetailName() {
        return "widget" + name + "Detail";
    }
    
    public String getWidgetVarDataTableName() {
        return "dataTable" + name + "Widget";
    }
    
    public String getWidgetVarDetail() {
        String widgetVar = getWidgetVarDetailName();
        if (primeFacesVersion != null) {
            widgetVar = PrimeFacesUtils.normalizeWidgetVar(widgetVar, primeFacesVersion);
        }
        return widgetVar;
    }

    public String getWidgetVarDataTable() {
        String widgetVar = getWidgetVarDataTableName();
        if (primeFacesVersion != null) {
            widgetVar = PrimeFacesUtils.normalizeWidgetVar(widgetVar, primeFacesVersion);
        }
        return widgetVar;
    }

    public String getNameLower() {
        if (name != null) {
            return StringUtils.getLowerFirstLetter(name);
        }
        return name;
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ViewField> getFields() {
        return fields;
    }

    public void setFields(List<ViewField> fields) {
        this.fields = fields;
    }

    public PrimeFacesVersion getPrimeFacesVersion() {
        return primeFacesVersion;
    }

    public void setPrimeFacesVersion(PrimeFacesVersion primeFacesVersion) {
        this.primeFacesVersion = primeFacesVersion;
    }

    
}
