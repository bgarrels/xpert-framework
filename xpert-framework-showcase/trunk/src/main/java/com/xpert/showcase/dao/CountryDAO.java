package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.Country;
import javax.ejb.Local;

/**
 *
 * @author #Author
 */
@Local
public interface CountryDAO extends BaseDAO<Country> {
    
}
