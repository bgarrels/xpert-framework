/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.pkg.dao.impl;

import it.pkg.application.BaseDAOImpl;
import it.pkg.dao.DAO;
import com.xpert.persistence.dao.BaseDAO;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class DAOImpl extends BaseDAOImpl implements DAO {

    @Override
    public <T> BaseDAO<T> getDAO(Class<T> entity) {
        this.setEntityClass(entity);
        return (BaseDAO<T>) this;
    }
}
