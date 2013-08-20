package com.xpert.showcase.mb;

import com.xpert.security.SecuritySessionManager;
import com.xpert.security.model.Role;
import com.xpert.showcase.model.UserRole;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ayslan
 */
@ManagedBean
@SessionScoped
public class UserSession {

    private Date startSession = new Date();

    @PostConstruct
    public void init() {

        List<Role> roles = new ArrayList<Role>();

        roles.add(new UserRole("city.create"));
        roles.add(new UserRole("city.list"));
        roles.add(new UserRole("city.delete"));
        roles.add(new UserRole("city.audit"));

        roles.add(new UserRole("state.create"));
        roles.add(new UserRole("state.list"));
        roles.add(new UserRole("state.delete"));
        roles.add(new UserRole("state.audit"));

        roles.add(new UserRole("country.create"));
        roles.add(new UserRole("country.list"));
        roles.add(new UserRole("country.delete"));
        roles.add(new UserRole("country.audit"));

        roles.add(new UserRole("group.create"));
        roles.add(new UserRole("group.list"));
        roles.add(new UserRole("group.delete"));
        roles.add(new UserRole("group.audit"));

        roles.add(new UserRole("statusGroup.create"));
        roles.add(new UserRole("statusGroup.list"));
        roles.add(new UserRole("statusGroup.delete"));
        roles.add(new UserRole("statusGroup.audit"));

        roles.add(new UserRole("profile.create"));
        roles.add(new UserRole("profile.list"));
        roles.add(new UserRole("profile.delete"));
        roles.add(new UserRole("profile.audit"));

        roles.add(new UserRole("permission.create"));
        roles.add(new UserRole("permission.list"));
        roles.add(new UserRole("permission.delete"));
        roles.add(new UserRole("permission.audit"));

        roles.add(new UserRole("person.create"));
        roles.add(new UserRole("person.list"));
        roles.add(new UserRole("person.delete"));
        roles.add(new UserRole("person.audit"));

        SecuritySessionManager.putRoles(roles);

    }

    public Date getStartSession() {
        return startSession;
    }

    public void setStartSession(Date startSession) {
        this.startSession = startSession;
    }
}
