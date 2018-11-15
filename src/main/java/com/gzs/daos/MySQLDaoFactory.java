package com.gzs.daos;

import com.gzs.daos.mysql.LanguageDaoMySQLImpl;
import com.gzs.daos.mysql.TermDaoMySQLImpl;
import com.gzs.daos.mysql.TranslationDaoMySQLImpl;

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
}
