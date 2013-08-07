package com.xpert.faces.component.initializer;

import com.xpert.faces.bean.InitializerBean;
import java.io.IOException;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

/**
 *
 * @author ayslan
 */
public class InitializerHandler extends TagHandler {

    protected final TagAttribute initializeOnlyWhenRendered;
    protected final TagAttribute property;
    protected final TagAttribute value;

    public InitializerHandler(ComponentConfig config) {
        super(config);
        this.value = this.getAttribute("value");
        this.initializeOnlyWhenRendered = this.getAttribute("initializeOnlyWhenRendered");
        this.property = this.getAttribute("property");
    }

    public InitializerHandler(TagConfig config) {
        super(config);
        this.value = this.getAttribute("value");
        this.initializeOnlyWhenRendered = this.getAttribute("initializeOnlyWhenRendered");
        this.property = this.getAttribute("property");
    }

    public void apply(final FaceletContext faceletContext, final UIComponent parent) throws IOException {
        InitializerEventListener initializerEventListener = new InitializerEventListener(isInitializeOnlyWhenRendered(faceletContext),
                getProperty(faceletContext), getValueExpression(faceletContext), faceletContext, parent);
        parent.subscribeToEvent(PreRenderComponentEvent.class, initializerEventListener);
    }

    public Boolean isInitializeOnlyWhenRendered(FaceletContext faceletContext) {
        if (initializeOnlyWhenRendered != null) {
            ValueExpression valueExpression = initializeOnlyWhenRendered.getValueExpression(faceletContext, Boolean.class);
            if (valueExpression != null) {
                return (Boolean) valueExpression.getValue(faceletContext);

            }
        }
        return null;
    }

    public String getProperty(FaceletContext faceletContext) {
        if (property != null) {
            ValueExpression valueExpression = property.getValueExpression(faceletContext, String.class);
            if (valueExpression != null) {
                return (String) valueExpression.getValue(faceletContext);

            }
        }
        return null;
    }

    public ValueExpression getValueExpression(FaceletContext faceletContext) {
        if (value != null) {
            return value.getValueExpression(faceletContext, Object.class);
        }
        return null;
    }
}
