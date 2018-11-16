package com.gzs.daos.inmemory;

import com.gzs.daos.LanguageDao;
import com.gzs.data.DataInMemoryCache;
import com.gzs.model.Language;

import java.util.List;
import java.util.Map;

public class LanguageDaoInMemoryImpl extends InMemoryDao implements LanguageDao {

    private static DataInMemoryCache dataCache;

    static {
        dataCache = DataInMemoryCache.getInstance();
    }

    @Override
    public List<Language> getAll() {
        return getAllFromMap(dataCache.getLanguages());
    }

    @Override
    public Language get(int id) {
        return dataCache.getLanguage(id);
    }

    @Override
    public Language getByEnglishName(String englishName) {
        Language language = new Language();
        Map<Integer, Language> map = dataCache.getLanguages();
        for (Map.Entry<Integer, Language> entry : map.entrySet()) {
            if (englishName.equals(entry.getValue().getEnglishName())) {
                language = entry.getValue();
            }
        }
        return language;
    }

    @Override
    public Language getByNativeName(String nativeName) {
        Language language = new Language();
        Map<Integer, Language> map = dataCache.getLanguages();
        for (Map.Entry<Integer, Language> entry : map.entrySet()) {
            if (nativeName.equals(entry.getValue().getNativeName())) {
                language = entry.getValue();
            }
        }
        return language;
    }

    @Override
    public Language getByIsoCode(String iso) {
        Language language = new Language();
        Map<Integer, Language> map = dataCache.getLanguages();
        for (Map.Entry<Integer, Language> entry : map.entrySet()) {
            if (iso.equals(entry.getValue().getIsoCode())) {
                language = entry.getValue();
            }
        }
        return language;
    }

    @Override
    public boolean insert(Language language) {
        if (language != null) {
            return dataCache.insertLanguage(language);
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Language language) {
        if (language != null) {
            return dataCache.updateLanguage(language);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Language language) {
        if (language != null) {
            return dataCache.deleteLanguage(language);
        } else {
            return false;
        }
    }
}