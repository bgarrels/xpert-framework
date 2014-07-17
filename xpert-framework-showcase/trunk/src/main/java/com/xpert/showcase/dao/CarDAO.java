package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.Car;
import javax.ejb.Local;

/**
 *
 * @author ayslan
 */
@Local
public interface CarDAO extends BaseDAO<Car> {
    
}
