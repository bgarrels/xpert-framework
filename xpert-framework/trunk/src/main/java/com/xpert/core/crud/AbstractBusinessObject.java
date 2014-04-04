package com.xpert.core.crud;

import com.xpert.core.exception.BusinessException;
import com.xpert.core.exception.UniqueFieldException;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.exception.DeleteException;
import com.xpert.core.validation.UniqueField;
import com.xpert.core.validation.UniqueFieldsValidation;
import com.xpert.persistence.utils.EntityUtils;
import java.util.List;

/**
 *
 * @author Ayslan
 */
public abstract class AbstractBusinessObject<T> {

    /**
     * @return The BusinessObject BaseDAO
     */
    public abstract BaseDAO getDAO();

    /**
     * Define the UniqueFields to be used in method "validateUniqueFields"
     * 
     * @return 
     */
    public abstract List<UniqueField> getUniqueFields();

    /**
     * Determine if entity will be audited on method "save()"
     * 
     * @return 
     */
    public abstract boolean isAudit();

    /**
     * Your own logic to validate the object
     * 
     * @param object
     * @throws BusinessException 
     */
    public abstract void validate(T object) throws BusinessException;

    
    /**
     * Validate unique field based on "getUniqueFields()"
     * 
     * @param object
     * @throws UniqueFieldException 
     */
    public void validateUniqueFields(Object object) throws UniqueFieldException {
        if (getUniqueFields() != null && !getUniqueFields().isEmpty()) {
            UniqueFieldsValidation.validateUniqueFields(getUniqueFields(), object, getDAO());
        }
    }

    /**
     * Saves entity:
     * 
     *  1 - call "validate(object)"
     *  2 - call "validateUniqueFields(object)"
     *  3 - check if any excpetion occurred
     *  4 - if entity id is null then call "save", if not, call "merge"
     * 
     * @param object
     * @throws BusinessException 
     */
    public void save(T object) throws BusinessException {

        BusinessException exception = new BusinessException();
        try {
            validate(object);
            validateUniqueFields(object);
        } catch (BusinessException ex) {
            exception.add(ex);
        }
        exception.check();
        if (!EntityUtils.isPersisted(object)) {
            getDAO().save(object, isAudit());
        } else {
            getDAO().merge(object, isAudit());
        }
    }

    /**
     * Call "baseDAO.delete()" to delete entity
     * 
     * @param id entity id
     * @throws DeleteException 
     */
    public void delete(Long id) throws DeleteException {
        getDAO().delete(id);
    }
    
    /**
     * Call "baseDAO.remove()" to delete entity
     * 
     * @param id Entity id
     * @throws DeleteException 
     */
    public void remove(Long id) throws DeleteException {
        Object object = getDAO().find(id);
        getDAO().remove(object);
    }
    
    /**
     * Call "baseDAO.remove()" to delete entity
     * 
     * @param object Object to delete
     * @throws DeleteException 
     */
    public void remove(T object) throws DeleteException {
        getDAO().remove(object);
    }
}
