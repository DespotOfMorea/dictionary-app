package com.gzs.main;

import java.sql.*;

public class DBConnector {

    private static DBConnector instance;
    private static Connection connection = null;
    private static String dbPath = "jdbc:mysql://localhost/";
    private static String dbName = "geodictionary";
    private static String username = "root";
    private static String password = "";

    private DBConnector(){
        connection = null;
        dbPath = "jdbc:mysql://localhost/";
        dbName = "geodictionary";
        username = "root";
        password = "";
        try {
            createConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DBConnector getInstance(){
        if(instance == null){
            synchronized (DBConnector.class) {
                if(instance == null){
                    instance = new DBConnector();
                }
            }
        }
        return instance;
    }


    private static void createConn() throws SQLException {
        connection = DriverManager.getConnection(dbPath + dbName, username, password);
    }

    public static Connection getConn(){
        return connection;
    }

    private static void endConn() {
        try {
            if (null != connection) {
               connection.close();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
