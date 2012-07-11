package com.xpert.showcase.application;

import com.xpert.EntityManagerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

/**
 *
 * @author Ayslan
 */
public class EntityManagerFactoryImpl implements EntityManagerFactory {

    private static final Logger logger = Logger.getLogger(EntityManagerFactoryImpl.class.getName());
    private static final String ENTITY_MANAGER_REF_NAME = "java:comp/env/persistence/entityManager";

    @Override
    public EntityManager getEntityManager() {
        InitialContext ctx;
        try {
            ctx = new InitialContext();
            EntityManager entityManager = (EntityManager) ctx.lookup(ENTITY_MANAGER_REF_NAME);
            return entityManager;
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
