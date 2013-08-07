/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.faces.component.initializer;

import com.xpert.faces.bean.InitializerBean;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;

/**
 *
 * @author ayslan
 */
public class InitializerEventListener implements ComponentSystemEventListener {

    private Boolean initializeOnlyWhenRendered;
    private String property;
    private ValueExpression valueExpression;
    private FaceletContext faceletContext;
    private UIComponent parent;

    public InitializerEventListener() {
    }

    public InitializerEventListener(Boolean initializeOnlyWhenRendered, String property, ValueExpression valueExpression, FaceletContext faceletContext, UIComponent parent) {
        this.initializeOnlyWhenRendered = initializeOnlyWhenRendered;
        this.property = property;
        this.valueExpression = valueExpression;
        this.faceletContext = faceletContext;
        this.parent = parent;
    }

    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        if(parent == null || faceletContext == null){
            return;
        }
        InitializerBean initializerBean = new InitializerBean();
        if (initializeOnlyWhenRendered == null) {
            initializeOnlyWhenRendered = false;
        }

        if (initializeOnlyWhenRendered == false || (initializeOnlyWhenRendered == true && parent.isRendered())) {
            if (valueExpression == null) {
                initializerBean.initialize(parent, faceletContext.getFacesContext(), property);
            } else {
                initializerBean.initialize(parent, faceletContext.getFacesContext(), valueExpression);
            }
        }
    }
}
