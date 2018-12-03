package com.gzs.daos.inmemory;

import com.gzs.daos.LanguageDao;
import com.gzs.model.Language;

import java.util.List;

public class LanguageDaoInMemoryImpl extends InMemoryDao<Language> implements LanguageDao {

    public LanguageDaoInMemoryImpl() {
        super();
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
        Language language = dataMap.values().stream()
                .filter(entry -> englishName.equals(entry.getEnglishName()))
                .findFirst()
                .orElse(new Language());
        return language;
    }

    @Override
    public Language getByNativeName(String nativeName) {
        Language language = dataMap.values().stream()
                .filter(entry -> nativeName.equals(entry.getNativeName()))
                .findFirst()
                .orElse(new Language());
        return language;
    }

    @Override
    public Language getByIsoCode(String iso) {
        Language language = dataMap.values().stream()
                .filter(entry -> iso.equals(entry.getIsoCode()))
                .findFirst()
                .orElse(new Language());
        return language;
    }
}