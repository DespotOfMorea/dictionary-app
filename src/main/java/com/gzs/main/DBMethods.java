package com.gzs.main;

import com.gzs.model.Language;
import com.gzs.model.Term;
import com.gzs.model.Translation;

import java.sql.*;

public class DBMethods {

    // Local variables with connection parameters.
    static Connection connection = null;
    static PreparedStatement statement = null;
    static ResultSet resultSet = null;
    private static String dbPath = "jdbc:mysql://localhost/";
    private static String dbName = "geodictionary";
    private static String tableLanguages = "languages";
    private static String tableTerms = "terms";
    private static String tableTranslations = "translations";
    private static String username = "root";
    private static String password = "";
    private static PreparedStatement getTermByIDPrpStmt;
    private static PreparedStatement getTranslatedTermIDPrpStmt;
    private static PreparedStatement getTermIDPrpStmt;

    static {

        try {
            createConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getTranslatedTermIDPrpStmt = connection.prepareStatement("SELECT * FROM " + tableTranslations + " WHERE term1id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getTermIDPrpStmt = connection.prepareStatement("SELECT * FROM " + tableTerms + " WHERE term = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getTermByIDPrpStmt = connection.prepareStatement("SELECT * FROM " + tableTerms + " WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDB() {
        try {
            connection = DriverManager.getConnection(dbPath, username, password);
            Statement st = connection.createStatement();
            int rs = st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void createTableLanguages() {
        try {
            Statement st = connection.createStatement();
            int rs = st.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableLanguages + " (" +
                    "ID int NOT NULL AUTO_INCREMENT, " +
                    "EnglishName varchar(255) NOT NULL, " +
                    "NativeName varchar(255), " +
                    "IsoCode char(3) UNIQUE, " +
                    "PRIMARY KEY (ID));");
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void createTableTerms() {
        try {
            Statement st = connection.createStatement();
            int rs = st.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableTerms + " (" +
                    "ID int NOT NULL AUTO_INCREMENT, " +
                    "Term varchar(255) NOT NULL, " +
                    "Meaning text, " +
                    "LanguageID int NOT NULL, " +
                    "PRIMARY KEY (ID), " +
                    "FOREIGN KEY (LanguageID) REFERENCES Languages(ID));");
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void createTableTranslations() {
        try {
            Statement st = connection.createStatement();
            int rs = st.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableTranslations + " (" +
                    "ID int NOT NULL AUTO_INCREMENT, " +
                    "Term1ID int NOT NULL, " +
                    "Term2ID int NOT NULL, " +
                    "Priority int NOT NULL, " +
                    "PRIMARY KEY (ID), " +
                    "CONSTRAINT FK_Term1 FOREIGN KEY (Term1ID) REFERENCES Terms(ID), " +
                    "CONSTRAINT FK_Term2 FOREIGN KEY (Term2ID) REFERENCES Terms(ID));");
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addLanguage(Language language) {
        try {
            statement = connection.prepareStatement("insert into " + tableLanguages + " values(?,?,?,?)");
            int i = 1;
            statement.setInt(i++, language.getId());
            statement.setString(i++, language.getEnglishName());
            statement.setString(i++, language.getNativeName());
            statement.setString(i++, language.getIsoCode());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
    }

    public static Language getLanguageByID(int id) {
        Language data = new Language();
        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableLanguages + " WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
                String englishName = resultSet.getString("EnglishName");
                String nativeName = resultSet.getString("NativeName");
                String isoCode = resultSet.getString("IsoCode");
                data = new Language(id, englishName, nativeName, isoCode);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
        return data;
    }

    public static void addTerm(Term term) {
        try {
            statement = connection.prepareStatement("insert into " + tableTerms + " values(?,?,?,?)");
            int i = 1;
            statement.setInt(i++, term.getId());
            statement.setString(i++, term.getTerm());
            statement.setString(i++, term.getMeaning());
            statement.setInt(i++, term.getLanguage().getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
    }

    public static void addTranslation(Translation translation) {
        try {
            statement = connection.prepareStatement("insert into " + tableTranslations + " values(?,?,?,?)");
            int i = 1;
            statement.setInt(i++, translation.getId());
            statement.setInt(i++, translation.getTerm1ID().getId());
            statement.setInt(i++, translation.getTerm2ID().getId());
            statement.setInt(i++, translation.getPriority());

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            endConn(connection, resultSet, statement);
        }
    }

    public static int getTermID(String term) {
        int id = 0;
        try {
            getTermIDPrpStmt.setString(1,term);
            resultSet = getTermIDPrpStmt.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, getTermIDPrpStmt);
        }
        return id;
    }

    public static Term getTermByID(int id) {
        Term data = new Term();
        try {
            getTermByIDPrpStmt.setInt(1, id);
            resultSet = getTermByIDPrpStmt.executeQuery();
            resultSet.next();
            String term = resultSet.getString("Term");
            String meaning = resultSet.getString("Meaning");
            int languageID = resultSet.getInt("LanguageID");
            data = new Term(id, term, meaning, languageID);
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, getTermByIDPrpStmt);
        }
        return data;
    }

    public static int getTranslatedTermID(int term1ID) {
        int id = 0;
        try {
            getTranslatedTermIDPrpStmt.setInt(1,term1ID);
            resultSet = getTranslatedTermIDPrpStmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("Priority")==1){
                    id = resultSet.getInt("Term2ID");
                }
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            endConn(connection, resultSet, getTranslatedTermIDPrpStmt);
        }
        return id;
    }

    private static void testConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Problem in loading or "
                    + "registering Oracle JDBC driver");
            e.printStackTrace();
        }
    }

    private static void createConn() throws SQLException {
        connection = DriverManager.getConnection(dbPath + dbName, username, password);
    }

    private static void endConn(Connection connection, ResultSet resultSet, Statement statement) {

        try {
            if (null != connection) {
                if (null != resultSet) {
                    resultSet.close();
                }
//                statement.close();
//                connection.close();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}