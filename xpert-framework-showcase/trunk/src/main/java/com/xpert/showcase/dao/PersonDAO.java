package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.Person;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ayslan
 */
@Local
public interface PersonDAO extends BaseDAO<Person> {

    public List<Person> getFromSQLFile();
    
}
