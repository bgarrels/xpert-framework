package com.xpert.persistence.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Ayslan
 */
public class QueryBuilder {

    private String order;
    private String attributeName;
    private Class from;
    private String alias;
    private StringBuilder joins = new StringBuilder();
    private List<Restriction> restrictions = new ArrayList<Restriction>();
    private QueryType type;
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(QueryBuilder.class.getName());

    public QueryBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public QueryBuilder(EntityManager entityManager, String alias) {
        this.entityManager = entityManager;
        this.alias = alias;
    }

    public QueryBuilder from(Class from) {
        this.from = from;
        return this;
    }

    public QueryBuilder from(Class from, String alias) {
        this.from = from;
        this.alias = alias;
        return this;
    }

    public QueryBuilder leftJoin(String join) {
        this.joins.append("LEFT JOIN FETCH ").append(join).append(" ");
        return this;
    }

    public QueryBuilder innerJoin(String join) {
        this.joins.append("INNER JOIN FETCH ").append(join).append(" ");
        return this;
    }

    public QueryBuilder join(String join) {
        this.joins.append("JOIN FETCH ").append(join).append(" ");
        return this;
    }

    public QueryBuilder join(JoinBuilder joinBuilder) {
        if (joinBuilder != null) {
            this.alias = joinBuilder.getAlias();
            this.joins.append(joinBuilder);
        }
        return this;
    }

    public QueryBuilder orderBy(String order) {
        this.order = order;
        return this;
    }

    public QueryBuilder type(QueryType type) {
        this.type = type;
        return this;
    }

    public QueryBuilder type(QueryType type, String attributeName) {
        this.type = type;
        this.attributeName = attributeName;
        return this;
    }

    public QueryBuilder add(List<Restriction> restrictions) {
        if (restrictions != null) {
            this.restrictions.addAll(restrictions);
        }
        return this;
    }

    public QueryBuilder add(Map<String, Object> restrictions) {
        if (restrictions != null) {
            for (Map.Entry e : restrictions.entrySet()) {
                this.restrictions.add(new Restriction(e.getKey().toString(), e.getValue()));
            }
        }
        return this;
    }

    public QueryBuilder add(Restriction restriction) {
        if (restriction != null) {
            this.restrictions.add(restriction);
        }
        return this;
    }

    public String getQueryString() {

        StringBuilder queryString = new StringBuilder();
        //type
        if (type == null) {
            type = QueryType.SELECT;
        }

        if (type.equals(QueryType.COUNT)) {
            queryString.append("SELECT COUNT(*) ");
        }

        if (type.equals(QueryType.MAX)) {
            queryString.append("SELECT MAX(").append(attributeName).append(") ");
        }

        if (type.equals(QueryType.MIN)) {
            queryString.append("SELECT MIN(").append(attributeName).append(") ");
        }

        if (type.equals(QueryType.SUM)) {
            queryString.append("SELECT SUM(").append(attributeName).append(") ");
        }

        if (type.equals(QueryType.SELECT) && attributeName != null && !attributeName.isEmpty()) {
            queryString.append("SELECT ").append(attributeName).append(" ");
        }

        queryString.append("FROM ").append(from.getName()).append(" ");

        if (alias != null) {
            queryString.append(alias).append(" ");
        }

        if (joins != null) {
            queryString.append(joins).append(" ");
        }

        //restrictions
        int currentParameter = 1;
        boolean containsWhere = false;

        for (Restriction restriction : restrictions) {
            String propertyName;
            if (alias != null && !alias.trim().isEmpty()) {
                propertyName = alias + "." + restriction.getProperty();
            } else {
                propertyName = restriction.getProperty();
            }
            //if RestrictionType is null set default to EQUALS
            if (restriction.getRestrictionType() == null) {
                restriction.setRestrictionType(RestrictionType.EQUALS);
            }

            //LIKE type must be only for String
            if (restriction.getRestrictionType().equals(RestrictionType.LIKE) || restriction.getRestrictionType().equals(RestrictionType.NOT_LIKE)) {
                String property = "";
                if (alias != null && !alias.trim().isEmpty() && restriction.getProperty().indexOf(".") > -1) {
                    property = restriction.getProperty().substring(restriction.getProperty().indexOf(".") + 1, restriction.getProperty().length());
                } else {
                    property = restriction.getProperty();
                }
                try {
                    Class propertyType = ReflectionUtils.getPropertyType(this.from, property);
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

            if (!containsWhere) {
                queryString.append("WHERE");
                containsWhere = true;
            } else {
                queryString.append("AND");
            }
            queryString.append(" ");
            if (restriction.getRestrictionType().equals(RestrictionType.LIKE) || restriction.getRestrictionType().equals(RestrictionType.NOT_LIKE)) {
                queryString.append("UPPER(").append(propertyName).append(")").append(" ");
            } else {
                queryString.append(propertyName);
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
                queryString.append(restriction.getRestrictionType().getSymbol()).append(" ");
                if (restriction.getRestrictionType().equals(RestrictionType.LIKE) || restriction.getRestrictionType().equals(RestrictionType.NOT_LIKE)) {
                    queryString.append("UPPER(?").append(currentParameter).append(")");
                } else if (restriction.getRestrictionType().equals(RestrictionType.IN) || restriction.getRestrictionType().equals(RestrictionType.NOT_IN)) {
                    queryString.append("(?").append(currentParameter).append(")");
                } else {
                    queryString.append("?").append(currentParameter);
                }
                queryString.append(" ");
                currentParameter++;
            }
        }
        //order by
        if (order != null && !order.trim().isEmpty()) {
            queryString.append("ORDER BY ").append(order).toString();
        }

        return queryString.toString();
    }

    public Query createQuery() {
        return createQuery(null);
    }

    public Query createQuery(Integer maxResults) {
        return createQuery(null, maxResults);
    }

    public Query createQuery(Integer firstResult, Integer maxResults) {

        String queryString = getQueryString();
        Query query = entityManager.createQuery(queryString);

        int parameter = 1;
        for (Restriction re : restrictions) {
            if (re.getValue() != null) {
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

    public static void main(String[] args) {

        QueryBuilder builder =
                new QueryBuilder(null, "p").from(Person.class).type(QueryType.SELECT, null).leftJoin("p.group").leftJoin("p.profile").add(new Restriction("cpf", RestrictionType.LIKE, LikeType.BOTH)).add(new Restriction("nome", "maria")).orderBy("p.nome");

        builder.add(new Restriction("nome", null));

    }
}
