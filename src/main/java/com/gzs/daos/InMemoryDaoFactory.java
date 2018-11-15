package com.gzs.daos;

import com.gzs.daos.inmemory.LanguageDaoInMemoryImpl;
import com.gzs.daos.inmemory.TermDaoInMemoryImpl;
import com.gzs.daos.inmemory.TranslationDaoInMemoryImpl;

public class InMemoryDaoFactory extends DaoFactory {
    @Override
    public LanguageDao getLanguageDao() {
        return new LanguageDaoInMemoryImpl();
    }

    @Override
    public TermDao getTermDao() {
        return new TermDaoInMemoryImpl();
    }

    @Override
    public TranslationDao getTranslationDao() {
        return new TranslationDaoInMemoryImpl();
    }
}
