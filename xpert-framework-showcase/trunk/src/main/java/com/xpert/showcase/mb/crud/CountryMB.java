package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.CountryBO;
import com.xpert.showcase.model.Country;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class CountryMB extends AbstractBaseBean<Country> {

    @EJB
    private CountryBO countryBO;

    @Override
    public AbstractBusinessObject getBO() {
        return countryBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
