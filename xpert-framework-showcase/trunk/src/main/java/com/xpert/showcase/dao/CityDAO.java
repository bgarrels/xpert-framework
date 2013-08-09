package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.City;
import javax.ejb.Local;

/**
 *
 * @author #Author
 */
@Local
public interface CityDAO extends BaseDAO<City> {
    
}
