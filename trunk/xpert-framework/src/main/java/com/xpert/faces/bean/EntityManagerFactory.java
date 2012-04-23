package com.xpert.faces.bean;

import javax.persistence.EntityManager;

/**
 *
 * @author Ayslan
 */
public interface EntityManagerFactory {
 
    public EntityManager getEntityManager();
    
}
