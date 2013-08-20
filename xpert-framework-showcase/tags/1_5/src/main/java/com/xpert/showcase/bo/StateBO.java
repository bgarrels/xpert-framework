package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.StateDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.State;

/**
 *
 * @author ayslan
 */
@Stateless
public class StateBO extends AbstractBusinessObject<State> {

    @EJB
    private StateDAO stateDAO;
    
    @Override
    public StateDAO getDAO() {
        return stateDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(State state) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
