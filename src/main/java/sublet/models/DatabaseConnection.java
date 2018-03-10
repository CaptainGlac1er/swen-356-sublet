package sublet.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static DatabaseConnection read;
    public static DatabaseConnection write;
    Connection conn;

    public DatabaseConnection(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }
}