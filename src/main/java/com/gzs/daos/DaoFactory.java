package com.gzs.daos;

public abstract class  DaoFactory {
    public static final String MYSQL = "MySQL";
    public static final String INMEMORY= "InMemory";

    public abstract LanguageDao getLanguageDao();
    public abstract TermDao getTermDao();
    public abstract TranslationDao getTranslationDao();


    public static DaoFactory getDAOFactory(String factoryType) {

        switch (factoryType) {
            case MYSQL:
                return new MySQLDaoFactory();
            case INMEMORY:
                return new InMemoryDaoFactory();
            default:
                return null;
        }
    }
}
