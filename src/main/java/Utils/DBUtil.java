package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/labs_tms";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "eric";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB_URL,DB_USER, DB_PASSWORD);
    }
}

