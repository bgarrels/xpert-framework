package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.CompanyTypeDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.CompanyType;

/**
 *
 * @author #Author
 */
@Stateless
public class CompanyTypeBO extends AbstractBusinessObject<CompanyType> {

    @EJB
    private CompanyTypeDAO companyTypeDAO;
    
    @Override
    public BaseDAO getDAO() {
        return companyTypeDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(CompanyType companyType) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
