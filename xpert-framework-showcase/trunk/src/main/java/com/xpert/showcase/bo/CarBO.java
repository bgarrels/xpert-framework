package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.showcase.dao.CarDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import com.xpert.core.validation.UniqueFields;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.Car;

/**
 *
 * @author ayslan
 */
@Stateless
public class CarBO extends AbstractBusinessObject<Car> {

    @EJB
    private CarDAO carDAO;
    
    @Override
    public CarDAO getDAO() {
        return carDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return new UniqueFields("name");
    }

    @Override
    public void validate(Car car) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
