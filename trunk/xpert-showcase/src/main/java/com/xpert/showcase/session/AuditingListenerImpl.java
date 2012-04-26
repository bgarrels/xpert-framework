package com.xpert.showcase.session;

import com.xpert.audit.AbstractAuditingListener;
import com.xpert.audit.model.AbstractAuditing;
import com.xpert.showcase.audit.Auditing;

/**
 *
 * @author Ayslan
 */
public class AuditingListenerImpl implements AbstractAuditingListener {

    @Override
    public void onSave(AbstractAuditing abstractAuditing) {
        Auditing auditing = (Auditing)abstractAuditing;
        //example, set here the current user
        auditing.setUser(null);
    }
}
