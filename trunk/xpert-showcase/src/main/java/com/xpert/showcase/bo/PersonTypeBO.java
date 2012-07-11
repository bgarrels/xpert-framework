package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.PersonTypeDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.PersonType;

/**
 *
 * @author #Author
 */
@Stateless
public class PersonTypeBO extends AbstractBusinessObject<PersonType> {

    @EJB
    private PersonTypeDAO personTypeDAO;
    
    @Override
    public BaseDAO getDAO() {
        return personTypeDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(PersonType personType) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
