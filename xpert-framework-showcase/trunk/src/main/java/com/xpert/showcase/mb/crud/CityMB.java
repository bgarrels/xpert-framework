package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.CityBO;
import com.xpert.showcase.model.City;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class CityMB extends AbstractBaseBean<City> {

    @EJB
    private CityBO cityBO;

    @Override
    public AbstractBusinessObject getBO() {
        return cityBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
