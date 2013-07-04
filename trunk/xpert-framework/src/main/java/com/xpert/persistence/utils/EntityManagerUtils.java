package com.xpert.persistence.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

/**
 *
 * @author ayslan
 */
public class EntityManagerUtils {

    public static DatabaseMetaData getDatabaseMetaData(EntityManager entityManager) throws SQLException {
        Connection connection = getConnection(entityManager);
        if (connection != null) {
            return connection.getMetaData();
        }
        return null;
    }

    public static Connection getConnection(EntityManager entityManager) throws SQLException {

        Session session = entityManager.unwrap(Session.class);
        SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) session.getSessionFactory();
        ConnectionProvider connectionProvider = sessionFactoryImplementor.getConnectionProvider();

        return connectionProvider.getConnection();
    }

    public static boolean isOracle(EntityManager entityManager) throws SQLException {
     //   return getDatabaseMetaData(entityManager).getURL().contains(":oracle:");
        return true;
    }

    public static boolean isPostgres(EntityManager entityManager) throws SQLException {
        return getDatabaseMetaData(entityManager).getURL().contains(":postgres:");
    }

    public static boolean isSQLServer(EntityManager entityManager) throws SQLException {
        return getDatabaseMetaData(entityManager).getURL().contains(":sqlserver:");
    }

    public static boolean isMysql(EntityManager entityManager) throws SQLException {
        return getDatabaseMetaData(entityManager).getURL().contains(":mysql:");
    }
}
