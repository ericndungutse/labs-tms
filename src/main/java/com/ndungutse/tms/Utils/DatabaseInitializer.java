package com.ndungutse.tms.Utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting database initialization");

        try {
            Class.forName("org.postgresql.Driver"); // explicitly load driver
            logger.debug("PostgreSQL JDBC Driver loaded successfully");

            try (Connection conn = DBUtil.getConnection();
                 Statement stmt = conn.createStatement()) {

                // Enable pgcrypto extension (needed for gen_random_uuid())
                String enableExtension = "CREATE EXTENSION IF NOT EXISTS pgcrypto";
                stmt.executeUpdate(enableExtension);
                logger.info("pgcrypto extension enabled or already exists");

                // Create table with UUID id
                String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                        "id UUID PRIMARY KEY DEFAULT gen_random_uuid()," +
                        "title VARCHAR(255) NOT NULL," +
                        "description TEXT," +
                        "dueDate DATE," +
                        "status VARCHAR(20) NOT NULL" +
                        ")";
                stmt.executeUpdate(sql);
                logger.info("Table 'tasks' created or already exists");
            }

            logger.info("Database initialization completed successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize database", e);
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
