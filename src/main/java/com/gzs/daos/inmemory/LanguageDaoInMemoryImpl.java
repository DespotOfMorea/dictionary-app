package com.gzs.daos.inmemory;

import com.gzs.daos.LanguageDao;
import com.gzs.data.GenerateTestData;
import com.gzs.model.Language;

import java.util.List;
import java.util.Map;

public class LanguageDaoInMemoryImpl extends InMemoryDao<Language> implements LanguageDao {
    private static GenerateTestData generatedData;

    static {
        generatedData = GenerateTestData.getInstance();
    }

    public LanguageDaoInMemoryImpl() {
        super();
        this.dataMap = generatedData.getLanguages();
    }

    @Override
    public List<Language> getAll() {
        return getAllFromMap();
    }

    @Override
    public Language get(int id) {
        return getById(id);
    }

    @Override
    public Language getByEnglishName(String englishName) {
        Language language = new Language();
        for (Map.Entry<Integer, Language> entry : dataMap.entrySet()) {
            if (englishName.equals(entry.getValue().getEnglishName())) {
                language = entry.getValue();
            }
        }
        return language;
    }

    @Override
    public Language getByNativeName(String nativeName) {
        Language language = new Language();
        for (Map.Entry<Integer, Language> entry : dataMap.entrySet()) {
            if (nativeName.equals(entry.getValue().getNativeName())) {
                language = entry.getValue();
            }
        }
        return language;
    }

    @Override
    public Language getByIsoCode(String iso) {
        Language language = new Language();
        for (Map.Entry<Integer, Language> entry : dataMap.entrySet()) {
            if (iso.equals(entry.getValue().getIsoCode())) {
                language = entry.getValue();
            }
        }
        return language;
    }

    @Override
    public boolean insert(Language language) {
        if (language != null) {
            return insertT(language,language.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Language language) {
        if (language != null) {
            return updateT(language,language.getId());
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Language language) {
        if (language != null) {
            return deleteT(language.getId());
        } else {
            return false;
        }
    }
}