package com.xpert;

import com.xpert.persistence.dao.BaseDAOImpl;

/**
 *
 * @author Ayslan
 */
public class DAO extends BaseDAOImpl {

    public DAO(Class entityClass) {
        super.setEntityClass(entityClass);
        super.setEntityManager(Configuration.getEntityManager());
    }

    public DAO() {
        super.setEntityManager(Configuration.getEntityManager());
    }

    @Override
    public void init() {
    }
}
