package com.gzs.daos.mysql;

import com.gzs.daos.LanguageDao;
import com.gzs.main.DBConnector;
import com.gzs.model.Language;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LanguageDaoMySQLImpl implements LanguageDao {

    private static String tableName;
    private static DBConnector dbConnector;
    private static Connection connection;
    private static PreparedStatement getAllStatement;
    private static PreparedStatement getByIdStatement;
    private static PreparedStatement getByEnglishNameStatement;
    private static PreparedStatement getByNativeNameStatement;
    private static PreparedStatement getByIsoCodeStatement;
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement deleteStatement;

    static {
        tableName = "languages";
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
        try {
            getAllStatement = connection.prepareStatement("SELECT * FROM " + tableName);
            getByIdStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            getByEnglishNameStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE englishName = ?");
            getByNativeNameStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE nativeName = ?");
            getByIsoCodeStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE isoCode = ?");
            insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?,?)");
            updateStatement = connection.prepareStatement("UPDATE " + tableName + " SET EnglishName=?, NativeName=?, IsoCode=? WHERE id=?");
            deleteStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Language> getAll() {
        List<Language> data = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                data.add(getLanguageFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Language get(int id) {
        Language data = null;
        ResultSet resultSet = null;
        try {
            getByIdStatement.setInt(1, id);
            resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                data = getLanguageFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Language getByEnglishName(String englishName) {
        Language data = null;
        ResultSet resultSet = null;
        try {
            getByEnglishNameStatement.setString(1, englishName);
            resultSet = getByEnglishNameStatement.executeQuery();
            if (resultSet.next()) {
                data = getLanguageFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Language getByNativeName(String nativeName) {
        Language data = null;
        ResultSet resultSet = null;
        try {
            getByNativeNameStatement.setString(1, nativeName);
            resultSet = getByNativeNameStatement.executeQuery();
            if (resultSet.next()) {
                data = getLanguageFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    @Override
    public Language getByIsoCode(String isoCode) {
        Language data = null;
        ResultSet resultSet = null;
        try {
            getByIsoCodeStatement.setString(1, isoCode);
            resultSet = getByIsoCodeStatement.executeQuery();
            if (resultSet.next()) {
                data = getLanguageFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            endResultSet(resultSet);
        }
        return data;
    }

    private Language getLanguageFromResultSet(ResultSet resultSet) {
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
            try {
                int i = 1;
                deleteStatement.setInt(i++, language.getId());
                return successfulAction(deleteStatement.executeUpdate());
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean successfulAction(int action){
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
        endStatemnt(getByIdStatement);
        endStatemnt(getByEnglishNameStatement);
        endStatemnt(getByNativeNameStatement);
        endStatemnt(getByIsoCodeStatement);
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
