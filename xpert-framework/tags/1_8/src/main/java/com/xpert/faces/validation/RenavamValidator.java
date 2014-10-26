package com.xpert.faces.validation;

import com.xpert.core.validation.Validation;
import com.xpert.i18n.XpertResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "renavamValidator")
public class RenavamValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null && !value.toString().trim().isEmpty()) {
            if (!Validation.validateRENAVAM(value.toString())) {
                FacesMessage msg = new FacesMessage(XpertResourceBundle.get("invalidRenavam"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
