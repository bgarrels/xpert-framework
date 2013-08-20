package com.xpert.showcase.model;

import com.xpert.security.model.Role;

/**
 *
 * @author ayslan
 */
public class UserRole implements Role{
    
   private String key;
   private String url;

    public UserRole(String key, String url) {
        this.key = key;
        this.url = url;
    }

    public UserRole(String key) {
        this.key = key;
    }

    public UserRole() {
    }
   

   @Override
    public String getKey() {
        return key;
    }

   @Override
    public void setKey(String key) {
        this.key = key;
    }

   @Override
    public String getUrl() {
        return url;
    }

   @Override
    public void setUrl(String url) {
        this.url = url;
    }
    
   
   
}
