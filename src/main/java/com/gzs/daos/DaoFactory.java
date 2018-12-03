package com.gzs.daos;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DaoFactory {
    public static final String MYSQL = "MySQL";
    public static final String INMEMORY= "InMemory";
    private static MySQLDaoFactory mySQLDaoFactory;
    private static InMemoryDaoFactory inMemoryDaoFactory;

    static {
        mySQLDaoFactory = new MySQLDaoFactory();
        inMemoryDaoFactory = new InMemoryDaoFactory();
    }

    public abstract LanguageDao getLanguageDao();
    public abstract TermDao getTermDao();
    public abstract TranslationDao getTranslationDao();
    public abstract void closeResources();


    public static DaoFactory getDAOFactory(String factoryType) {

        switch (factoryType) {
            case MYSQL:
                return mySQLDaoFactory;
            case INMEMORY:
                return inMemoryDaoFactory;
            default:
                log.info("MySQL will be used in Application, because data type was not found or not defined in configuration.");
                return mySQLDaoFactory;
        }
    }
}
