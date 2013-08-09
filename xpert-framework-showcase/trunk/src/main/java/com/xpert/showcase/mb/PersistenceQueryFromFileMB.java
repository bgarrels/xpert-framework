package com.xpert.showcase.mb;

import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class PersistenceQueryFromFileMB {
    
    @EJB
    private PersonDAO personDAO;
    private List<Person> people;
    
    @PostConstruct
    public void init(){
        people = personDAO.getFromSQLFile();
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    
}
