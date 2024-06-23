package com.esde.webdevtask1.pool;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.sql.DataSource;
import java.sql.*;

public class DatabasePool {
    private static final Logger logger = LogManager.getLogger();

    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    public static final String URL = "jdbc:postgresql://localhost:5432/webdev";
    public static final String POSTGRES_USERNAME = "postgres";
    public static final String POSTGRES_PASSWORD = "postgres";

    private static final String CREATE_USERS_TABLE_IF_NOT_EXISTS = """
        CREATE TABLE IF NOT EXISTS public.users (
            id SERIAL PRIMARY KEY,
            username TEXT NOT NULL,
            email TEXT UNIQUE NOT NULL,
            password TEXT NOT NULL,
            verified BOOLEAN DEFAULT FALSE,
            user_file bytea
        );
""";

    private static final String CREATE_CONTACTS_TABLE_IF_NOT_EXISTS = """
       CREATE TABLE IF NOT EXISTS public.contacts
       (
           id SERIAL PRIMARY KEY,
           name text NOT NULL,
           number text NOT NULL,
           userid INTEGER,
           CONSTRAINT contacts_fk FOREIGN KEY (userid)
               REFERENCES public.users (id) MATCH SIMPLE
               ON UPDATE CASCADE
               ON DELETE CASCADE
       )
    """;

    private static DataSource dataSource;

    public static void initializeDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DRIVER_CLASS_NAME);
        basicDataSource.setUrl(URL);
        basicDataSource.setUsername(POSTGRES_USERNAME);
        basicDataSource.setPassword(POSTGRES_PASSWORD);

        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(10);

        dataSource = basicDataSource;

        try (final var connection = getConnection()) {
            try (Statement statement = connection.createStatement()){
                statement.execute(CREATE_USERS_TABLE_IF_NOT_EXISTS);
                statement.execute(CREATE_CONTACTS_TABLE_IF_NOT_EXISTS);
            }
        } catch(SQLException e){
            logger.fatal("Failed to initialize database", e);
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            try {
                dataSource.getConnection().close();
            } catch (SQLException e) {
                logger.error("Failed to close data source", e);
            }
        }
    }
}

