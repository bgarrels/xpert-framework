package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.core.exception.BusinessException;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.validation.UniqueFields;
import com.xpert.showcase.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ayslan
 */
@Stateless
public class PersonBO extends AbstractBusinessObject<Person> {

    @EJB
    private PersonDAO personDAO;

    @Override
    public BaseDAO getDAO() {
        return personDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        UniqueFields uniqueFields = new UniqueFields();
        uniqueFields.add(new UniqueField("cpf").setMessage("CPF already exists"));
        uniqueFields.add(new UniqueField("email").setMessage("Email already exists"));
        uniqueFields.add(new UniqueField("rg").setMessage("RG already exists"));
        return uniqueFields;
    }

    @Override
    public boolean isAudit() {
        return true;
    }

    @Override
    public void validate(Person person) throws BusinessException {
    }
}
