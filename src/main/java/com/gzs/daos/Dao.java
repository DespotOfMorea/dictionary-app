package com.gzs.daos;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    List<T> getAll();
    boolean insert(T t);
    boolean update(T t);
    boolean delete(T t);
}