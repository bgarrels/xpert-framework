package com.xpert.showcase.mb.crud;


import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.CarBO;
import com.xpert.showcase.model.Car;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class CarMB extends AbstractBaseBean<Car> implements Serializable {

    @EJB
    private CarBO carBO;

    @Override
    public CarBO getBO() {
        return carBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
