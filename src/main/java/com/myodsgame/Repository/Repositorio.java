package com.myodsgame.Repository;

import java.util.List;

public interface Repositorio<T, ID> {
    void create(T entidad);
    T findById(ID id);
    List<T> findAll();
    List<T> findByLimit(Integer valor1, Integer valor2);
    void insert(T entidad, ID id);
    void update(T entidad, ID id);
    void delete(T entidad);
    void deleteById(ID id);
}

