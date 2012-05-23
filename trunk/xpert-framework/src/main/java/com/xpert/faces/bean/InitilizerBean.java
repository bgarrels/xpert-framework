package com.xpert.faces.bean;

import com.xpert.DAO;
import com.xpert.faces.utils.FacesUtils;
import com.xpert.persistence.dao.BaseDAO;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import org.hibernate.LazyInitializationException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class InitilizerBean {

    private static final Logger logger = Logger.getLogger(InitilizerBean.class.getName());
    private Map<ClassIdentifier, Object> cache = new HashMap<ClassIdentifier, Object>();
    private BaseDAO baseDAO;

    public InitilizerBean() {
        try {
            baseDAO = new DAO();
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Error on InitilizerBean Cosntructor.", t);
        }
    }

    @PostConstruct
    public void init() {
    }

    public void initialize(ComponentSystemEvent event) {
        try {
            initialize(event.getComponent(), FacesContext.getCurrentInstance());
        } catch (Throwable t) {
            logger.log(Level.SEVERE, null, t);
        }
    }

    public String getParentExpression(String expression) {

        if (expression != null && !expression.isEmpty()) {
            int index = expression.lastIndexOf(".");
            if (index > -1) {
                return expression.substring(0, index) + "}";
            }
        }

        return expression;
    }

    public void initializeValue(String expression) {

        Object value = null;

        try {
            value = FacesUtils.getBeanByEl(expression);
        } catch (ELException ex) {
            if (ex.getCause() != null && ex.getCause() instanceof LazyInitializationException) {
                //try to initializer parent expression
                initializeValue(getParentExpression(expression));
                //get expression now initialized
                value = FacesUtils.getBeanByEl(expression);
            }
        }

        LazyInitializer lazyInitializer = null;

        if (value instanceof HibernateProxy) {
            lazyInitializer = ((HibernateProxy) value).getHibernateLazyInitializer();
            Object cached = cache.get(new ClassIdentifier(lazyInitializer.getIdentifier(), lazyInitializer.getPersistentClass()));
            if (cached != null) {
                FacesUtils.setValueEl(expression, cached);
                return;
            }

        }

        if (value instanceof HibernateProxy || value instanceof PersistentCollection) {
            Object initialized = baseDAO.getInitialized(value);
            if (value instanceof HibernateProxy) {
                cache.put(new ClassIdentifier(lazyInitializer.getIdentifier(), lazyInitializer.getPersistentClass()), initialized);
            }
            FacesUtils.setValueEl(expression, initialized);
        }
    }

    public void initialize(UIComponent component, FacesContext context) {
        ValueExpression valueExpression = component.getValueExpression("value");
        String expression = valueExpression.getExpressionString();
        initializeValue(expression);
    }

    public class ClassIdentifier {

        private Object id;
        private Class entity;

        public ClassIdentifier(Object id, Class entity) {
            this.id = id;
            this.entity = entity;
        }

        public Class getEntity() {
            return entity;
        }

        public void setEntity(Class entity) {
            this.entity = entity;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ClassIdentifier other = (ClassIdentifier) obj;
            if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
                return false;
            }
            if (this.entity != other.entity && (this.entity == null || !this.entity.equals(other.entity))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
            hash = 97 * hash + (this.entity != null ? this.entity.hashCode() : 0);
            return hash;
        }
    }
}
