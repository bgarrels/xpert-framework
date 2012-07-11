package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.PersonTypeBO;
import com.xpert.showcase.model.PersonType;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class PersonTypeMB extends AbstractBaseBean<PersonType> {

    @EJB
    private PersonTypeBO personTypeBO;

    @Override
    public AbstractBusinessObject getBO() {
        return personTypeBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
