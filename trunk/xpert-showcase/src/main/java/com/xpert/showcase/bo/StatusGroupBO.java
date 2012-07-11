package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.StatusGroupDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.StatusGroup;

/**
 *
 * @author #Author
 */
@Stateless
public class StatusGroupBO extends AbstractBusinessObject<StatusGroup> {

    @EJB
    private StatusGroupDAO statusGroupDAO;
    
    @Override
    public BaseDAO getDAO() {
        return statusGroupDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(StatusGroup statusGroup) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
