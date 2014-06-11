/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xpert.showcase.test;

import com.xpert.faces.primefaces.LazyDataModelImpl;
import com.xpert.persistence.query.JoinBuilder;
import com.xpert.persistence.query.Restrictions;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;

/**
 *
 * @author ayslan
 */
public class TestLazyDataModel {
    
    public static void main(String[] args) {
        
        PersonDAO personDAO = null;
        
        //criando o join builder onde "p" será o root da query
        JoinBuilder joinBuilder = new JoinBuilder("p");
        joinBuilder.leftJoinFetch("p.empresa", "e");
        
        //onde o nome é "like" Maria
        Restrictions restrictions = new Restrictions();
        restrictions.like("p.nome", "Maria");
      
        //criando o data model e setando o joinBuilder onde a ordenação padrão será "p.nome"
        LazyDataModelImpl<Person> dataModel = new LazyDataModelImpl<Person>("p.nome", personDAO);
        dataModel.setJoinBuilder(joinBuilder);
        
        
    }
    
}
