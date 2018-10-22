package com.gzs.daos;

import com.gzs.model.Translation;

import java.util.List;

public interface TranslationDao {

    List<Translation> getAll();
    Translation getById(int id);
    Translation getByTerm1Id(int term1ID);
    Translation getByTerm2Id(int term2ID);
    boolean insertTranslation(Translation translation);
    boolean updateTranslation(Translation translation);
    boolean deleteTranslation(Translation translation);
}