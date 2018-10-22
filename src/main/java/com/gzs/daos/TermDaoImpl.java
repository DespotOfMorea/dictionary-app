package com.gzs.daos;

import com.gzs.main.DBConnector;
import com.gzs.main.DBMethods;
import com.gzs.model.Language;
import com.gzs.model.Term;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TermDaoImpl implements TermDao {

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    private static String tableName;
    private static PreparedStatement getTermByIDPrpStmt;
    private static PreparedStatement getTermByTermPrpStmt;
    private static DBConnector dbConnector;

    public TermDaoImpl () {
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
        statement = null;
        resultSet = null;
        tableName = "terms";
        try {
            getTermByIDPrpStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getTermByTermPrpStmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Term> getAll() {
        ArrayList<Term> data = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String term = resultSet.getString("Term");
                String meaning = resultSet.getString("Meaning");
                int languageID = resultSet.getInt("LanguageID");
                data.add(new Term(id, term, meaning, languageID));
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Term getById(int id) {
        Term data = new Term();
        try {
            getTermByIDPrpStmt.setInt(1,id);
            resultSet = getTermByIDPrpStmt.executeQuery();
            if (resultSet.next()) {
                String term = resultSet.getString("Term");
                String meaning = resultSet.getString("Meaning");
                int languageID = resultSet.getInt("LanguageID");
                data = new Term(id, term, meaning, languageID);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Term getByTerm(String term) {
        Term data = new Term();
        try {
            getTermByTermPrpStmt.setString(1,term);
            resultSet = getTermByTermPrpStmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String meaning = resultSet.getString("Meaning");
                int languageID = resultSet.getInt("LanguageID");
                data = new Term(id, term, meaning, languageID);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public boolean insertTerm(Term term) {
        if (term!=null) {
            try {
                statement = connection.prepareStatement("INSERT INTO " + tableName + " (term, meaning, languageID) " +
                        "SELECT * FROM (SELECT ?, ?, ?) AS tmp WHERE NOT EXISTS " +
                        "(SELECT * FROM  " + tableName + " WHERE term=? AND meaning=? AND languageID=?) LIMIT 1");
                int i = 1;
                statement.setString(i++, term.getTerm());
                statement.setString(i++, term.getMeaning());
                statement.setInt(i++, term.getLanguage().getId());
                statement.setString(i++, term.getTerm());
                statement.setString(i++, term.getMeaning());
                statement.setInt(i++, term.getLanguage().getId());

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
    public boolean updateTerm(Term term) {
        if (term!=null) {
            try {
                statement = connection.prepareStatement("UPDATE " + tableName + " SET term=?, meaning=?, languageID=? WHERE id=?");
                int i = 1;
                statement.setString(i++, term.getTerm());
                statement.setString(i++, term.getMeaning());
                statement.setInt(i++, term.getLanguage().getId());
                statement.setInt(i++, term.getId());

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
    public boolean deleteTerm(Term term) {
        if (term!=null) {
            try {
                statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
                int i = 1;
                statement.setInt(i++, term.getId());

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