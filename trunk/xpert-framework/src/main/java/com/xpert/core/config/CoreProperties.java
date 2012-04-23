package com.xpert.core.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ayslan
 */
public class CoreProperties {

    private static final Logger logger = Logger.getLogger(CoreProperties.class.getName());
    private static final String BUNDLE_PATH = "xpert.BUNDLE_PATH";
    private static final String PROPERTIE_USE_I18N = "xpert.USE_I18N";
    private static final String DEFAULT_PROPERTIES_NAME = "core-config.properties";
    public static final String CURRENT_BUNDLE;
    public static final boolean USE_I18N;

    static {

        CURRENT_BUNDLE = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(BUNDLE_PATH);
        if(CURRENT_BUNDLE != null){
             logger.log(Level.INFO, "Bundle found for xpert-framework: {0}", CURRENT_BUNDLE);
        }else{
             logger.log(Level.INFO, "Bundle not found for xpert-framework.");
        }
        Object i18n = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(PROPERTIE_USE_I18N);
        if (i18n == null || Boolean.valueOf(i18n.toString()) == true) {
            USE_I18N = true;
             logger.log(Level.INFO, "Using internacionalization from xpert-framework.");
        }else{
            USE_I18N = false;
        }
        /*
        Properties properties = null;
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(DEFAULT_PROPERTIES_NAME);
            if (inputStream == null) {
                inputStream = CoreProperties.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_NAME);
            }
            if (inputStream == null) {
                inputStream = CoreProperties.class.getResourceAsStream(DEFAULT_PROPERTIES_NAME);
            }
            if (inputStream == null) {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_NAME);
            }
            if (inputStream != null) {
                properties = new Properties();
                properties.load(inputStream);
                logger.info("Bundle found for name: " + DEFAULT_PROPERTIES_NAME);
            }
        } catch (FileNotFoundException e) {
            logger.info(DEFAULT_PROPERTIES_NAME + " not found.");
        } catch (IOException e) {
            logger.info(DEFAULT_PROPERTIES_NAME + " not found.");
        }
        if (properties != null) {
            if (properties.getProperty(PROPERTIE_USE_I18N) == null || properties.getProperty(PROPERTIE_USE_I18N).equals(Boolean.TRUE.toString())) {
                USE_I18N = true;
            } else {
                USE_I18N = false;
            }
            if (USE_I18N) {
                logger.info("Using I18N from xpert-core.");
                CURRENT_BUNDLE = properties.getProperty(BUNDLE_PATH);
                if (CURRENT_BUNDLE == null) {
                    logger.warning("No bundle path defined in " + DEFAULT_PROPERTIES_NAME);
                }
            } else {
                CURRENT_BUNDLE = null;
            }
        } else {
            CURRENT_BUNDLE = null;
            USE_I18N = false;
        }
        * 
        */
    }

    public void init() {
    }
}
