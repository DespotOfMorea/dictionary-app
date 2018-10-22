package com.gzs.daos;

import com.gzs.main.DBConnector;
import com.gzs.model.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDaoImpl implements LanguageDao {

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    private static String tableName;
    private static DBConnector dbConnector;

    public LanguageDaoImpl() {
        dbConnector = DBConnector.getInstance();
        connection = dbConnector.getConn();
        statement = null;
        resultSet = null;
        tableName = "languages";
    }

    @Override
    public List<Language> getAll() {
        ArrayList<Language> data = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String englishName = resultSet.getString("EnglishName");
                String nativeName = resultSet.getString("NativeName");
                String isoCode = resultSet.getString("IsoCode");
                data.add(new Language(id, englishName, nativeName, isoCode));
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Language getById(int id) {
        Language data = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String englishName = resultSet.getString("EnglishName");
                String nativeName = resultSet.getString("NativeName");
                String isoCode = resultSet.getString("IsoCode");
                data = new Language(id, englishName, nativeName, isoCode);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Language getByEnglishName(String englishName) {
        Language data = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE englishName = ?");
            statement.setString(1, englishName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nativeName = resultSet.getString("NativeName");
                String isoCode = resultSet.getString("IsoCode");
                data = new Language(id, englishName, nativeName, isoCode);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Language getByNativeName(String nativeName) {
        Language data = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE nativeName = ?");
            statement.setString(1, nativeName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String englishName = resultSet.getString("EnglishName");
                String isoCode = resultSet.getString("IsoCode");
                data = new Language(id, englishName, nativeName, isoCode);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public Language getByIsoCode(String isoCode) {
        Language data = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE isoCode = ?");
            statement.setString(1, isoCode);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String englishName = resultSet.getString("EnglishName");
                String nativeName = resultSet.getString("NativeName");
                data = new Language(id, englishName, nativeName, isoCode);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    @Override
    public boolean insertLanguage(Language language) {
        if (language!=null) {
            try {
                statement = connection.prepareStatement("INSERT INTO " + tableName + " (englishName, nativeName, isoCode) " +
                        "SELECT * FROM (SELECT ?, ?, ?) AS tmp WHERE NOT EXISTS " +
                        "(SELECT * FROM  " + tableName + " WHERE englishName=? AND nativeName=? AND isoCode=?) LIMIT 1");
                int i = 1;
                statement.setString(i++, language.getEnglishName());
                statement.setString(i++, language.getNativeName());
                statement.setString(i++, language.getIsoCode());
                statement.setString(i++, language.getEnglishName());
                statement.setString(i++, language.getNativeName());
                statement.setString(i++, language.getIsoCode());

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
    public boolean updateLanguage(Language language) {
        if (language!=null) {
            try {
                statement = connection.prepareStatement("UPDATE " + tableName + " SET EnglishName=?, NativeName=?, IsoCode=? WHERE id=?");
                int i = 1;
                statement.setString(i++, language.getEnglishName());
                statement.setString(i++, language.getNativeName());
                statement.setString(i++, language.getIsoCode());
                statement.setInt(i++, language.getId());

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
    public boolean deleteLanguage(Language language) {
        if (language!=null) {
            try {
                statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
                int i = 1;
                statement.setInt(i++, language.getId());

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
                statement.close();
//                connection.close();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}