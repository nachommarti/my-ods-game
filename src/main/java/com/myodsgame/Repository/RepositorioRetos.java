package com.myodsgame.Repository;

import com.myodsgame.Models.Reto;

import java.util.List;

public interface RepositorioRetos {

    List<Reto> getRetosPorNivelDificultadInicial(int nivelDificultad, int numFacil, int numResto);
}
