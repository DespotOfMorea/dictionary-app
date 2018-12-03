package com.gzs.daos.inmemory;

import com.gzs.daos.TermDao;
import com.gzs.model.Term;

import java.util.List;

public class TermDaoInMemoryImpl extends InMemoryDao<Term> implements TermDao {

    public TermDaoInMemoryImpl() {
        super();
    }

    @Override
    public List<Term> getAll() {
        return getAllFromMap();
    }

    @Override
    public Term get(int id) {
        return getById(id);
    }

    @Override
    public Term getByTerm(String term) {
        Term returnTerm = dataMap.values().stream()
                .filter(entry -> term.equals(entry.getTerm()))
                .findFirst()
                .orElse(new Term());
        return returnTerm;
    }

    @Override
    public Term getByTermLang(String term, int langId) {
        Term returnTerm = dataMap.values().stream()
                .filter(entry -> term.equals(entry.getTerm()) && langId == entry.getLanguage().getId())
                .findFirst()
                .orElse(new Term());
        return returnTerm;
    }

    @Override
    public Term getTermsTranslation(String term) {
        return null;
    }
}