package com.xpert.showcase.mb.crud;

import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.xpert.showcase.bo.GroupBO;
import com.xpert.showcase.model.Group;

/**
 *
 * @author #Author
 */
@ManagedBean
@ViewScoped
public class GroupMB extends AbstractBaseBean<Group> {

    @EJB
    private GroupBO groupBO;

    @Override
    public AbstractBusinessObject getBO() {
        return groupBO;
    }

    @Override
    public String getDataModelOrder() {
        return "id";
    }
}
