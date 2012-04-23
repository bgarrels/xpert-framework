/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.session;

import com.xpert.annotation.AuditingListener;
import com.xpert.audit.AbstractAuditingListener;
import com.xpert.audit.model.AbstractAuditing;

/**
 *
 * @author Ayslan
 */
@AuditingListener
public class AuditingListenerImpl implements AbstractAuditingListener{

    @Override
    public Object getUser() {
        return null;
    }

    @Override
    public void onSave(AbstractAuditing abstractAuditing) {
        System.out.println("on save");
    }
    
}
