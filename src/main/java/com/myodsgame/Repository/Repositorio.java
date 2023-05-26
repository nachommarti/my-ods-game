package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;

import java.util.List;

public interface Repositorio<T, ID, Valor1, Valor2> {
    void create(T entidad);
    T findById(ID id);
    List<T> findAll();
    List<T> findByProperty(Valor1 valor1, Valor2 valor2);
    void update(T entidad);
    void delete(T entidad);
    void deleteById(ID id);
}

