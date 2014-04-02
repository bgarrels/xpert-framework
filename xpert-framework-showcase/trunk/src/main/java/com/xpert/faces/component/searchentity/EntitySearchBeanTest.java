/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.faces.component.searchentity;

import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class EntitySearchBeanTest implements Serializable {

    @EJB
    private PersonDAO personDAO;
    private List<Person> people;

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    
    
}
