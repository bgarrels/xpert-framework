package com.xpert.showcase.dao.impl;

import com.xpert.showcase.application.BaseDAOImpl;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Ayslan
 */
@Stateless
public class PersonDAOImpl extends BaseDAOImpl<Person> implements PersonDAO {
    @Override
    public List<Person> getFromSQLFile(){
        Query query = getNativeQueryFromFile("findAllPeople.sql", this.getClass(), Person.class);
        return query.getResultList();
    }
}
