package com.gzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    private int id;
    private String englishName;
    private String nativeName;
    private String isoCode;

    public Language(String englishName, String nativeName, String isoCode) {
        this.englishName = englishName;
        this.nativeName = nativeName;
        this.isoCode = isoCode;
    }
}