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

public class RepositorioPreguntaImpl implements RepositorioRetos{
    private final Connection connection;
    private Services services;

    public RepositorioPreguntaImpl() {
        connection = DBConnection.getConnection();
    }

    public List<Reto> getRetosPorNivelDificultadInicial(int nivelDificultad) {
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + nivelDificultad +
                        " ORDER BY RAND() LIMIT 5) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT 4) " +
                        "UNION ALL" +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT 4) "
                ;
        return services.getPreguntasHelper(connection, query);
    }
}
