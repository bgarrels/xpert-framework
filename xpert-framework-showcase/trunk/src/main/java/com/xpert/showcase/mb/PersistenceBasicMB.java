package com.xpert.showcase.mb;

import com.xpert.persistence.query.Restriction;
import com.xpert.persistence.query.RestrictionType;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class PersistenceBasicMB {
    
    @EJB
    private PersonDAO personDAO;
    private Long count;
    private List<Person> people;
    private Long countPeopleWithCodeGreaterThanFive;
    private List<Person> peopleWithCodeGreaterThanFive;
    
    @PostConstruct
    public void init(){
        count = personDAO.count();
        people = personDAO.listAll();
        
        Restriction restriction = new Restriction("code", RestrictionType.GREATER_THAN, 5L);
        
        countPeopleWithCodeGreaterThanFive = personDAO.count(restriction);
        peopleWithCodeGreaterThanFive = personDAO.list(restriction);
        
    }
    
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public Long getCountPeopleWithCodeGreaterThanFive() {
        return countPeopleWithCodeGreaterThanFive;
    }

    public void setCountPeopleWithCodeGreaterThanFive(Long countPeopleWithCodeGreaterThanFive) {
        this.countPeopleWithCodeGreaterThanFive = countPeopleWithCodeGreaterThanFive;
    }

    public List<Person> getPeopleWithCodeGreaterThanFive() {
        return peopleWithCodeGreaterThanFive;
    }

    public void setPeopleWithCodeGreaterThanFive(List<Person> peopleWithCodeGreaterThanFive) {
        this.peopleWithCodeGreaterThanFive = peopleWithCodeGreaterThanFive;
    }

}
