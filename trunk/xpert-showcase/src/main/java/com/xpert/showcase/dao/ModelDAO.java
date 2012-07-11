package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.Model;
import javax.ejb.Local;

/**
 *
 * @author #Author
 */
@Local
public interface ModelDAO extends BaseDAO<Model> {
    
}
