package com.gzs.daos;

import com.gzs.main.DBConnector;
import com.gzs.main.DBMethods;
import com.gzs.model.Translation;

import java.sql.*;
import java.util.List;

public class TranslationDaoImpl  implements TranslationDao {

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    private static String tableName;
    private static PreparedStatement getTranslationByTerm1IdPrpStmt;
    private static DBConnector dbConnector;

    public TranslationDaoImpl () {
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
        statement = null;
        resultSet = null;
        tableName = "translations";
        try {
            getTranslationByTerm1IdPrpStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term1id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Translation> getAll() {
        return null;
    }

    @Override
    public Translation getById(int id) {
        Translation data = new Translation();
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int term1ID = resultSet.getInt("Term1ID");
                int term2ID = resultSet.getInt("Term2ID");
                int priority = resultSet.getInt("Priority");
                data = new Translation(id, term1ID, term2ID, priority);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Translation getByTerm1Id(int term1ID) {
        Translation data = new Translation();
        try {
            getTranslationByTerm1IdPrpStmt.setInt(1,term1ID);
            resultSet = getTranslationByTerm1IdPrpStmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int term2ID = resultSet.getInt("Term2ID");
                int priority = resultSet.getInt("Priority");
                data = new Translation(id, term1ID, term2ID, priority);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Translation getByTerm2Id(int term2ID) {
        Translation data = new Translation();
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term2id = ?");
            statement.setInt(1, term2ID);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int term1ID = resultSet.getInt("Term1ID");
                int priority = resultSet.getInt("Priority");
                data = new Translation(id, term1ID, term2ID, priority);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public boolean insertTranslation(Translation translation) {
        if (translation!=null) {
            try {
                statement = connection.prepareStatement("INSERT INTO " + tableName + " (term1id, term2id, priority) " +
                        "SELECT * FROM (SELECT ?, ?, ?) AS tmp WHERE NOT EXISTS " +
                        "(SELECT * FROM  " + tableName + " WHERE term1id=? AND term2id=? AND priority=?) LIMIT 1");
                int i = 1;
                statement.setInt(i++, translation.getTerm1ID().getId());
                statement.setInt(i++, translation.getTerm2ID().getId());
                statement.setInt(i++, translation.getPriority());
                statement.setInt(i++, translation.getTerm1ID().getId());
                statement.setInt(i++, translation.getTerm2ID().getId());
                statement.setInt(i++, translation.getPriority());

                return successfulAction(statement.executeUpdate());
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            } finally {
                endConn(connection, resultSet, statement);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean updateTranslation(Translation translation) {
        if (translation!=null) {
            try {
                statement = connection.prepareStatement("UPDATE " + tableName + " SET term1id=?, term2id=?, priority=? WHERE id=?");
                int i = 1;
                statement.setInt(i++, translation.getTerm1ID().getId());
                statement.setInt(i++, translation.getTerm2ID().getId());
                statement.setInt(i++, translation.getPriority());
                statement.setInt(i++, translation.getId());

                return successfulAction(statement.executeUpdate());
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            } finally {
                endConn(connection, resultSet, statement);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteTranslation(Translation translation) {
        if (translation!=null) {
            try {
                statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
                int i = 1;
                statement.setInt(i++, translation.getId());

                return successfulAction(statement.executeUpdate());
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            } finally {
                endConn(connection, resultSet, statement);
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

    private static void endConn(Connection connection, ResultSet resultSet, Statement statement) {
        try {
            if (null != connection) {
                if (null != resultSet) {
                    resultSet.close();
                }
                if (null != statement) {
                    statement.close();
                }
//                connection.close();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
