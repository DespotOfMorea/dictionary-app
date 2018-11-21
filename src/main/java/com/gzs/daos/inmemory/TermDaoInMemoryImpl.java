package com.gzs.daos.inmemory;

import com.gzs.daos.TermDao;
import com.gzs.data.GenerateTestData;
import com.gzs.model.Term;

import java.util.List;
import java.util.Map;

public class TermDaoInMemoryImpl extends InMemoryDao<Term> implements TermDao {
    private static GenerateTestData generatedData;

    static {
        generatedData = GenerateTestData.getInstance();
    }

    public TermDaoInMemoryImpl() {
        super();
        this.dataMap = generatedData.getTerms();
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
        Term returnTerm = new Term();
        for (Map.Entry<Integer, Term> entry : dataMap.entrySet()) {
            if (term.equals(entry.getValue().getTerm())) {
                returnTerm = entry.getValue();
            }
        }
        return returnTerm;
    }

    @Override
    public Term getByTermLang(String term, int langId) {
        Term returnTerm = new Term();
        for (Map.Entry<Integer, Term> entry : dataMap.entrySet()) {
            if (term.equals(entry.getValue().getTerm())&&langId==entry.getKey()) {
                returnTerm = entry.getValue();
            }
        }
        return returnTerm;
    }

    @Override
    public boolean insert(Term term) {
        if (term != null) {
            return insertT(term,term.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Term term) {
        if (term != null) {
            return updateT(term,term.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Term term) {
        if (term != null) {
            return deleteT(term.getId());
        } else {
            return false;
        }
    }
}