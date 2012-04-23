package com.xpert.core.validation;

import com.xpert.core.exception.BusinessException;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.query.Restriction;
import com.xpert.persistence.query.RestrictionType;
import com.xpert.persistence.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Ayslan
 */
public class UniqueFieldsValidation {
    
    public static void validateUniqueFields(List<UniqueField> uniqueFields, Object object, BaseDAO baseDAO) throws BusinessException {

        BusinessException exception = new BusinessException();

        for (UniqueField uniqueField : uniqueFields) {
            List<Restriction> restrictions = new ArrayList<Restriction>();
            for (String s : uniqueField.getConstraints()) {
                try {
                    Object value = PropertyUtils.getProperty(object, s);
                    if (value != null && !value.toString().isEmpty()) {
                        restrictions.add(new Restriction(s, value));
                    }else{
                        //remove restriction
                        restrictions.clear();
                        break;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (!restrictions.isEmpty()) {
                Object id = EntityUtils.getId(object);
                if (id != null) {
                    restrictions.add(new Restriction(EntityUtils.getIdFieldName(object), RestrictionType.NOT_EQUALS, id));
                }
                if (baseDAO.count(restrictions) > 0) {
                    exception.add(uniqueField.getMessage());
                }
            }
        }

        exception.check();
    }
}
