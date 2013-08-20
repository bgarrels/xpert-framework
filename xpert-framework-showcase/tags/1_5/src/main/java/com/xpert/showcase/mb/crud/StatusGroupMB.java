package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.StatusGroupBO;
import com.xpert.showcase.model.StatusGroup;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class StatusGroupMB extends AbstractBaseBean<StatusGroup> {

    @EJB
    private StatusGroupBO statusGroupBO;

    @Override
    public AbstractBusinessObject getBO() {
        return statusGroupBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
