package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.StateBO;
import com.xpert.showcase.model.State;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class StateMB extends AbstractBaseBean<State> {

    @EJB
    private StateBO stateBO;

    @Override
    public AbstractBusinessObject getBO() {
        return stateBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
