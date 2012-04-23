/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.test;

import com.xpert.annotation.MetadataEntity;
import eu.infomas.annotation.AnnotationDetector;
import eu.infomas.annotation.AnnotationDetector.TypeReporter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayslan
 */
public class AnnotationTest {

    public static void main(String[] args) {
        System.out.println("inicio");

        final TypeReporter reporter = new TypeReporter() {

            public void reportTypeAnnotation(Class<? extends Annotation> type, String string) {
                System.out.println("type: "+type.getName()+" string: "+string);
            }

            public Class<? extends Annotation>[] annotations() {
               return new Class[]{MetadataEntity.class};
            }

        };

        AnnotationDetector detector = new AnnotationDetector(reporter);
        try {
            detector.detect();
        } catch (IOException ex) {
            Logger.getLogger(AnnotationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("fim!");
    }
}
