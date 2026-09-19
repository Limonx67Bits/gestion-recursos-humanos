package main.java.com.pbcorporations.gestion.recursos.humanos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static Connection connection;

    private DataBaseConnection() {}

    public static Connection getDBConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    Credentials.URL_DATA_BASE,
                    Credentials.USER_DATA_BASE,
                    Credentials.PASSWORD_DATA_BASE
            );
        }
        return connection;
    }
}
