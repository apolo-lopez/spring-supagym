package com.codemystack.apps.supagym.multitenancy;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component("multiTenantConnectionProvider")
public class SchemaConnectionProvider implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;

    public SchemaConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = getAnyConnection();
        try {
            // Cambiar schema a nivel de conexión
            connection.createStatement()
                    .execute("SET search_path TO " + tenantIdentifier + ", public");
        } catch (SQLException e) {
            throw new SQLException("Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            // Volver al schema por defecto
            connection.createStatement().execute("SET search_path TO public");
        } catch (SQLException e) {
            throw new SQLException("Could not reset JDBC connection to default schema", e);
        } finally {
            releaseAnyConnection(connection);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    // Hibernate 6: estos dos métodos son los correctos

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return unwrapType.isAssignableFrom(getClass()) || unwrapType.isAssignableFrom(DataSource.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> unwrapType) {
        if (unwrapType.isAssignableFrom(getClass())) {
            return (T) this;
        }
        if (unwrapType.isAssignableFrom(DataSource.class)) {
            return (T) dataSource;
        }
        throw new UnknownUnwrapTypeException(unwrapType);
    }
}
