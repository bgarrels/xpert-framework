/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.mb;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.persistence.query.Restriction;
import java.io.Serializable;
import java.util.List;

public class EntitySearch implements Serializable {

    private List<Restriction> restrictions;
    private Class forClass;
    private BaseDAO baseDAO;
    private List value;

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public Class getForClass() {
        return forClass;
    }

    public void setForClass(Class forClass) {
        this.forClass = forClass;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public List getValue() {
        return value;
    }

    public void setValue(List value) {
        this.value = value;
    }

}
