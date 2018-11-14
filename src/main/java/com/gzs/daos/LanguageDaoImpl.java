package com.gzs.daos;

import com.gzs.model.Language;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LanguageDaoImpl extends DatabaseDao implements LanguageDao {

    private static List<PreparedStatement> statements;
    private static PreparedStatement getAllStatement;
    private static PreparedStatement getByIdStatement;
    private static PreparedStatement getByEnglishNameStatement;
    private static PreparedStatement getByNativeNameStatement;
    private static PreparedStatement getByIsoCodeStatement;
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement deleteStatement;

    static {
        String tableName = "languages";
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByEnglishNameStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE englishName = ?");
            getByNativeNameStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE nativeName = ?");
            getByIsoCodeStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE isoCode = ?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET EnglishName=?, NativeName=?, IsoCode=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            statements = new ArrayList<>();
            statements.add(getAllStatement);
            statements.add(getByIdStatement);
            statements.add(getByEnglishNameStatement);
            statements.add(getByNativeNameStatement);
            statements.add(getByIsoCodeStatement);
            statements.add(insertStatement);
            statements.add(updateStatement);
            statements.add(deleteStatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Language> getAll() {
        return getAllFromDatabase(getAllStatement);
    }

    @Override
    public Language get(int id) {
        Language data = getterFromInt(getByIdStatement,id);
        return nullCheck(data,new Language());
    }

    @Override
    public Language getByEnglishName(String englishName) {
        Language data = getterFromString(getByEnglishNameStatement,englishName);
        return nullCheck(data,new Language());
    }

    @Override
    public Language getByNativeName(String nativeName) {
        Language data = getterFromString(getByNativeNameStatement,nativeName);
        return nullCheck(data,new Language());
    }

    @Override
    public Language getByIsoCode(String isoCode) {
        Language data = getterFromString(getByIsoCodeStatement,isoCode);
        return nullCheck(data,new Language());
    }

    @Override
    protected Language getFromResultSet(ResultSet resultSet) {
        Language language = new Language();
        try {
            int id = resultSet.getInt("ID");
            String englishName = resultSet.getString("EnglishName");
            String nativeName = resultSet.getString("NativeName");
            String isoCode = resultSet.getString("IsoCode");
            language = new Language(id, englishName, nativeName, isoCode);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return language;
    }

    @Override
    public boolean insert(Language language) {
        if (language!=null) {
            try {
                int i = 1;
                insertStatement.setInt(i++, language.getId());
                insertStatement.setString(i++, language.getEnglishName());
                insertStatement.setString(i++, language.getNativeName());
                insertStatement.setString(i++, language.getIsoCode());

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
    public boolean update(Language language) {
        if (language!=null) {
            try {
                int i = 1;
                updateStatement.setString(i++, language.getEnglishName());
                updateStatement.setString(i++, language.getNativeName());
                updateStatement.setString(i++, language.getIsoCode());
                updateStatement.setInt(i++, language.getId());

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
    public boolean delete(Language language) {
        if (language!=null) {
            return deleteFromDatabase(deleteStatement, language.getId());
        } else {
            return false;
        }
    }

    public static void endStatements() {
        statements.forEach(statement -> endStatement(statement));
    }
}
