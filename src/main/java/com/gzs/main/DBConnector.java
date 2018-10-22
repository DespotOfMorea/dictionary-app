package com.gzs.main;

import java.sql.*;

public class DBConnector {

    // Local variables with connection parameters.
    static Connection connection = null;
    private static String dbPath = "jdbc:mysql://localhost/";
    private static String dbName = "geodictionary";
    private static String username = "root";
    private static String password = "";

    static {
        try {
            createConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
