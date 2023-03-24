package com.myodsgame.Repository;

import com.myodsgame.Models.Pregunta;

import java.util.List;

public interface RepositorioPregunta {

    public List<Pregunta> getPreguntas();
    public List<Pregunta> getPreguntasPorNivelDificultad(int nivelDificultad);
    public List<Pregunta> getPreguntasOrdenadasPorNivelDificultad();

}
