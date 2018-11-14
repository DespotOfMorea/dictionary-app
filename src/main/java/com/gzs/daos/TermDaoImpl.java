package com.gzs.daos;

import com.gzs.model.Term;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class TermDaoImpl extends DatabaseDao implements TermDao {

    private static PreparedStatement getByTermStatement;
    private static PreparedStatement getByTermLangStatement;

    static {
        tableName = "terms";
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByTermStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term = ?");
            getByTermLangStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term = ? AND languageID=?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET term=?, meaning=?, languageID=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            statements.add(getByTermLangStatement);
            statements.add(getByTermStatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Term> getAll() {
        return getAllFromDatabase();
    }

    @Override
    public Term get(int id) {
        Term data = getterFromInt(getByIdStatement,id);
        return nullCheck(data);
    }

    @Override
    public Term getByTerm(String term) {
        Term data = getterFromString(getByTermStatement,term);
        return nullCheck(data);
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
                data = getFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return data;
    }

    private Term nullCheck(Term term){
        if (term==null){
            term = new Term();
        }
        return term;
    }

    protected Term getFromResultSet(ResultSet resultSet) {
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
            return deleteFromDatabase(term.getId());
        } else {
            return false;
        }
    }

}
