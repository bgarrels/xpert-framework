package com.xpert.showcase.application;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ayslan
 */
public class BaseDAOImpl<T> extends com.xpert.persistence.dao.BaseDAOImpl<T> {

    @PersistenceContext(unitName = "xpertShowcasePU")
    private EntityManager entityManager;

    public BaseDAOImpl() {
    }

    @PostConstruct
    @Override
    public void init() {
        super.setEntityManager(entityManager);
    }
}
