/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.test;

import com.xpert.core.conversion.NumberUtils;
import com.xpert.showcase.model.Person;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayslan
 */
public class TestSumAvgMinMax {

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Person 1", new BigDecimal("1000.80")));
        personList.add(new Person("Person 2", new BigDecimal("6000.00")));
        personList.add(new Person("Person 3", (BigDecimal) null));

        try {
            System.out.println(NumberUtils.sum(personList, "salary"));
            System.out.println(NumberUtils.max(personList, "salary"));
            System.out.println(NumberUtils.min(personList, "salary"));
            System.out.println(NumberUtils.avg(personList, "salary"));
        } catch (Exception ex) {
            Logger.getLogger(TestSumAvgMinMax.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
