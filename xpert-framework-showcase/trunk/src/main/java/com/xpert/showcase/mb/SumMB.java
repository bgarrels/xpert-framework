package com.xpert.showcase.mb;

import com.xpert.faces.primefaces.LazyDataModelImpl;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SumMB {
    
    @EJB
    private PersonDAO personDAO;

    private LazyDataModelImpl<Person> personLazy;
    private List<Person> personList;

    @PostConstruct
    public void init() {
        //create list
        personList = new ArrayList<Person>();
        personList.add(new Person("Person 1", new BigDecimal("1000.80")));
        personList.add(new Person("Person 2", new BigDecimal("6000.00")));
        personList.add(new Person("Person 3", (BigDecimal) null));
        personList.add(new Person("Person 4", new BigDecimal("7000.1")));
        personList.add(new Person("Person 5", new BigDecimal("9999.80")));
        
        //create LazyDataModel
        personLazy = new LazyDataModelImpl<Person>("name", personDAO);
    }

    public LazyDataModelImpl<Person> getPersonLazy() {
        return personLazy;
    }

    public void setPersonLazy(LazyDataModelImpl<Person> personLazy) {
        this.personLazy = personLazy;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
    
    

}
