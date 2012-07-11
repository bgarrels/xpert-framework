package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.CountryDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.Country;

/**
 *
 * @author #Author
 */
@Stateless
public class CountryBO extends AbstractBusinessObject<Country> {

    @EJB
    private CountryDAO countryDAO;
    
    @Override
    public BaseDAO getDAO() {
        return countryDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(Country country) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
