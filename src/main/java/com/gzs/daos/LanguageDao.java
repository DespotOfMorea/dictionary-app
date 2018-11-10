package com.gzs.daos;

import com.gzs.model.Language;

public interface LanguageDao extends Dao<Language> {
    Language getByEnglishName(String englishName);
    Language getByNativeName(String nativeName);
    Language getByIsoCode(String iso);
}