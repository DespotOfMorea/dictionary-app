package com.gzs.daos;

import com.gzs.model.Term;

import java.util.List;

public interface TermDao {
    List<Term> getAll();
    Term getById(int id);
    Term getByTerm(String term);
    Term getByTermLang(String term,int langId);
    boolean insertTerm(Term term);
    boolean updateTerm(Term term);
    boolean deleteTerm(Term term);
}