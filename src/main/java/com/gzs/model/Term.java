package com.gzs.model;

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
    private int languageID;

    public Term(String term, String meaning, int languageID) {
        this.term = term;
        this.meaning = meaning;
        this.languageID = languageID;
    }
}
