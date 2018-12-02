package com.gzs.daos.inmemory;

import com.gzs.daos.TranslationDao;
import com.gzs.data.GenerateTestData;
import com.gzs.model.Translation;

import java.util.List;

public class TranslationDaoInMemoryImpl extends InMemoryDao<Translation> implements TranslationDao {
    private static GenerateTestData generatedData;

    static {
        generatedData = GenerateTestData.getInstance();
    }

    public TranslationDaoInMemoryImpl() {
        super();
        this.dataMap = generatedData.getTranslations();
    }

    @Override
    public List<Translation> getAll() {
        return getAllFromMap();
    }

    @Override
    public Translation get(int id) {
        return getById(id);
    }

    @Override
    public Translation getByTerm1Id(int term1ID) {
        Translation translation = dataMap.values().stream()
                .filter(entry -> term1ID == entry.getTerm1ID().getId())
                .findFirst()
                .orElse(new Translation());
        return translation;
    }

    @Override
    public Translation getByTerm2Id(int term2ID) {
        Translation translation = dataMap.values().stream()
                .filter(entry -> term2ID == entry.getTerm2ID().getId())
                .findFirst()
                .orElse(new Translation());
        return translation;
    }
}