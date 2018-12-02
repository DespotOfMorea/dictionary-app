package com.gzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language extends DatabaseEntity {
    private String englishName;
    private String nativeName;
    private String isoCode;

    public Language(int id, String englishName, String nativeName, String isoCode) {
        super(id);
        this.englishName = englishName;
        this.nativeName = nativeName;
        this.isoCode = isoCode;
    }
}