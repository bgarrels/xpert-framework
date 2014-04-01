package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.query.JoinBuilder;
import com.xpert.showcase.bo.PersonBO;
import com.xpert.showcase.model.Person;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@ViewScoped
public class PersonMB extends AbstractBaseBean<Person> {

    @EJB
    private PersonBO personBO;

    @Override
    public AbstractBusinessObject getBO() {
        return personBO;
    }

    @Override
    public String getDataModelOrder() {
        return "name";
    }
    
}
