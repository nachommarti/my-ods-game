package com.myodsgame.Services;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Utils.TipoReto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Services implements IServices {
    @Override
    public List<Reto> getPreguntasHelper(Connection connection, String query) {
        List<Reto> preguntas = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put("enunciado", rs.getString("enunciado"));
                map.put("respuesta1", rs.getString("respuesta1"));
                map.put("respuesta2", rs.getString("respuesta2"));
                map.put("respuesta3", rs.getString("respuesta3"));
                map.put("respuesta4", rs.getString("respuesta4"));
                map.put("respuesta_correcta", rs.getString("respuesta_correcta"));
                preguntas.add(RetoFactory.crearReto(false, 30, 10,
                        rs.getInt("nivel_dificultad"), rs.getInt("nivel_dificultad")*100,
                        TipoReto.PREGUNTA, map));
            }
            return preguntas;
        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reto> getPalabrasHelper(Connection connection, String query) {
        List<Reto> palabras = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put("palabra", rs.getString("palabra"));
                palabras.add(RetoFactory.crearReto(false, 120, 20,
                        rs.getInt("nivel_dificultad"), rs.getInt("nivel_dificultad")*100,
                        TipoReto.AHORACADO, map));
            }
            return palabras;
        } catch (SQLException e) {
            System.err.println("Error al obtener palabras: " + e.getMessage());
            return null;
        }
    }
}
