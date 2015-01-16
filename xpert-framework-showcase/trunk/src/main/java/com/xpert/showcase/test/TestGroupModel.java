/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.test;

import com.xpert.faces.component.group.model.GroupModel;
import com.xpert.faces.component.group.model.GroupModelItem;
import com.xpert.showcase.mb.GroupComponentMB;
import com.xpert.showcase.model.Country;
import com.xpert.showcase.model.Person;
import com.xpert.showcase.model.State;
import java.util.List;

/**
 *
 * @author Ayslan
 */
public class TestGroupModel {

    public static void main(String[] args) {

        GroupComponentMB groupComponentMB = new GroupComponentMB();
        groupComponentMB.init();

        List<Person> people = groupComponentMB.getPeople();

        GroupModel groupModel = new GroupModel("city.state.country", people);
        groupModel.groupItens();

        for (Object object : groupModel.getItens()) {
            GroupModelItem item = (GroupModelItem) object;
            Country country = (Country) item.getKey();
            System.out.println("## " + country.getName() + " ##");

            GroupModel groupModelEstados = new GroupModel("city.state", item.getValue());
            groupModelEstados.groupItens();

            for (Object objectEstado : groupModelEstados.getItens()) {

                GroupModelItem itemEstado = (GroupModelItem) objectEstado;
                State state = (State) itemEstado.getKey();
                System.out.println("- " + state.getName());

                 for (Object objectPerson : itemEstado.getValue()) {
//                       GroupModelItem itemPerson = (GroupModelItem) objectEstado;
                       Person person = (Person)objectPerson;
                       System.out.println(" - "+person.getName());
                 }
                
            }

        }

    }

}
