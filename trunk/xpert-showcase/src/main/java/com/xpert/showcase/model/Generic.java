/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.model;

import java.util.Date;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ayslan
 */
@MappedSuperclass
public abstract class Generic {
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }
    
    public String getTest(){
        return "";
    }
    
}
