package com.gzs.daos.inmemory;

import com.gzs.daos.TranslationDao;
import com.gzs.data.GenerateTestData;
import com.gzs.model.Translation;

import java.util.List;
import java.util.Map;

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
        Translation translation = new Translation();
        for (Map.Entry<Integer, Translation> entry : dataMap.entrySet()) {
            if (term1ID == entry.getValue().getTerm1ID().getId()) {
                translation = entry.getValue();
            }
        }
        return translation;
    }

    @Override
    public Translation getByTerm2Id(int term2ID) {
        Translation translation = new Translation();
        for (Map.Entry<Integer, Translation> entry : dataMap.entrySet()) {
            if (term2ID == entry.getValue().getTerm2ID().getId()) {
                translation = entry.getValue();
            }
        }
        return translation;
    }

    @Override
    public boolean insert(Translation translation) {
        if (translation != null) {
            return insertT(translation,translation.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Translation translation) {
        if (translation != null) {
            return updateT(translation,translation.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Translation translation) {
        if (translation != null) {
            return deleteT(translation.getId());
        } else {
            return false;
        }
    }
}