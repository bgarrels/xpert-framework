package com.xpert.showcase.mb;

import com.xpert.showcase.model.*;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class ClassMB {

    public Class getGroup() {
        return Group.class;
    }

    public Class getPermission() {
        return Permission.class;
    }

    public Class getPerson() {
        return Person.class;
    }

    public Class getStatus() {
        return Status.class;
    }

    public Class getCountry() {
        return Country.class;
    }
    public Class getState() {
        return State.class;
    }
}
