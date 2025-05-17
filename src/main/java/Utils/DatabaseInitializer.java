package Utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.postgresql.Driver"); // explicitly load driver
            try (Connection conn = DBUtil.getConnection();
                 Statement stmt = conn.createStatement()) {

                // Enable pgcrypto extension (needed for gen_random_uuid())
                String enableExtension = "CREATE EXTENSION IF NOT EXISTS pgcrypto";
                stmt.executeUpdate(enableExtension);

                // Create table with UUID id
                String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                        "id UUID PRIMARY KEY DEFAULT gen_random_uuid()," +
                        "title VARCHAR(255) NOT NULL," +
                        "description TEXT," +
                        "due_date DATE," +
                        "status VARCHAR(20) NOT NULL" +
                        ")";
                stmt.executeUpdate(sql);
                System.out.println("Tables created/verified successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
