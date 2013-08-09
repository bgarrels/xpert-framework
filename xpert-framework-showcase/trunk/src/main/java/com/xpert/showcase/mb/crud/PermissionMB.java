package com.xpert.showcase.mb.crud;


import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.PermissionBO;
import com.xpert.showcase.model.Permission;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class PermissionMB extends AbstractBaseBean<Permission> implements Serializable {

    @EJB
    private PermissionBO permissionBO;

    @Override
    public AbstractBusinessObject getBO() {
        return permissionBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
