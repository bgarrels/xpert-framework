package com.xpert.faces.conversion;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * Conversor para cnpj, na tela exibe com a mascara e ao submeter, remove a
 * mascara
 *
 * @author Ayslan
 */
@FacesConverter(value = "cnpjConverter")
public class CnpjConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String cnpj = "";

        if (value != null) {
            cnpj = value.replaceAll("[^\\d]", "");
        }

        return cnpj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        String cnpj = "";

        if (value != null && !value.toString().isEmpty()) {
            cnpj = Mask.maskCnpj(value.toString());
        }

        return cnpj;
    }
}
