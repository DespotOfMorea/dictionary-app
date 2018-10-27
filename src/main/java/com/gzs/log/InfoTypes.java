package com.gzs.log;

import java.util.HashMap;
import java.util.Map;

public enum InfoTypes {

    DEBUG('d'), ERROR('e'), INFO('i'), TRACE('t'), QUESTION('q'), WARN('w');

    private char value;
    private static final Map<Character, InfoTypes> lookup = new HashMap<>();

    static {
        for(InfoTypes info : InfoTypes.values()) {
            lookup.put(info.getValue(), info);
        }
    }

    InfoTypes(char infoValue) {
        this.value = infoValue;
    }

    private char getValue() {
        return value;
    }

    public static InfoTypes get(char value) {
        return lookup.get(value);
    }
}