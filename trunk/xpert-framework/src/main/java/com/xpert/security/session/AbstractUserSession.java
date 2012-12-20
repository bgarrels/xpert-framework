package com.xpert.security.session;

import com.xpert.security.model.Role;
import com.xpert.security.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ayslan
 */
public abstract class AbstractUserSession {

    public Map<String, Role> rolesMap;
    public Map<String, String> urlsMap;

    public void createSession() {
        urlsMap = null;
        rolesMap = null;
    }

    public abstract User getUser();

    public abstract void setUser(User user);

    public abstract List<Role> getRoles();

    public boolean isAuthenticated(AbstractUserSession userSession) {
        return getUser() != null;
    }

    /**
     * Method to store roles in a map, for better peformance
     *
     * @return
     */
    public Map<String, Role> getRolesMap() {
        if (rolesMap == null) {
            rolesMap = new HashMap<String, Role>();
            for (Role role : getRoles()) {
                if (role.getKey() != null) {
                    for (String string : role.getKey().split(",")) {
                        rolesMap.put(string, role);
                    }
                }
            }
        }
        return rolesMap;
    }

    public Map<String, String> getUrlsMap() {
        if (urlsMap == null) {
            urlsMap = new HashMap<String, String>();
            for (Role role : getRoles()) {
                if (role.getUrl() != null && !role.getUrl().isEmpty()) {
                    for (String url : role.getUrl().split(",")) {
                        urlsMap.put(url.trim(), url.trim());
                    }
                }
            }
        }
        return urlsMap;
    }

    /**
     * Verify if user can acess url.
     *
     * @param key
     * @return
     */
    public boolean hasURL(String url) {
        if (getUrlsMap() != null && getUrlsMap().get(url.trim()) != null) {
            return true;
        }
        return false;
    }

    /**
     * Verify if user has the role os a permission. It can be multiple keys
     * (separeted with commas)
     *
     * @param key
     * @return
     */
    public boolean hasRole(String key) {
        if (getUser() != null && getRolesMap() != null) {
            if (getRole(key) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get role from user. The key can be separeted with commas, so the first
     * result is retrieved
     *
     * @param key
     * @return
     */
    private Role getRole(String key) {
        if (key != null && !key.trim().isEmpty() && getRolesMap() != null) {
            String[] keys = key.split(",");
            for (String c : keys) {
                Role role = getRolesMap().get(c.trim());
                if (role != null) {
                    return role;
                }
            }
        }
        return null;
    }
}
