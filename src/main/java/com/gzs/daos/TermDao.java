package com.gzs.daos;

import com.gzs.model.Term;

public interface TermDao extends Dao<Term> {
    Term getByTerm(String term);
    Term getByTermLang(String term,int langId);
    Term getTermsTranslation(String term);
}