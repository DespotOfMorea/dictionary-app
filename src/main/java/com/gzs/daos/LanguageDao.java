package com.gzs.daos;

import com.gzs.model.Language;
import java.util.List;

public interface LanguageDao {
    List<Language> getAll();
    Language getById(int id);
    Language getByEnglishName(String englishName);
    Language getByNativeName(String nativeName);
    Language getByIsoCode(String iso);
    boolean insertLanguage(Language language);
    boolean updateLanguage(Language language);
    boolean deleteLanguage(Language language);
}