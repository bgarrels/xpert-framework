package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.PermissionDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.Permission;

/**
 *
 * @author ayslan
 */
@Stateless
public class PermissionBO extends AbstractBusinessObject<Permission> {

    @EJB
    private PermissionDAO permissionDAO;
    
    @Override
    public PermissionDAO getDAO() {
        return permissionDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(Permission permission) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
