package com.xpert.showcase.bo;

import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.dao.ModelDAO;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.xpert.showcase.model.Model;

/**
 *
 * @author #Author
 */
@Stateless
public class ModelBO extends AbstractBusinessObject<Model> {

    @EJB
    private ModelDAO modelDAO;
    
    @Override
    public BaseDAO getDAO() {
        return modelDAO;
    }

    @Override
    public List<UniqueField> getUniqueFields() {
        return null;
    }

    @Override
    public void validate(Model model) throws BusinessException {
    }

    @Override
    public boolean isAudit() {
        return true;
    }

}
