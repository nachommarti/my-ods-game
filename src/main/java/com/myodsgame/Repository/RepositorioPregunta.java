package com.myodsgame.Repository;

import com.myodsgame.Models.Pregunta;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoPregunta;

import java.util.List;

public interface RepositorioPregunta {

    public List<Reto> getPreguntas();
    public List<Reto> getPreguntasPorNivelDificultad(int nivelDificultad);
    public List<Reto> getPreguntasOrdenadasPorNivelDificultad();

}
