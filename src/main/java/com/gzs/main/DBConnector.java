package com.gzs.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static volatile DBConnector instance;
    private static Connection connection = null;
    private static String dbPath;
    private static String dbName;
    private static String username;
    private static String password;

    private DBConnector() {
        connection = null;
        Configuration config = new Configuration ();
        dbPath = config.getDbPath();
        dbName = config.getDbName();
        username = config.getDbUserName();
        password = config.getDbPassword();
        try {
            connection = DriverManager.getConnection(dbPath + dbName, username, password);
        } catch (SQLException e) {
            throw new NoConnectionException(e);
        }
    }

    public static synchronized DBConnector getInstance() {
        if(instance == null){
            synchronized (DBConnector.class) {
                if(instance == null){
                    instance = new DBConnector();
                }
            }
        }
        return instance;
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