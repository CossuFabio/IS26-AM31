package it.polimi.ingsw.am31.am31.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectionFactory {

    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    private static boolean isInitialized = false;

    //Makes it private so the class cannot be instantiated
    private DataBaseConnectionFactory() {}

    public static void initialize(String dbUrl, String dbUsername, String dbPassword, String dbDriver) {

        //Static initialization
        DataBaseConnectionFactory.dbUrl = dbUrl;
        DataBaseConnectionFactory.dbUsername = dbUsername;
        DataBaseConnectionFactory.dbPassword = dbPassword;

        try {
            Class.forName(dbDriver);
            isInitialized = true;
        }catch(ClassNotFoundException e) {
            System.err.println("Unable to retrieve connection driver");
        }


    }


    public static Connection getNewConnection() throws SQLException {
        if(!isInitialized) throw new IllegalStateException("Factory must be initialized before connecting");
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        }catch(Exception ignored) {}
    }

    public static boolean isIsInitialized(){
        return isInitialized;
    }



}
