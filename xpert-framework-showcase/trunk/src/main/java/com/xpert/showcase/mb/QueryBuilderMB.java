package com.xpert.showcase.mb;

import com.xpert.persistence.query.QueryBuilder;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import com.xpert.showcase.model.Status;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class QueryBuilderMB {
    
    private List<Person> example1 ;
    private List<Person> example2 ;
    private BigDecimal sumSalary ;
    private Long countPerson ;
    @EJB
    private PersonDAO personDAO;
    
    @PostConstruct
    public void init(){
        
        
        example1 = personDAO.getQueryBuilder()
                            .from(Person.class)
                            .like("name", "Maria")
                            .orderBy("name").getResultList();
        
        example2 = personDAO.getQueryBuilder()
                            .from(Person.class)
                            .equals("status", Status.ACTIVE)
                            .greaterEqualsThan("salary", new BigDecimal(1000))
                            .orderBy("code DESC").getResultList();
        
        countPerson = personDAO.getQueryBuilder()
                               .from(Person.class).count();
        
        sumSalary = (BigDecimal) personDAO.getQueryBuilder()
                                          .from(Person.class).sum("salary");
        
        
    }

    public List<Person> getExample1() {
        return example1;
    }

    public void setExample1(List<Person> example1) {
        this.example1 = example1;
    }

    public List<Person> getExample2() {
        return example2;
    }

    public void setExample2(List<Person> example2) {
        this.example2 = example2;
    }

    public BigDecimal getSumSalary() {
        return sumSalary;
    }

    public void setSumSalary(BigDecimal sumSalary) {
        this.sumSalary = sumSalary;
    }

    public Long getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Long countPerson) {
        this.countPerson = countPerson;
    }
    
    
    
    
    
}
