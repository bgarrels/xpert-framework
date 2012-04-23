package com.xpert.configuration;

import com.xpert.annotation.AuditingEntity;
import com.xpert.annotation.AuditingListener;
import com.xpert.annotation.MetadataEntity;
import eu.infomas.annotation.AnnotationDetector;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class PostConstructApplicationEventListener implements SystemEventListener {

    private final static Logger logger = Logger.getLogger(PostConstructApplicationEventListener.class.getName());

    public boolean isListenerForSource(Object source) {
        return true;
    }

    public void processEvent(SystemEvent event) throws AbortProcessingException {
        logger.log(Level.INFO, "Running on Xpert-framework {0}", Constants.VERSION);

        //scan for annotations
        auditing();
    }

    public void auditing() {
        AnnotationDetector.TypeReporter reporter = new AnnotationDetector.TypeReporter() {

            public void reportTypeAnnotation(Class<? extends Annotation> type, String className) {
                logger.log(Level.INFO, "Found @MetadataEntity: {0}", className);
            }

            public Class<? extends Annotation>[] annotations() {
                return new Class[]{MetadataEntity.class};
            }
        };
        AnnotationDetector detector = new AnnotationDetector(reporter);
        try {
            logger.log(Level.INFO, "detect all");
            detector.detect();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
