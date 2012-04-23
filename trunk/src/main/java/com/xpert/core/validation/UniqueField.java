/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.core.validation;

/**
 *
 * @author Ayslan
 */
public class UniqueField {

    private String message;
    private String[] constraints;

    public UniqueField(String message, String field) {
        this.message = message;
        this.constraints = new String[]{field};
    }

    public UniqueField(String message, String... constraints) {
        this.message = message;
        this.constraints = constraints;
    }

    public String[] getConstraints() {
        return constraints;
    }

    public void setConstraints(String[] constraints) {
        this.constraints = constraints;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
