package com.gzs.daos;

import com.gzs.main.DBConnector;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class DatabaseDao {
    protected static String tableName;
    protected static DBConnector dbConnector;
    protected static Connection connection;
    protected static List<Statement> statements;
    protected static PreparedStatement getAllStatement;
    protected static PreparedStatement getByIdStatement;
    protected static PreparedStatement insertStatement;
    protected static PreparedStatement updateStatement;
    protected static PreparedStatement deleteStatement;

    static {
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
        statements = new ArrayList<>();
        statements.add(getAllStatement);
        statements.add(getByIdStatement);
        statements.add(insertStatement);
        statements.add(updateStatement);
        statements.add(deleteStatement);
    }

    public <T> List<T> getAllFromDatabase() {
        List<T> data = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                data.add(getFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    protected <T> T getFromResultSet (ResultSet resultSet){
        return null;
    }

    protected <T> T getterFromInt(PreparedStatement statement,int num) {
        T data = null;
        ResultSet resultSet = null;
        try {
            statement.setInt(1, num);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                data = getFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    protected <T> T getterFromString(PreparedStatement statement,String s) {
        T data = null;
        ResultSet resultSet = null;
        try {
            statement.setString(1, s);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                data = getFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    protected boolean deleteFromDatabase(int id) {
            try {
                int i = 1;
                deleteStatement.setInt(i++, id);
                return successfulAction(deleteStatement.executeUpdate());
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
    }

    protected boolean successfulAction(int action){
        boolean result = true;
        if (action==0) result=false;
        return result;
    }

    protected void endResultSet(ResultSet resultSet) {
        try {
            if (null != resultSet) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    protected void endStatement(Statement statement) {
        try {
            if (null != statement) {
                statement.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    protected void endStatements() {
        statements.forEach(statement -> endStatement(statement));
    }
}
