package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.GroupDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.Group;

/**
 *
 * @author ayslan
 */
@Stateless
public class GroupBO extends AbstractBusinessObject<Group> {

    @EJB
    private GroupDAO groupDAO;
    
    @Override
    public GroupDAO getDAO() {
        return groupDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(Group group) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
