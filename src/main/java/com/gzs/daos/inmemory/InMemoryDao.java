package com.gzs.daos.inmemory;

import com.gzs.data.DataInMemoryCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class InMemoryDao {
/*
    protected static DataInMemoryCache dataCache;

    static {
        dataCache = DataInMemoryCache.getInstance();
    }
*/

    public <T> List<T> getAllFromMap(Map<Integer, T> map) {
        List<T> data = new ArrayList<>(map.values());
        return data;
    }
}