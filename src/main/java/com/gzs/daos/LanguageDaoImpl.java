package com.gzs.daos;

import com.gzs.model.Language;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class LanguageDaoImpl extends DatabaseDao implements LanguageDao {

    private static PreparedStatement getByEnglishNameStatement;
    private static PreparedStatement getByNativeNameStatement;
    private static PreparedStatement getByIsoCodeStatement;

    static {
        tableName = "languages";
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByEnglishNameStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE englishName = ?");
            getByNativeNameStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE nativeName = ?");
            getByIsoCodeStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE isoCode = ?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET EnglishName=?, NativeName=?, IsoCode=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            statements.add(getByEnglishNameStatement);
            statements.add(getByNativeNameStatement);
            statements.add(getByIsoCodeStatement);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Language> getAll() {
        return getAllFromDatabase();
    }

    @Override
    public Language get(int id) {
        Language data = getterFromInt(getByIdStatement,id);
        return nullCheck(data);
    }

    @Override
    public Language getByEnglishName(String englishName) {
        Language data = getterFromString(getByEnglishNameStatement,englishName);
        return nullCheck(data);
    }

    @Override
    public Language getByNativeName(String nativeName) {
        Language data = getterFromString(getByNativeNameStatement,nativeName);
        return nullCheck(data);
    }

    @Override
    public Language getByIsoCode(String isoCode) {
        Language data = getterFromString(getByIsoCodeStatement,isoCode);
        return nullCheck(data);
    }

    private Language nullCheck(Language language){
        if (language==null){
            language = new Language();
        }
        return language;
    }

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
            return deleteFromDatabase(language.getId());
        } else {
            return false;
        }
    }
}
