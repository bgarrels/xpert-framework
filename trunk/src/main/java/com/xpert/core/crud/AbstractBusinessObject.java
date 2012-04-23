/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.core.crud;

import com.xpert.core.exception.BusinessException;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.exception.DeleteException;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.validation.UniqueFieldsValidation;
import java.util.List;

/**
 *
 * @author Ayslan
 */
public abstract class AbstractBusinessObject {

    public abstract BaseDAO getDAO();

    public abstract List<UniqueField> getUniqueFields();

    public abstract Boolean isAudit();

    public void validateUniqueFields(Object object) throws BusinessException {
        if (getUniqueFields() != null && !getUniqueFields().isEmpty()) {
            UniqueFieldsValidation.validateUniqueFields(getUniqueFields(), object, getDAO());
        }
    }

    public void save(Object object) throws BusinessException {

        BusinessException exception = new BusinessException();
        try {
            validateUniqueFields(object);
        } catch (BusinessException ex) {
            exception.add(ex);
        }
        exception.check();
        getDAO().saveOrUpdate(object);
    }

    public void delete(Long id) throws DeleteException {
        getDAO().delete(id);
    }
}
