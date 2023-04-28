package com.myodsgame.Repository;

import com.myodsgame.Models.RetoPregunta;

import java.util.List;

public interface RepositorioPregunta {

    public List<RetoPregunta> getPreguntas();
    public List<RetoPregunta> getPreguntasPorNivelDificultad(int nivelDificultad);
    public List<RetoPregunta> getPreguntasOrdenadasPorNivelDificultad();

}
