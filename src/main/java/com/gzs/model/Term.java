package com.gzs.model;

import com.gzs.main.DBMethods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Term {
    private int id;
    private String term;
    private String meaning;
    private Language language;

    public Term(String term, String meaning, Language language) {
        this.term = term;
        this.meaning = meaning;
        this.language = language;
    }

    public Term(int id, String term, String meaning, int languageID) {
        this.id = id;
        this.term = term;
        this.meaning = meaning;
        this.language = DBMethods.getLanguageByID(languageID);
    }
}
