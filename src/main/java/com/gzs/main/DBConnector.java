package com.gzs.main;

import com.typesafe.config.Config;

import java.sql.*;

public class DBConnector {

    private static volatile DBConnector instance;
    private static Connection connection = null;
    private static Config config;
    private static String dbPath;
    private static String dbName;
    private static String username;
    private static String password;

    private DBConnector(){
        connection = null;
        Configuration configuration = Configuration.getInstance();
        config = configuration.getConfiguration();
        dbPath = config.getString("database.path");
        dbName = config.getString("database.name");
        username = config.getString("database.username");
        password = config.getString("database.password");
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


    private void createConn() throws SQLException {
        connection = DriverManager.getConnection(dbPath + dbName, username, password);
    }

    public Connection getConn(){
        return connection;
    }

    public void endConn() {
        try {
            if (null != connection) {
               connection.close();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
