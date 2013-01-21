package com.xpert.faces.bean;

import javax.faces.context.FacesContext;

/**
 *
 * @author Ayslan
 */
public class InitilizerContext {

    public static final String CONTEXT_KEY = "xpert.initialize";

    public static void forceInitilialization() {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(CONTEXT_KEY, true);
    }

    public static boolean isForcedInitilialization() {
        return (Boolean) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(CONTEXT_KEY);
    }
}
