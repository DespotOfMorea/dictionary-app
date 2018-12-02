package com.gzs.daos.inmemory;

import com.gzs.model.DatabaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDao<T extends DatabaseEntity> {

    protected Map<Integer, T> dataMap;

    public InMemoryDao() {
        this.dataMap = new HashMap<Integer, T>();
    }

    protected List<T> getAllFromMap() {
        List<T> data = new ArrayList<>(dataMap.values());
        return data;
    }

    protected T getById(int id) {
        return dataMap.get(id);
    }

    public boolean insert (T t) {
        return t != null ? dataMap.put(t.getId(),t) == null : false;
    }

    public boolean update (T t) {
        return t != null ? dataMap.replace(t.getId(),t) != null : false;
    }

    public boolean delete (T t) {
        return t != null ? dataMap.remove(t.getId()) != null : false;
    }
}