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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioPalabraImpl implements RepositorioRetos {

    private final Connection connection;
    private final Services services;

    public RepositorioPalabraImpl() {
        connection = DBConnection.getConnection();
        services = new Services();
    }

    public List<Reto> getRetosPorNivelDificultadInicial(int nivelDificultad) {
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + nivelDificultad +
                        " ORDER BY RAND() LIMIT 5) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT 4) " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT 4) "
                ;
        return services.getPalabrasHelper(connection, query);
    }
}
