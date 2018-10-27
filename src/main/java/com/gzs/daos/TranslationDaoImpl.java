package com.gzs.daos;

import com.gzs.main.DBConnector;
import com.gzs.model.Translation;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TranslationDaoImpl  implements TranslationDao {

    private static String tableName;
    private static DBConnector dbConnector;
    private static Connection connection;
    private static PreparedStatement getAllStatement;
    private static PreparedStatement getByIdstatement;
    private static PreparedStatement getByTerm1Idstatement;
    private static PreparedStatement getByTerm2Idstatement;
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement deleteStatement;

    static {
        tableName = "translations";
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdstatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByTerm1Idstatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term1id = ?");
            getByTerm2Idstatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term2id = ?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET term1id=?, term2id=?, priority=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Translation> getAll() {
        List<Translation> data = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int term1ID = resultSet.getInt("Term1ID");
                int term2ID = resultSet.getInt("Term2ID");
                int priority = resultSet.getInt("Priority");
                data.add(new Translation(id, term1ID, term2ID, priority));
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Translation getById(int id) {
        Translation data = new Translation();
        ResultSet resultSet = null;
        try {
            getByIdstatement.setInt(1, id);
            resultSet = getByIdstatement.executeQuery();
            if (resultSet.next()) {
                int term1ID = resultSet.getInt("Term1ID");
                int term2ID = resultSet.getInt("Term2ID");
                int priority = resultSet.getInt("Priority");
                data = new Translation(id, term1ID, term2ID, priority);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Translation getByTerm1Id(int term1ID) {
        Translation data = new Translation();
        ResultSet resultSet = null;
        try {
            getByTerm1Idstatement.setInt(1, term1ID);
            resultSet = getByTerm1Idstatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int term2ID = resultSet.getInt("Term2ID");
                int priority = resultSet.getInt("Priority");
                data = new Translation(id, term1ID, term2ID, priority);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Translation getByTerm2Id(int term2ID) {
        Translation data = new Translation();
        ResultSet resultSet = null;
        try {
            getByTerm2Idstatement.setInt(1, term2ID);
            resultSet = getByTerm2Idstatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int term1ID = resultSet.getInt("Term1ID");
                int priority = resultSet.getInt("Priority");
                data = new Translation(id, term1ID, term2ID, priority);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public boolean insertTranslation(Translation translation) {
        if (translation!=null) {
            try {
                int i = 1;
                insertStatement.setInt(i++, translation.getId());
                insertStatement.setInt(i++, translation.getTerm1ID().getId());
                insertStatement.setInt(i++, translation.getTerm2ID().getId());
                insertStatement.setInt(i++, translation.getPriority());

                return successfulAction(insertStatement.executeUpdate());
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean updateTranslation(Translation translation) {
        if (translation!=null) {
            try {
                int i = 1;
                updateStatement.setInt(i++, translation.getTerm1ID().getId());
                updateStatement.setInt(i++, translation.getTerm2ID().getId());
                updateStatement.setInt(i++, translation.getPriority());
                updateStatement.setInt(i++, translation.getId());

                return successfulAction(updateStatement.executeUpdate());
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteTranslation(Translation translation) {
        if (translation!=null) {
            try {
                int i = 1;
                deleteStatement.setInt(i++, translation.getId());

                return successfulAction(deleteStatement.executeUpdate());
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean successfulAction(int action){
        boolean result = true;
        if (action==0) result=false;
        return result;
    }

    private static void endResultSet(ResultSet resultSet) {
        try {
            if (null != resultSet) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static void endStatements() {
        endStatemnt(getAllStatement);
        endStatemnt(getByIdstatement);
        endStatemnt(getByTerm1Idstatement);
        endStatemnt(getByTerm1Idstatement);
        endStatemnt(insertStatement);
        endStatemnt(updateStatement);
        endStatemnt(deleteStatement);
    }

    private static void endStatemnt (Statement statement) {
        try {
            if (null != statement) {
                statement.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}