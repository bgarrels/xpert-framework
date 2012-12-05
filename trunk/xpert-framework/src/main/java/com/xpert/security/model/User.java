package com.xpert.security.model;

/**
 *
 * @author ayslan
 */
public abstract class User {

    public abstract boolean isActive();

    public abstract void setActive(boolean active);

    public abstract String getUserPassword();

    public abstract void setUserPassword(String password);

    public abstract String getUserLogin();

    public abstract void setUserLogin(String login);
}
