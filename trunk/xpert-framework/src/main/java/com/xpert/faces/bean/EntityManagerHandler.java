package com.xpert.faces.bean;

import com.xpert.configuration.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author Ayslan
 */
public class EntityManagerHandler {

    private static final Logger logger = Logger.getLogger(EntityManagerHandler.class.getName());

    public static EntityManager getEntityManager() {
        try {
            EntityManagerFactory entityManagerFactory = Configuration.getEntityManagerFactory();
            return entityManagerFactory.getEntityManager();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
