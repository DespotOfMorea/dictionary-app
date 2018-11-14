package com.gzs.daos;

import com.gzs.main.DBConnector;
import com.gzs.main.NoConnectionException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class DatabaseDao {

    protected static DBConnector dbConnector;
    protected static Connection connection;

    static {
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
    }

    protected static void endStatement(Statement statement) {
        try {
            if (null != statement) {
                statement.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public <T> List<T> getAllFromDatabase(PreparedStatement getAllStatement) {
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

    protected <T> T getFromResultSet(ResultSet resultSet) {
        return null;
    }

    protected <T> T getterFromInt(PreparedStatement statement, int num) {
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

    protected <T> T getterFromString(PreparedStatement statement, String s) {
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

    protected <T> T nullCheck(T t, T newT) {
        if (t == null) {
            t = newT;
        }
        return t;
    }

    protected boolean deleteFromDatabase(PreparedStatement deleteStatement, int id) {
        try {
            int i = 1;
            deleteStatement.setInt(i++, id);
            return successfulAction(deleteStatement.executeUpdate());
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    protected boolean successfulAction(int action) {
        boolean result = true;
        if (action == 0) result = false;
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
}
