package com.gzs.daos;

import com.gzs.main.DBConnector;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    protected abstract <T> T getFromResultSet(ResultSet resultSet);

    protected <T> Optional<T> getterFromInt(PreparedStatement statement, int num) {
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
        return Optional.ofNullable(data);
    }

    protected <T> Optional<T> getterFromString(PreparedStatement statement, String s) {
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
        return Optional.ofNullable(data);
    }

    protected boolean deleteFromDatabase(PreparedStatement deleteStatement, int id) {
        try {
            int i = 1;
            deleteStatement.setInt(i++, id);
            return deleteStatement.executeUpdate()==1;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
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
