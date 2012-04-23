package com.xpert;

import com.xpert.faces.bean.EntityManagerHandler;
import com.xpert.persistence.dao.BaseDAOImpl;

/**
 *
 * @author Ayslan
 */
public class DAO extends BaseDAOImpl {

    public DAO(Class entityClass) {
        super.setEntityClass(entityClass);
        super.setEntityManager(EntityManagerHandler.getEntityManager());
    }

    public DAO() {
        super.setEntityManager(EntityManagerHandler.getEntityManager());
    }

    @Override
    public void init() {
    }
}
