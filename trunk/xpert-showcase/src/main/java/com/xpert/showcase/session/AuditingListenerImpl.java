/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.session;

import com.xpert.audit.AbstractAuditingListener;
import com.xpert.audit.model.AbstractAuditing;
import java.util.logging.Logger;

/**
 *
 * @author Ayslan
 */
public class AuditingListenerImpl implements AbstractAuditingListener {

    private static final Logger logger = Logger.getLogger(AuditingListenerImpl.class.getName());
    
    @Override
    public void onSave(AbstractAuditing abstractAuditing) {
        logger.info("on save @Entity");
    }
}
