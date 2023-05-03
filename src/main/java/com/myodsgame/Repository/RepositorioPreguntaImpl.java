package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Pregunta;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.TipoReto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RepositorioPreguntaImpl implements RepositorioPregunta{
    private Connection connection;

    public RepositorioPreguntaImpl() {
        connection = DBConnection.getConnection();
    }

    public List<Reto> getPreguntas() {
        String query = "SELECT * FROM preguntas";
        return getPreguntasHelper(query);
    }

    public List<Reto> getPreguntasPorNivelDificultad(int nivelDificultad) {
        String query = "SELECT * FROM preguntas WHERE nivel_dificultad == " + nivelDificultad;
        return getPreguntasHelper(query);
    }

    @Override
    public List<Reto> getPreguntasOrdenadasPorNivelDificultad() {
        return null;
    }

    private List<Reto> getPreguntasHelper(String query){
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
                preguntas.add(RetoFactory.crearReto(false, 30,
                        rs.getInt("nivel_dificultad"), rs.getInt("nivel_dificultad")*100,
                        TipoReto.PREGUNTA, map));
            }
            return preguntas;
        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
            return null;
        }
    }
}
