package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.ProfileDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.Profile;

/**
 *
 * @author ayslan
 */
@Stateless
public class ProfileBO extends AbstractBusinessObject<Profile> {

    @EJB
    private ProfileDAO profileDAO;
    
    @Override
    public ProfileDAO getDAO() {
        return profileDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(Profile profile) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
