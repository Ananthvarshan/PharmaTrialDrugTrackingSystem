package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Single static connection
    private static Connection conn = null;

    // --- Get connection ---
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                // --- Update these according to your PostgreSQL setup ---
                String url = "jdbc:postgresql://localhost:5432/pharma_java_db";
                String user = "postgres";
                String password = "1234";

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Database connected successfully!");

            } catch (SQLException e) {
                System.out.println("❌ Database connection failed: " + e.getMessage());
                throw e;
            }
        }
        return conn;
    }

    // --- Close connection ---
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing connection: " + e.getMessage());
        }
    }
}
