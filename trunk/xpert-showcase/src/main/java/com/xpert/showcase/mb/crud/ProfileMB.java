package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.ProfileBO;
import com.xpert.showcase.model.Profile;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class ProfileMB extends AbstractBaseBean<Profile> {

    @EJB
    private ProfileBO profileBO;

    @Override
    public AbstractBusinessObject getBO() {
        return profileBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
