package com.xpert.persistence.query;

import com.xpert.persistence.exception.QueryFileNotFoundException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Ayslan
 */
public class QueryBuilderOld {

    private String order;
    private String attributeName;
    private StringBuilder queryString = new StringBuilder();
    private Class entity;
    private int currentParameter = 1;
    private List<Restriction> restrictions = new ArrayList<Restriction>();
    private EntityManager entityManager;
    private QueryType type;
    private boolean containsWhere;
    private JoinBuilder joinBuilder;
    private static final Logger logger = Logger.getLogger(QueryBuilderOld.class.getName());

    public static void main(String[] args) {

        QueryBuilderOld queryBuilder = new QueryBuilderOld(Person.class, null, QueryType.SELECT, "nome", null);
        queryBuilder.add(new Restriction("nome", RestrictionType.NULL));
        //    queryBuilder.add(new Restriction("dataNascimento", new Date(), TemporalType.TIMESTAMP));
        queryBuilder.add(new Restriction("cpf", RestrictionType.NOT_IN, "FULANO"));
        queryBuilder.add(new Restriction("cpf", RestrictionType.NOT_LIKE, "FULANO"));

    }

    public QueryBuilderOld(Class entity, EntityManager entityManager) {
        this.entity = entity;
        this.entityManager = entityManager;
        initQueryString();
    }

    public QueryBuilderOld(Class entity, EntityManager entityManager, QueryType type) {
        this.entity = entity;
        this.entityManager = entityManager;
        this.type = type;
        initQueryString();
    }

    public QueryBuilderOld(Class entity, EntityManager entityManager, String order) {
        this.entity = entity;
        this.entityManager = entityManager;
        this.order = order;
        initQueryString();
    }

    public QueryBuilderOld(Class entity, EntityManager entityManager, String attributeName, String order) {
        this.entity = entity;
        this.entityManager = entityManager;
        this.order = order;
        this.attributeName = attributeName;
        initQueryString();
    }

    public QueryBuilderOld(Class entity, EntityManager entityManager, QueryType type, String order) {
        this.entity = entity;
        this.entityManager = entityManager;
        this.order = order;
        this.type = type;
        initQueryString();
    }

    public QueryBuilderOld(Class entity, EntityManager entityManager, QueryType type, String attributeName, String order) {
        this.entity = entity;
        this.entityManager = entityManager;
        this.order = order;
        this.type = type;
        this.attributeName = attributeName;
        initQueryString();
    }

    public Query createQuery() {
        return createQuery(null, null);
    }

    public Query createQuery(Integer maxResults) {
        return createQuery(null, maxResults);
    }

    public static Query createNativeQueryFromFile(EntityManager entityManager, String queryPath, Class daoClass) {
        return createNativeQueryFromFile(entityManager, queryPath, daoClass, null);
    }

    public static Query createNativeQueryFromFile(EntityManager entityManager, String queryPath, Class daoClass, Class resultClass) {

        InputStream inputStream = daoClass.getResourceAsStream(queryPath);
        if (inputStream == null) {
            throw new QueryFileNotFoundException("Query File not found: " + queryPath + " in package: " + daoClass.getPackage());
        }
        try {
            String queryString = readInputStreamAsString(inputStream);
            if (resultClass != null) {
                return entityManager.createNativeQuery(queryString, resultClass);
            } else {
                return entityManager.createNativeQuery(queryString);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static String readInputStreamAsString(InputStream inputStream)
            throws IOException {

        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            byte b = (byte) result;
            buf.write(b);
            result = bis.read();
        }
        bis.close();
        buf.close();
        return buf.toString();
    }

    public Query createQuery(Integer firstResult, Integer maxResults) {

        Query query = entityManager.createQuery(getQueryString());

        int parameter = 1;
        for (Restriction re : restrictions) {
            if (re.getRestrictionType().equals(RestrictionType.LIKE)) {
                if (re.getLikeType() == null || re.getLikeType().equals(LikeType.BOTH)) {
                    query.setParameter(parameter, "%" + re.getValue() + "%");
                } else if (re.getLikeType().equals(LikeType.BEGIN)) {
                    query.setParameter(parameter, "%" + re.getValue());
                } else if (re.getLikeType().equals(LikeType.END)) {
                    query.setParameter(parameter, re.getValue() + "%");
                }
            } else {
                if (re.getTemporalType() != null && (re.getValue() instanceof Date || re.getValue() instanceof Calendar)) {
                    if (re.getValue() instanceof Date) {
                        query.setParameter(parameter, (Date) re.getValue(), re.getTemporalType());
                    } else if (re.getValue() instanceof Calendar) {
                        query.setParameter(parameter, (Calendar) re.getValue(), re.getTemporalType());
                    }
                } else {
                    query.setParameter(parameter, re.getValue());
                }
            }
            parameter++;
        }

        if (maxResults != null) {
            query.setMaxResults(maxResults);
        }
        if (firstResult != null) {
            query.setFirstResult(firstResult);
        }
        return query;
    }

    public final void initQueryString() {
        this.queryString.append("FROM ").append(entity.getName());
    }

    public String getQueryString() {

        if (type == null) {
            type = QueryType.SELECT;
        }

        if (type.equals(QueryType.COUNT)) {
            this.queryString.insert(0, "SELECT COUNT(*) ");
        }

        if (type.equals(QueryType.MAX)) {
            this.queryString.insert(0, "SELECT MAX(" + attributeName + ")");
        }

        if (type.equals(QueryType.MIN)) {
            this.queryString.insert(0, "SELECT MIN(" + attributeName + ")");
        }

        if (type.equals(QueryType.SELECT) && attributeName != null && !attributeName.isEmpty()) {
            this.queryString.insert(0, "SELECT " + attributeName + " ");
        }
        if (order != null && !order.trim().isEmpty()) {
            return queryString.append(" ORDER BY ").append(order).toString();
        } else {
            return queryString.toString();
        }
    }

    public void add(List<Restriction> restrictions) {
        if (restrictions != null && !restrictions.isEmpty()) {
            for (Restriction re : restrictions) {
                add(re);
            }
        }
    }

    public void add(Restriction restriction) {

        //if RestrictionType is null set default to EQUALS
        if (restriction.getRestrictionType() == null) {
            restriction.setRestrictionType(RestrictionType.EQUALS);
        }

        //LIKE type must be only for String
        if (restriction.getRestrictionType().equals(RestrictionType.LIKE) || restriction.getRestrictionType().equals(RestrictionType.NOT_LIKE)) {
            try {
                Class propertyType = ReflectionUtils.getPropertyType(entity, restriction.getProperty());
                //set to type to EQUALS when is not String
                if (!propertyType.equals(String.class)) {
                    restriction.setRestrictionType(RestrictionType.EQUALS);
                    if (propertyType.isEnum()) {
                        restriction.setValue(Enum.valueOf(propertyType, restriction.getValue().toString()));
                    }
                    if (propertyType.equals(Long.class)) {
                        restriction.setValue(Long.valueOf(restriction.getValue().toString()));
                    }
                    if (propertyType.equals(BigDecimal.class)) {
                        restriction.setValue(new BigDecimal(restriction.getValue().toString()));
                    }
                }
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Error getting Property Type: {0}", ex.getMessage());
            }

        }


        queryString.append(" ");
        if (!containsWhere) {
            queryString.append("WHERE");
            containsWhere = true;
        } else {
            queryString.append("AND");
        }
        queryString.append(" ");
        if (restriction.getRestrictionType().equals(RestrictionType.LIKE) || restriction.getRestrictionType().equals(RestrictionType.NOT_LIKE)) {
            queryString.append("UPPER(").append(restriction.getProperty()).append(")");
        } else {
            queryString.append(restriction.getProperty());
        }
        //if Value is null set default to IS NULL
        if (restriction.getValue() == null) {
            //  EQUALS null or IS_NULL
            if (restriction.getRestrictionType().equals(RestrictionType.EQUALS) || restriction.getRestrictionType().equals(RestrictionType.NULL)) {
                queryString.append(" IS NULL ");
            } else if (restriction.getRestrictionType().equals(RestrictionType.NOT_NULL)) {
                queryString.append(" IS NOT NULL ");
            }
        } else {
            restrictions.add(restriction);
            queryString.append(" ");
            queryString.append(restriction.getRestrictionType().getSymbol());
            if (restriction.getRestrictionType().equals(RestrictionType.LIKE) || restriction.getRestrictionType().equals(RestrictionType.NOT_LIKE)) {
                queryString.append(" UPPER(?").append(currentParameter).append(")");
            } else if (restriction.getRestrictionType().equals(RestrictionType.IN) || restriction.getRestrictionType().equals(RestrictionType.NOT_IN)) {
                queryString.append(" (?").append(currentParameter).append(")");
            } else {
                queryString.append(" ?").append(currentParameter);
            }
            currentParameter++;
        }

    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public Class getEntity() {
        return entity;
    }

    public void setEntity(Class entity) {
        this.entity = entity;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public QueryType getType() {
        return type;
    }

    public void setType(QueryType type) {
        this.type = type;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
