/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.test;

import com.xpert.showcase.model.Person;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author Ayslan
 */
public class TestValidation {

    public static void main(String[] args) {

        Person pessoa = new Person();


        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(pessoa);

        for (ConstraintViolation<Person> constrantViolation : constraintViolations) {
            System.out.println(constrantViolation.getRootBean().getClass().getName());
            System.out.println(constrantViolation.getPropertyPath());
        }
    }
}
