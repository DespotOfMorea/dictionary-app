package com.gzs.daos;

import com.gzs.daos.inmemory.LanguageDaoInMemoryImpl;
import com.gzs.daos.inmemory.TermDaoInMemoryImpl;
import com.gzs.daos.inmemory.TranslationDaoInMemoryImpl;

public class InMemoryDaoFactory extends DaoFactory {
    private LanguageDaoInMemoryImpl languageDaoInMemory;
    private TermDaoInMemoryImpl termDaoInMemory;
    private TranslationDaoInMemoryImpl translationDaoInMemory;

    public InMemoryDaoFactory() {
        this.languageDaoInMemory = new LanguageDaoInMemoryImpl();
        this.termDaoInMemory = new TermDaoInMemoryImpl();
        this.translationDaoInMemory = new TranslationDaoInMemoryImpl();
    }

    @Override
    public LanguageDao getLanguageDao() {
        return languageDaoInMemory;
    }

    @Override
    public TermDao getTermDao() {
        return termDaoInMemory;
    }

    @Override
    public TranslationDao getTranslationDao() {
        return translationDaoInMemory;
    }

    @Override
    public void closeResources() {}
}
