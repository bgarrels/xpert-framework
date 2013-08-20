package com.xpert.showcase.mb;

import com.xpert.faces.primefaces.LazyDataModelImpl;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@ViewScoped
public class LazyDataModelMB {
    
    @EJB
    private PersonDAO personDAO;
    private LazyDataModel<Person> people;
    
    @PostConstruct
    public void init(){
        people = new LazyDataModelImpl<Person>("name", personDAO);
    }

    public LazyDataModel<Person> getPeople() {
        return people;
    }

    public void setPeople(LazyDataModel<Person> people) {
        this.people = people;
    }
    
    
}
