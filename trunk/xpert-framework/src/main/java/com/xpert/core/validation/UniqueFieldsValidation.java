package com.xpert.core.validation;

import com.xpert.Configuration;
import com.xpert.core.exception.BusinessException;
import com.xpert.core.exception.UniqueFieldException;
import com.xpert.i18n.I18N;
import com.xpert.i18n.XpertResourceBundle;
import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.query.Restriction;
import com.xpert.persistence.query.RestrictionType;
import com.xpert.persistence.utils.EntityUtils;
import com.xpert.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Ayslan
 */
public class UniqueFieldsValidation {

    public static void validateUniqueFields(List<UniqueField> uniqueFields, Object object, BaseDAO baseDAO) throws BusinessException {

        if (uniqueFields == null) {
            return;
        }

        UniqueFieldException exception = new UniqueFieldException();

        for (UniqueField uniqueField : uniqueFields) {
            List<Restriction> restrictions = new ArrayList<Restriction>();
            for (String fieldName : uniqueField.getConstraints()) {
                try {
                    Object value = PropertyUtils.getProperty(object, fieldName);
                    if (value != null && !value.toString().isEmpty()) {
                        restrictions.add(new Restriction(fieldName, value));
                    } else {
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
                    if (uniqueField.getMessage() != null && !uniqueField.getMessage().isEmpty()) {
                        exception.add(uniqueField.getMessage());
                    } else {
                        exception.setI18n(false);
                        exception.add(getValidationMessage(uniqueField, object));
                    }
                }
            }
        }

        exception.check();
    }

    private static String getValidationMessage(UniqueField uniqueField, Object object) {
        String lowerClassName = StringUtils.getLowerFirstLetter(object.getClass().getSimpleName());
        StringBuilder properties = new StringBuilder();
        properties.append(I18N.get(lowerClassName + "." + uniqueField.getConstraints()[0]));
        if (uniqueField.getConstraints().length > 1) {
            int it = 0;
            for (String fieldName : uniqueField.getConstraints()) {
                if (it > 0) {
                    if (it + 1 == uniqueField.getConstraints().length) {
                        properties.append(XpertResourceBundle.get("and"));
                    } else if (it > 0) {
                        properties.append(", ");
                    }
                    properties.append(I18N.get(lowerClassName + "." + fieldName));
                }
                it++;
            }
        }
        return XpertResourceBundle.get("alreadyRegisteredWithField", lowerClassName, properties.toString());
    }
}
