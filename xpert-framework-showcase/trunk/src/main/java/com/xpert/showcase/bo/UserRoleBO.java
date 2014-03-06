package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.UserRoleDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.UserRole;

/**
 *
 * @author Ayslan
 */
@Stateless
public class UserRoleBO extends AbstractBusinessObject<UserRole> {

    @EJB
    private UserRoleDAO userRoleDAO;
    
    @Override
    public UserRoleDAO getDAO() {
        return userRoleDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(UserRole userRole) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
