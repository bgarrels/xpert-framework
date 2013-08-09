package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.CityDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.City;

/**
 *
 * @author ayslan
 */
@Stateless
public class CityBO extends AbstractBusinessObject<City> {

    @EJB
    private CityDAO cityDAO;
    
    @Override
    public CityDAO getDAO() {
        return cityDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(City city) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
