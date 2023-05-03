package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.TipoReto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RepositorioRetoMixtoImpl implements RepositorioRetos{
    private final Connection connection;
    private final Services services;

    public RepositorioRetoMixtoImpl() {
        connection = DBConnection.getConnection();
        services = new Services();
    }

    public List<Reto> getRetosPorNivelDificultadInicial(int nivelDificultad) {
        Random random = new Random();
        int preguntasFacil = random.nextInt(6);
        // Para cuando hayan más retos, se hace así:
        // int palabrasFacil = random.nextInt(6 - preguntasFacil);
        // y el último, la resta.
        int palabrasFacil = 5 - preguntasFacil;

        int preguntasResto = random.nextInt(5);
        int palabrasResto = 4 - preguntasResto;
        String queryPreguntas =
                "SELECT * FROM " +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + nivelDificultad +
                        " ORDER BY RAND() LIMIT " + preguntasFacil + ") AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT " + preguntasResto + ") " +
                        "UNION ALL" +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT " + preguntasResto + ") "
                ;
        nivelDificultad -= 2;
        String queryPalabras =
                "SELECT * FROM " +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + nivelDificultad +
                        " ORDER BY RAND() LIMIT " + palabrasFacil + ") AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT " + palabrasResto + ") " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT " + palabrasResto + ") "
                ;
        List<Reto> listaCombinada = services.getPreguntasHelper(connection, queryPreguntas);
        listaCombinada.addAll(services.getPalabrasHelper(connection, queryPalabras));
        return listaCombinada;
    }
}
