package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.PersonType;
import javax.ejb.Local;

/**
 *
 * @author #Author
 */
@Local
public interface PersonTypeDAO extends BaseDAO<PersonType> {
    
}
