package it.polimi.ingsw.am31.am31.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnectionFactory {

    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    private static boolean isInitialized = false;

    //Makes it private so the class cannot be instantiated
    private DataBaseConnectionFactory() {}


    //Overload: init with default values or init with custom
    public static void initialize() throws IOException, ClassNotFoundException {

        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("database.properties");
        if (stream == null) throw new IOException("database.properties not found in classpath");

        try (stream) {
            properties.load(stream);
            initialize(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"),
                    properties.getProperty("db.driver")
            );
        }

        //stream self closes using try-with-resources


    }

    public static void initialize(String dbUrl, String dbUsername, String dbPassword, String dbDriver) throws ClassNotFoundException {

        //Static initialization
        DataBaseConnectionFactory.dbUrl = dbUrl;
        DataBaseConnectionFactory.dbUsername = dbUsername;
        DataBaseConnectionFactory.dbPassword = dbPassword;

        Class.forName(dbDriver);
        isInitialized = true;

    }


    public static Connection getNewConnection() throws SQLException {
        if (!isInitialized) throw new IllegalStateException("Factory must be initialized before connecting");
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ignored) {}
    }

    public static boolean isInitialized() {
        return isInitialized;
    }


}
