package com.xpert.showcase.mb;


import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.UserRoleBO;
import com.xpert.showcase.model.UserRole;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@ViewScoped
public class UserRoleMB extends AbstractBaseBean<UserRole> implements Serializable {

    @EJB
    private UserRoleBO userRoleBO;

    @Override
    public UserRoleBO getBO() {
        return userRoleBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
