/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.model;

/**
 *
 * @author Ayslan
 */
public enum Status {
    
    ACTIVE("Active"), SUSPENDED("Suspended"), INACTIVE("Inactive");
    
    private String description;

    private Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
