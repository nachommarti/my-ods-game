package com.myodsgame.Factory;

import com.myodsgame.Models.*;
import com.myodsgame.Repository.RepositorioPalabra;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import com.myodsgame.Repository.RepositorioPregunta;
import com.myodsgame.Repository.RepositorioPreguntaImpl;
import com.myodsgame.Utils.TipoReto;

import java.util.HashSet;
import java.util.List;

public class RetoAhoracadoFactory implements RetoFactory {


    @Override
    public RetoAhorcado[] crearRetos() {
        RepositorioPalabra repositorioPalabra = new RepositorioPalabraImpl();
        List<Palabra> palabras = repositorioPalabra.getPalabras();


        RetoAhorcado [] retoAhorcado = new RetoAhorcado[10];

        for(int i = 0; i < palabras.size(); i++){
            Palabra palabra = palabras.get(i);

            retoAhorcado[i] = new RetoAhorcado(
                    false,
                    30,
                    palabra.getNivelDificultad(),
                    palabra.getNivelDificultad()*100,
                    TipoReto.AHORACADO,
                    palabra.getPalabra(),
                    new HashSet<>(),
                    6
            );
        }
        return retoAhorcado;
    }
}
