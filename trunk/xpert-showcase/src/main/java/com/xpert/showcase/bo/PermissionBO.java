package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.core.exception.BusinessException;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.PermissionDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.showcase.model.Permission;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author #Insert Author here
 */
@Stateless
public class PermissionBO extends AbstractBusinessObject<Permission> {

    @EJB
    private PermissionDAO permissionDAO;

    @Override
    public BaseDAO getDAO() {
        return permissionDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public boolean isAudit() {
        return true;
    }

    @Override
    public void validate(Permission permission) throws BusinessException {
    }
}
