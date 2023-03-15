package com.myodsgame.myodsgame.DAO;

import com.myodsgame.myodsgame.Models.Pregunta;
import com.myodsgame.myodsgame.Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO {
    private Connection connection;

    public PreguntaDAO() {
        connection = DBConnection.getConnection();
    }

    /*
    public void insertarPersona(String nombre, int edad) {
        String sql = "INSERT INTO persona (nombre, edad) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, edad);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar persona: " + e.getMessage());
        }
    }

    public void eliminarPersona(int id) {
        String sql = "DELETE FROM persona WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar persona: " + e.getMessage());
        }
    }
    */

    public List<Pregunta> getPregunta(/*int dificultad*/) {
        String sql = "SELECT * FROM preguntas"; //where nivel_dificultad = dificultad";
        Pregunta pregunta;
        List<Pregunta> list = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Pregunta.Builder()
                        .setId(rs.getInt("id"))
                        .setEnunciado(rs.getString("pregunta"))
                        .setRespuesta1(rs.getString("respuesta1"))
                        .setRespuesta2(rs.getString("respuesta2"))
                        .setRespuesta3(rs.getString("respuesta3"))
                        .setRespuesta4(rs.getString("respuesta4"))
                        .setRespuestaCorrecta(rs.getInt("respuesta_correcta"))
                        .setNivelDificultad(rs.getInt("nivel_dificultad"))
                        .build());
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error al obtener personas: " + e.getMessage());
            return null;
        }
    }
}
