/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.showcase.mb;

import com.xpert.persistence.query.QueryBuilder;
import com.xpert.persistence.query.RestrictionType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ayslan
 */
@ManagedBean
@ViewScoped
public class EntitySearchBean implements Serializable {

    private EntitySearch entitySearch = new EntitySearch();
    private List<EntitySearchField> fields = new ArrayList<EntitySearchField>();
    private String order;
    private List<String> orderList;

    public void search() {

        QueryBuilder queryBuilder = new QueryBuilder(entitySearch.getBaseDAO().getEntityManager());
        queryBuilder.from(entitySearch.getForClass());
        for (EntitySearchField entitySearchField : fields) {
            if (entitySearchField.getValue() != null && !entitySearchField.getValue().toString().isEmpty()) {
                queryBuilder.add(entitySearchField.getProperty(), entitySearchField.getRestrictionType(), entitySearchField.getValue());
            }
        }
        queryBuilder.orderBy(orderList);
        entitySearch.setValue(queryBuilder.getResultList());
    }

    public void addField(String propertyName, String property, String options, boolean required, String requiredMessage, List filterOptions, String filterOptionsLabel) {
        if (fields == null) {
            fields = new ArrayList<EntitySearchField>();
        }
        boolean contains = false;
        for (EntitySearchField entitySearchField : fields) {
            if (entitySearchField.getProperty().equals(property)) {
                contains = true;
                break;
            }
        }
        if (contains == false) {
            fields.add(new EntitySearchField(propertyName, property, options, required, requiredMessage, filterOptions, filterOptionsLabel));
        }
    }

    public void addOrder(String order) {
        if(orderList == null){
            orderList = new ArrayList<String>();
        }
        if (!orderList.contains(order)) {
            this.orderList.add(order);
        }
    }

    public EntitySearch getEntitySearch() {
        return entitySearch;
    }

    public void setEntitySearch(EntitySearch entitySearch) {
        this.entitySearch = entitySearch;
    }

    public List<EntitySearchField> getFields() {
        return fields;
    }

    public void setFields(List<EntitySearchField> fields) {
        this.fields = fields;
    }

    public List<String> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<String> orderList) {
        this.orderList = orderList;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    
    

}
