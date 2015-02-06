package com.xpert.showcase.mb;

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
public class SumMB {

    public static void main(String[] args) {

        List<Person> list = new ArrayList<Person>();
        list.add(new Person("Person 1", null, new BigDecimal("1000.80"), 1));
        list.add(new Person("Person 2", null, new BigDecimal("6000.00"), 80));
        list.add(new Person("Person 3", null, null, 1));
        list.add(new Person("Person 4", 1L, new BigDecimal("7000.1"), 0));
        list.add(new Person("Person 5", 8L, new BigDecimal("9999.80"), 1));

//        for (int i = 0; i < 1000; i++) {
//            list.add(new Person("Person "+i, 8L, new BigDecimal("9999.80"), 1));
//        }

        try {

            Object sumSalary = NumberUtils.sumLong(list, "salary");
            Object sumCode = NumberUtils.sumInteger(list, "code");
            Object sumCount = NumberUtils.sum(list, "count");

            System.out.println("Salary: " + sumSalary + " - " + sumSalary.getClass());
            System.out.println("Code: " + sumCode + " - " + sumCode.getClass());
            System.out.println("Count: " + sumCount + " - " + sumCount.getClass());

        } catch (Exception ex) {
            Logger.getLogger(SumMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
