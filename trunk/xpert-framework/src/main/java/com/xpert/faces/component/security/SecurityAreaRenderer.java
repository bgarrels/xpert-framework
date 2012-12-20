package com.xpert.faces.component.security;

import com.xpert.Constants;
import com.xpert.security.model.Role;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 *
 * @author ayslan
 */
public class SecurityAreaRenderer extends Renderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        SecurityArea securityArea = (SecurityArea) component;
        if (securityArea.isRendered() && securityArea.getChildCount() > 0) {
            if (allow(securityArea.getRolesAllowed(), component, context)) {
                for (UIComponent child : securityArea.getChildren()) {
                    if (child.isRendered()) {
                        child.encodeAll(context);
                    }
                }
            }

        }
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    private boolean allow(String rolesAllowed, UIComponent component, FacesContext context) {


        List<Role> roles = (List<Role>) context.getExternalContext().getSessionMap().get(Constants.USER_ROLES);

        if (rolesAllowed != null && !rolesAllowed.trim().isEmpty()) {
            return checkRoles(roles, rolesAllowed);
        } else {
            return true;
        }
    }

    private boolean checkRoles(List<Role> userRoles, String rolesAllowed) {
        if (userRoles == null || userRoles.isEmpty()) {
            return false;
        }

        String[] allowedRoles = rolesAllowed.split(",");
        if (allowedRoles != null) {
            for (Role role : userRoles) {
                if (role.getKey() != null) {
                    for (String string : role.getKey().split(",")) {
                        if (string == null) {
                            continue;
                        }
                        for (String allowed : allowedRoles) {
                            if (string.trim().equalsIgnoreCase(allowed.trim())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
