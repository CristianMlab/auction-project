package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection instance = null;

    private DbConnection() {}

    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            String url = "jdbc:mysql://localhost:3306/auctions";
            String username = "root";
            String password = "";
            instance = DriverManager.getConnection(url, username, password);
        }
        return instance;
    }

}
