package com.xpert.faces.bean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Ayslan
 */
public class EntityManagerHandler {

    private static String FACTORY_NAME = "xpert.ENTITY_MANAGER_FACTORY";
    private static final Logger logger = Logger.getLogger(EntityManagerHandler.class.getName());

    public static EntityManager getEntityManager() {
        String classFactory = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(FACTORY_NAME);
        try {
            Class clazz = Class.forName(classFactory, true, Thread.currentThread().getContextClassLoader());
            EntityManagerFactory entityManagerFactory = (EntityManagerFactory) clazz.newInstance();
            return entityManagerFactory.getEntityManager();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
