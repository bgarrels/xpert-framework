package com.xpert.showcase.mb;

import com.xpert.showcase.audit.Auditing;
import com.xpert.showcase.audit.Metadata;
import com.xpert.showcase.model.City;
import com.xpert.showcase.model.Country;
import com.xpert.showcase.model.Group;
import com.xpert.showcase.model.Permission;
import com.xpert.showcase.model.Person;
import com.xpert.showcase.model.Profile;
import com.xpert.showcase.model.State;
import com.xpert.showcase.model.Status;
import com.xpert.showcase.model.StatusGroup;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class ClassMB {

    public Class getAuditing() {
        return Auditing.class;
    }
    public Class getMetadata() {
        return Metadata.class;
    }
    public Class getCity() {
        return City.class;
    }
    public Class getCountry() {
        return Country.class;
    }
    public Class getGroup() {
        return Group.class;
    }
    public Class getPermission() {
        return Permission.class;
    }
    public Class getProfile() {
        return Profile.class;
    }
    public Class getState() {
        return State.class;
    }
    public Class getStatus() {
        return Status.class;
    }
    public Class getStatusGroup() {
        return StatusGroup.class;
    }

}

                            