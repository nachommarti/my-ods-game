package com.myodsgame.Factory;

import com.myodsgame.Models.*;
import com.myodsgame.Repository.RepositorioPalabra;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import com.myodsgame.Utils.TipoReto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RetoAhoracadoFactory implements RetoFactory {


    @Override
    public List<RetoAhorcado> crearRetos() {
        RepositorioPalabra repositorioPalabra = new RepositorioPalabraImpl();
        List<Palabra> palabras = repositorioPalabra.getPalabras();


        List<RetoAhorcado> retoAhorcado = new ArrayList<>();

        for(int i = 0; i < palabras.size(); i++){
            Palabra palabra = palabras.get(i);

            retoAhorcado.add(new RetoAhorcado(
                    false,
                    30,
                    palabra.getNivelDificultad(),
                    palabra.getNivelDificultad()*100,
                    TipoReto.AHORACADO,
                    palabra.getPalabra(),
                    new HashSet<>(),
                    6
            ));
        }
        return retoAhorcado;
    }
}
