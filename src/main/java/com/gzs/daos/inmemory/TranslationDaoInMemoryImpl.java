package com.gzs.daos.inmemory;

import com.gzs.daos.TranslationDao;
import com.gzs.data.DataInMemoryCache;
import com.gzs.model.Translation;

import java.util.List;
import java.util.Map;

public class TranslationDaoInMemoryImpl extends InMemoryDao implements TranslationDao {

    private static DataInMemoryCache dataCache;

    static {
        dataCache = DataInMemoryCache.getInstance();
    }

    @Override
    public List<Translation> getAll() {
        return getAllFromMap(dataCache.getTranslations());
    }

    @Override
    public Translation get(int id) {
        return dataCache.getTranslation(id);
    }

    @Override
    public Translation getByTerm1Id(int term1ID) {
        Translation translation = new Translation();

        Map<Integer, Translation> map = dataCache.getTranslations();
        for (Map.Entry<Integer, Translation> entry : map.entrySet()) {
            if (term1ID == entry.getValue().getTerm1ID().getId()) {
                translation = entry.getValue();
            }
        }
        return translation;
    }

    @Override
    public Translation getByTerm2Id(int term2ID) {
        Translation translation = new Translation();

        Map<Integer, Translation> map = dataCache.getTranslations();
        for (Map.Entry<Integer, Translation> entry : map.entrySet()) {
            if (term2ID == entry.getValue().getTerm2ID().getId()) {
                translation = entry.getValue();
            }
        }
        return translation;
    }

    @Override
    public boolean insert(Translation translation) {
        if (translation != null) {
            if (!dataCache.getTranslations().containsKey(translation.getId())) {
                dataCache.addTranslation(translation);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Translation translation) {
        if (translation != null) {
            if (dataCache.getTranslations().containsKey(translation.getId())) {
                dataCache.updateTranslation(translation);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Translation translation) {
        if (translation != null) {
            if (dataCache.getTranslations().containsKey(translation.getId())) {
                dataCache.removeTranslation(translation);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}