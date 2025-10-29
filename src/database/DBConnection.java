package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // !!! IMPORTANT: CHANGE YOUR PASSWORD HERE !!!
    private static final String URL = "jdbc:mysql://localhost:3306/student_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password_here";

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("Database connection successful!"); // Optional print
            return con;
        } catch (SQLException e) {
            System.err.println("Database connection failed. Check your URL, USER, and PASSWORD.");
            throw e;
        }
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}