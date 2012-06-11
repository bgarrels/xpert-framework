package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.core.exception.BusinessException;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.core.validation.UniqueField;
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
        return new ArrayList<UniqueField>() {

            {
                add(new UniqueField("CPF already exists", "cpf"));
                add(new UniqueField("Email already exists", "email"));
                add(new UniqueField("RG already exists", "rg"));
            }
        };
    }

    @Override
    public boolean isAudit() {
        return true;
    }

    @Override
    public void validate(Person person) throws BusinessException {
    }

    
}
