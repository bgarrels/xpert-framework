package it.pkg.application;

import it.pkg.model.audit.Auditing;
import com.xpert.audit.AbstractAuditingListener;
import com.xpert.audit.model.AbstractAuditing;

/**
 *
 * @author
 */
public class AuditingListenerImpl implements AbstractAuditingListener {

    @Override
    public void onSave(AbstractAuditing abstractAuditing) {
        Auditing auditing = (Auditing)abstractAuditing;
        //TODO set user in audit
    }
}
