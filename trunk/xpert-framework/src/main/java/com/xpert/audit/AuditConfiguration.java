package com.xpert.audit;

import javax.faces.context.FacesContext;

/**
 *
 * @author Ayslan
 */
public class AuditConfiguration {

    private static final String AUDIT_PARAM = "xpert.AUDIT";
    private static Boolean AUDIT;

    public static boolean isAudit() {
        if (AUDIT == null) {
            String param = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(AUDIT_PARAM);
            if (param == null || param.trim().isEmpty()) {
                AUDIT = false;
            } else {
                AUDIT = Boolean.valueOf(param);
            }
        }
        return AUDIT;
    }
}
