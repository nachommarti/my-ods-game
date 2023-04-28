package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPreguntaImpl implements RepositorioPregunta{
    private Connection connection;

    public RepositorioPreguntaImpl() {
        connection = DBConnection.getConnection();
    }

    public List<RetoPregunta> getPreguntas() {
        String query = "SELECT * FROM preguntas";
        return getPreguntasHelper(query);
    }

    public List<RetoPregunta> getPreguntasPorNivelDificultad(int nivelDificultad) {
        String query = "SELECT * FROM preguntas WHERE nivel_dificultad = " + nivelDificultad;
        return getPreguntasHelper(query);
    }

    @Override
    public List<RetoPregunta> getPreguntasOrdenadasPorNivelDificultad() {
        String query = "SELECT * FROM preguntas";
        return getPreguntasHelper(query).stream().sorted(Comparator.comparingInt(RetoPregunta::getDificultad))
                .collect(Collectors.toList());
    }

    private List<RetoPregunta> getPreguntasHelper(String query){
        List<RetoPregunta> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add((RetoPregunta) RetoFactory.crearReto(
                        false,30,
                        rs.getInt("dificultad"),
                        rs.getInt("dificultad") * 100,
                        rs.getString("tipo"),
                        rs.getString("enunciado"),
                        rs.getString("respuesta1"),
                        rs.getString("respuesta2"),
                        rs.getString("respuesta3"),
                        rs.getString("respuesta4"),
                        rs.getString("respuestaCorrecta"),
                        null, null, 0
                ));
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error al obtener personas: " + e.getMessage());
            return null;
        }
    }
}
