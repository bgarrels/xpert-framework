package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.PermissionDAO;
import com.xpert.core.validation.UniqueField;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author #Insert Author here
 */
@Stateless
public class PermissionBO extends AbstractBusinessObject {

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
    public Boolean isAudit() {
        return true;
    }
}
