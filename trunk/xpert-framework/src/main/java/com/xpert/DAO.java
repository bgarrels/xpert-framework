package com.xpert;

import com.xpert.persistence.dao.BaseDAOImpl;
import com.xpert.persistence.utils.EntityUtils;
import java.util.List;
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

    public DAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public DAO(Class entityClass, EntityManager entityManager) {
        super.setEntityClass(entityClass);
        this.entityManager = entityManager;
    }

    public DAO() {
    }

    @Override
    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Configuration.getEntityManager();
        }
        return entityManager;
    }
}
