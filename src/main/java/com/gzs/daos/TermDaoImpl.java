package com.gzs.daos;

import com.gzs.model.Term;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class TermDaoImpl extends DatabaseDao implements TermDao {

    private static List<PreparedStatement> statements;
    private static PreparedStatement getAllStatement;
    private static PreparedStatement getByIdStatement;
    private static PreparedStatement getByTermStatement;
    private static PreparedStatement getByTermLangStatement;
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement deleteStatement;
    private static PreparedStatement getTermsTranslationStatement;

    static {
        String tableName = "terms";
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByTermStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term = ?");
            getByTermLangStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE term = ? AND languageID=?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET term=?, meaning=?, languageID=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");

            getTermsTranslationStatement = connection.prepareStatement("SELECT * FROM terms WHERE id IN " +
                    "(SELECT Term2ID FROM translations WHERE Term1ID IN " +
                    "(SELECT id FROM terms WHERE term = ?))");

            statements = new ArrayList<>();
            statements.add(getAllStatement);
            statements.add(getByIdStatement);
            statements.add(getByTermStatement);
            statements.add(getByTermLangStatement);
            statements.add(insertStatement);
            statements.add(updateStatement);
            statements.add(deleteStatement);
            statements.add(getTermsTranslationStatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Term> getAll() {
        return getAllFromDatabase(getAllStatement);
    }

    @Override
    public Term get(int id) {
        Term data = (Term) getterFromInt(getByIdStatement,id).orElse(new Term());
        return data;
    }

    @Override
    public Term getByTerm(String term) {
        Term data = (Term) getterFromString(getByTermStatement,term).orElse(new Term());
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
                data = getFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return data;
    }

    @Override
    protected Term getFromResultSet(ResultSet resultSet) {
        Term returnTerm = new Term();
        LanguageDao languageDao = new LanguageDaoImpl();
        try {
            int id = resultSet.getInt("ID");
            String term = resultSet.getString("Term");
            String meaning = resultSet.getString("Meaning");
            int languageID = resultSet.getInt("LanguageID");
            returnTerm = new Term(id, term, meaning, languageDao.get(languageID));
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

                return insertStatement.executeUpdate()==1;
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

                return updateStatement.executeUpdate()==1;
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
            return deleteFromDatabase(deleteStatement, term.getId());
        } else {
            log.warn("User tried to delete Term with null value from data.");
            return false;
        }
    }

    public static void endStatements() {
        statements.forEach(statement -> endStatement(statement));
    }

    @Override
    public Term getTermsTranslation(String term) {
        Term data = (Term) getterFromString(getTermsTranslationStatement,term).orElse(new Term());
        return data;
    }
}
