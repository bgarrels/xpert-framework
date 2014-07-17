package com.xpert.showcase.dao.impl;

import com.xpert.showcase.application.BaseDAOImpl;
import com.xpert.showcase.dao.CarDAO;
import com.xpert.showcase.model.Car;
import javax.ejb.Stateless;

/**
 *
 * @author ayslan
 */
@Stateless
public class CarDAOImpl extends BaseDAOImpl<Car> implements CarDAO {
}
