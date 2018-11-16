package com.gzs.daos.inmemory;

import com.gzs.daos.TermDao;
import com.gzs.data.DataInMemoryCache;
import com.gzs.model.Term;

import java.util.List;
import java.util.Map;

public class TermDaoInMemoryImpl extends InMemoryDao implements TermDao {

    private static DataInMemoryCache dataCache;

    static {
        dataCache = DataInMemoryCache.getInstance();
    }

    @Override
    public List<Term> getAll() {
        return getAllFromMap(dataCache.getTerms());
    }

    @Override
    public Term get(int id) {
        return dataCache.getTerm(id);
    }

    @Override
    public Term getByTerm(String term) {
        Term returnTerm = new Term();
        Map<Integer, Term> map = dataCache.getTerms();
        for (Map.Entry<Integer, Term> entry : map.entrySet()) {
            if (term.equals(entry.getValue().getTerm())) {
                returnTerm = entry.getValue();
            }
        }
        return returnTerm;
    }

    @Override
    public Term getByTermLang(String term, int langId) {
        return null;
    }

    @Override
    public boolean insert(Term term) {
        if (term != null) {
            return dataCache.insertTerm(term);
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Term term) {
        if (term != null) {
            return dataCache.updateTerm(term);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Term term) {
        if (term != null) {
            return dataCache.deleteTerm(term);
        } else {
            return false;
        }
    }
}