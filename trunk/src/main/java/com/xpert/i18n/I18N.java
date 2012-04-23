package com.xpert.i18n;

import com.xpert.core.config.CoreProperties;
import java.util.Locale;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ayslan
 */
public class I18N {

    private static final Logger logger = Logger.getLogger(I18N.class.getName());

    public static String get(String key, Object... parameters) {
        if (CoreProperties.CURRENT_BUNDLE == null) {
            return key;
        }
        return ResourceBundleUtils.get(key, CoreProperties.CURRENT_BUNDLE, Thread.currentThread().getContextClassLoader(), parameters);
    }

    public static Locale getLocale() {
        //for JSF Context
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            if (facesContext.getViewRoot() != null) {
                return facesContext.getViewRoot().getLocale();
            }
            if (facesContext.getApplication() != null && facesContext.getApplication().getDefaultLocale() != null) {
                return facesContext.getApplication().getDefaultLocale();
            }
        }
        return ResourceBundleUtils.PT_BR;
    }
}
