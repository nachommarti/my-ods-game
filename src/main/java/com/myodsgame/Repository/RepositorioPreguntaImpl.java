package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Pregunta;
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

    public List<Pregunta> getPreguntas() {
        String query = "SELECT * FROM preguntas";
        return getPreguntasHelper(query);
    }

    public List<Pregunta> getPreguntasPorNivelDificultad(int nivelDificultad) {
        String query = "SELECT * FROM preguntas WHERE nivel_dificultad <= " + nivelDificultad;
        return getPreguntasHelper(query);
    }

    @Override
    public List<Pregunta> getPreguntasOrdenadasPorNivelDificultad() {
        return null;
    }

    private List<Pregunta> getPreguntasHelper(String query){
        List<Pregunta> preguntas = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                preguntas.add( new Pregunta(
                        rs.getString("enunciado"),
                        rs.getString("respuesta1"),
                        rs.getString("respuesta2"),
                        rs.getString("respuesta3"),
                        rs.getString("respuesta4"),
                        rs.getString("respuesta_correcta"),
                        rs.getInt("nivel_dificultad")
                ));
            }
            return preguntas;
        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
            return null;
        }
    }
}
