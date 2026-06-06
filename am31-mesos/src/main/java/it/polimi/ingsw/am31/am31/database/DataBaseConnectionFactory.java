package it.polimi.ingsw.am31.am31.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Static factory that provides JDBC connections to the historical leaderboard database. */
public class DataBaseConnectionFactory {

    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    private static boolean isInitialized = false;

    //Makes it private so the class cannot be instantiated
    private DataBaseConnectionFactory() {}



    /**
     * Initializes the factory with default settings loaded from the properties file.
     * @throws IOException if it is not possible to access the properties file
     * @throws ClassNotFoundException if the JDBC driver class cannot be loaded
     */
    public static void initialize() throws IOException, ClassNotFoundException {

        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        File externalFile = new File("database.properties");
        InputStream stream = externalFile.exists()
                ? new FileInputStream(externalFile)
                : loader.getResourceAsStream("database.properties");
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

    /**
     * Initializes the factory with custom connection settings.
     * @param dbUrl JDBC URL of the database
     * @param dbUsername database username
     * @param dbPassword database password
     * @param dbDriver JDBC driver class name
     * @throws ClassNotFoundException if the JDBC driver class cannot be loaded
     */
    public static void initialize(String dbUrl, String dbUsername, String dbPassword, String dbDriver) throws ClassNotFoundException {

        //Static initialization
        DataBaseConnectionFactory.dbUrl = dbUrl;
        DataBaseConnectionFactory.dbUsername = dbUsername;
        DataBaseConnectionFactory.dbPassword = dbPassword;

        Class.forName(dbDriver);
        isInitialized = true;
        try (Connection connection = getNewConnection()) {
            if (!connection.isValid(3))
                throw new RuntimeException("Unable to access database!");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to access database!"+e.getMessage());
        }

    }

    /**
     * Factory method that returns a new connection to the database
     * @return the requested connection
     * @throws SQLException if unable to get a new connection
     * @throws IllegalStateException if the factory is not initialized
     */
    public static Connection getNewConnection() throws SQLException {
        if (!isInitialized) throw new IllegalStateException("Factory must be initialized before connecting");
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    /**
     * Closes the given database connection, ignoring any errors.
     * @param connection the connection to close
     */
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ignored) {}
    }

    /**
     * @return the initialization status of the factory
     */
    public static boolean isInitialized() {
        return isInitialized;
    }


}
