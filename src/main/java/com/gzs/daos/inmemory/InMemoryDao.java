package com.gzs.daos.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDao<T> {

    protected Map<Integer, T> dataMap;

    public InMemoryDao() {
        this.dataMap = new HashMap<Integer, T>();
    }

    protected List<T> getAllFromMap() {
        List<T> data = new ArrayList<>(dataMap.values());
        return data;
    }

    protected T getById(int id) {
        T newT;
        newT = dataMap.get(id);
        return newT;
    }

    protected boolean insertT (T t, int id) {
        return dataMap.put(id, t) == null;
    }

    protected boolean updateT (T t, int id) {
        return dataMap.replace(id, t) != null;
    }

    protected boolean deleteT (int id) {
        return dataMap.remove(id) != null;
    }


}