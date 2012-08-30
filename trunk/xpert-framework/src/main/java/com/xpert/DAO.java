package com.xpert;

import com.xpert.persistence.dao.BaseDAOImpl;
import javax.persistence.EntityManager;

/**
 *
 * @author Ayslan
 */
public class DAO extends BaseDAOImpl {

    private EntityManager entityManager;
    
    public DAO(Class entityClass) {
        super.setEntityClass(entityClass);
    }

    public DAO() {
    }

    @Override
    public EntityManager getEntityManager() {
        if(entityManager == null){
            entityManager = Configuration.getEntityManager();
        }
        return entityManager;
    }
}
