#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author
 */
public class BaseDAOImpl<T> extends com.xpert.persistence.dao.BaseDAOImpl<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public BaseDAOImpl() {
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
