package com.myodsgame.Factory;

import com.myodsgame.Models.Pregunta;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Repository.RepositorioPregunta;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import com.myodsgame.Utils.TipoReto;

import java.util.List;

public class RetoPreguntaFactory implements RetoFactory{
    @Override
    public RetoPregunta [] crearRetos() {
        RepositorioPregunta repositorioPregunta = new RepositorioPreguntaImpl();
        List<Pregunta> preguntas = repositorioPregunta.getPreguntas();
        RetoPregunta [] retosPregunta = new RetoPregunta[10];

        for(int i = 0; i < preguntas.size(); i++){
            Pregunta pregunta = preguntas.get(i);

            retosPregunta[i] = new RetoPregunta(
                    false,
                    30,
                    pregunta.getNivelDificultad(),
                    pregunta.getNivelDificultad()*100,
                    TipoReto.PREGUNTA,
                    pregunta.getEnunciado(),
                    pregunta.getRespuesta1(),
                    pregunta.getRespuesta2(),
                    pregunta.getRespuesta3(),
                    pregunta.getRespuesta4(),
                    pregunta.getRespuestaCorrecta()
            );
        }
        return retosPregunta;
    }
}
