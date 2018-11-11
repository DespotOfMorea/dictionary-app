package com.gzs.daos;

import com.gzs.main.DBConnector;
import com.gzs.model.Term;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.gzs.daos.LanguageDaoImpl.successfulAction;

@Slf4j
public class TermDaoImpl implements TermDao {

    private static String tableName;
    private static DBConnector dbConnector;
    private static Connection connection;
    private static PreparedStatement getAllStatement;
    private static PreparedStatement getByIdStatement;
    private static PreparedStatement getByTermStatement;
    private static PreparedStatement getByTermLangStatement;
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement deleteStatement;

    static {
        tableName = "terms";
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByTermStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term = ?");
            getByTermLangStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term = ? AND languageID=?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET term=?, meaning=?, languageID=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Term> getAll() {
        List<Term> data = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                data.add(getTermFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Term get(int id) {
        Term data = new Term();
        ResultSet resultSet = null;
        try {
            getByIdStatement.setInt(1,id);
            resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                data = getTermFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Term getByTerm(String term) {
        Term data = new Term();
        ResultSet resultSet = null;
        try {
            getByTermStatement.setString(1,term);
            resultSet = getByTermStatement.executeQuery();
            if (resultSet.next()) {
                data = getTermFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return data;
    }

    @Override
    public Term getByTermLang(String term,int langId) {
        Term data = new Term();
        ResultSet resultSet = null;
        try {
            getByTermLangStatement.setString(1,term);
            getByTermLangStatement.setInt(2,langId);
            resultSet = getByTermLangStatement.executeQuery();
            if (resultSet.next()) {
                data = getTermFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return data;
    }

    private Term getTermFromResultSet(ResultSet resultSet) {
        Term returnTerm = new Term();
        try {
            int id = resultSet.getInt("ID");
            String term = resultSet.getString("Term");
            String meaning = resultSet.getString("Meaning");
            int languageID = resultSet.getInt("LanguageID");
            returnTerm = new Term(id, term, meaning, languageID);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return returnTerm;
    }

    @Override
    public boolean insert(Term term) {
        if (term!=null) {
            try {
                int i = 1;
                insertStatement.setInt(i++, term.getId());
                insertStatement.setString(i++, term.getTerm());
                insertStatement.setString(i++, term.getMeaning());
                insertStatement.setInt(i++, term.getLanguage().getId());

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
    public boolean update(Term term) {
        if (term!=null) {
            try {
                int i = 1;
                updateStatement.setString(i++, term.getTerm());
                updateStatement.setString(i++, term.getMeaning());
                updateStatement.setInt(i++, term.getLanguage().getId());
                updateStatement.setInt(i++, term.getId());

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
    public boolean delete(Term term) {
        if (term!=null) {
            try {
                int i = 1;
                deleteStatement.setInt(i++, term.getId());

                return successfulAction(deleteStatement.executeUpdate());
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
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
        endStatemnt(getByIdStatement);
        endStatemnt(getByTermStatement);
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
