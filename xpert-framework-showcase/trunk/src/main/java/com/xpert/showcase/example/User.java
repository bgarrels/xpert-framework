package com.xpert.showcase.example;

import com.xpert.audit.NotAudited;
import javax.persistence.Transient;

/**
 *
 * @author Ayslan
 */
public class User {

    private String name;
    private String login;
    @NotAudited
    private String password;

    @Transient
    public String getLoginAndName() {
        return login + " - " + name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
