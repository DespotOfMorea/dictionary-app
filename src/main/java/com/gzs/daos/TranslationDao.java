package com.gzs.daos;

import com.gzs.model.Translation;

public interface TranslationDao extends Dao<Translation>{
    Translation getByTerm1Id(int term1ID);
    Translation getByTerm2Id(int term2ID);
}