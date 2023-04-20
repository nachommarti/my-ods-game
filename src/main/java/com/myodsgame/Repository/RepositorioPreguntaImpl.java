package com.myodsgame.Repository;

import com.myodsgame.Models.Pregunta;
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

    public List<Pregunta> getPreguntas() {
        String query = "SELECT * FROM preguntas";
        return getPreguntasHelper(query);
    }

    public List<Pregunta> getPreguntasPorNivelDificultad(int nivelDificultad) {
        String query = "SELECT * FROM preguntas WHERE nivel_dificultad = " + nivelDificultad;
        return getPreguntasHelper(query);
    }

    @Override
    public List<Pregunta> getPreguntasOrdenadasPorNivelDificultad() {
        String query = "SELECT * FROM preguntas";
        return getPreguntasHelper(query).stream().sorted(Comparator.comparingInt(Pregunta::getNivelDificultad)).collect(Collectors.toList());
    }

    private List<Pregunta> getPreguntasHelper(String query){
        List<Pregunta> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Pregunta.Builder()
                        .setId(rs.getInt("id"))
                        .setEnunciado(rs.getString("enunciado"))
                        .setRespuesta1(rs.getString("respuesta1"))
                        .setRespuesta2(rs.getString("respuesta2"))
                        .setRespuesta3(rs.getString("respuesta3"))
                        .setRespuesta4(rs.getString("respuesta4"))
                        .setRespuestaCorrecta(rs.getString("respuesta_correcta"))
                        .setNivelDificultad(rs.getInt("nivel_dificultad"))
                        .setOds(rs.getString("ods"))
                        .build());
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error al obtener personas: " + e.getMessage());
            return null;
        }
    }
}
