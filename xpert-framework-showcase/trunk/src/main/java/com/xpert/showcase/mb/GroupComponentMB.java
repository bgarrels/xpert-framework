/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.mb;

import com.xpert.showcase.model.City;
import com.xpert.showcase.model.Country;
import com.xpert.showcase.model.Person;
import com.xpert.showcase.model.State;
import com.xpert.showcase.model.Status;
import com.xpert.utils.CollectionsUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class GroupComponentMB {

    private List<Person> people = new ArrayList<Person>();

    @PostConstruct
    public void init() {

        Country country1 = new Country(1L, "BRASIL");
        Country country2 = new Country(2L, "ARGENTINA");

        //COUNTRY 1
        State state1 = new State(1L, "AMAZONAS", country1);
        City city1 = new City(1l, "MANAUS", state1);

        State state2 = new State(2L, "SAO PAULO", country1);
        City city2 = new City(2l, "SAO PAULO", state2);
        City city3 = new City(3l, "CAMPINAS", state2);

        //COUNTRY 2
        State state3 = new State(3L, "BUENOS AIRES", country2);
        City city4 = new City(4l, "MAR DEL PLATA", state3);
        City city5 = new City(5l, "LA PLATA", state3);

        addPerson(city1);
        addPerson(city2);
        addPerson(city3);
        addPerson(city4);
        addPerson(city5);

    }

    public void addPerson(City city) {
        int randomSize = new Random().nextInt(4);
        if (randomSize == 0) {
            randomSize = 1;
        }
        for (int i = 0; i < randomSize; i++) {
            Person person = new Person("PERSON " + city.getName() + " " + (i + 1),
                    "person." + city.getName().replace(" ", ".").toLowerCase() + (i + 1) + "@gmail.com", city);
            if (i % 2 == 0) {
                person.setStatus(Status.ACTIVE);
            } else {
                person.setStatus(Status.INACTIVE);
            }
            people.add(person);
        }
        
        Collections.shuffle(people);
        
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

}
