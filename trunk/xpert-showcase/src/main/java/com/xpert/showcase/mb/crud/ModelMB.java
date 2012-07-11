package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.ModelBO;
import com.xpert.showcase.model.Model;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class ModelMB extends AbstractBaseBean<Model> {

    @EJB
    private ModelBO modelBO;

    @Override
    public AbstractBusinessObject getBO() {
        return modelBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
