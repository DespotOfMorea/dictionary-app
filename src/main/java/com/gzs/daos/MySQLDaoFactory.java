package com.gzs.daos;

import com.gzs.daos.mysql.LanguageDaoMySQLImpl;
import com.gzs.daos.mysql.TermDaoMySQLImpl;
import com.gzs.daos.mysql.TranslationDaoMySQLImpl;
import com.gzs.main.DBConnector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySQLDaoFactory extends DaoFactory {
    @Override
    public LanguageDao getLanguageDao() {
        return new LanguageDaoMySQLImpl();
    }

    @Override
    public TermDao getTermDao() {
        return new TermDaoMySQLImpl();
    }

    @Override
    public TranslationDao getTranslationDao() {
        return new TranslationDaoMySQLImpl();
    }

    @Override
    public void closeResources() {
        LanguageDaoMySQLImpl.endStatements();
        log.info("LanguageDao statements closed.Starting TermDao statements closing...");
        TermDaoMySQLImpl.endStatements();
        log.info("TermDao statements closed.Starting TranslationDao statements closing...");
        TranslationDaoMySQLImpl.endStatements();
        log.info("TranslationDao statements closed.Starting connection closing...");

        DBConnector dbConnector = DBConnector.getInstance();
        dbConnector.endConn();
        log.info("Connection is closed.");
    }
}
