package com.xpert.showcase.mb;

import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class SavePerson {
    
    @EJB
    private PersonDAO personDAO;
    private Person person = new Person();
    
    
    public void saveWithAudit(){
        personDAO.saveOrUpdate(person);
    }
    
    public void saveNoAudit(){
        personDAO.saveOrUpdate(person, false);
    }
    
    //getters and setters
    
}
