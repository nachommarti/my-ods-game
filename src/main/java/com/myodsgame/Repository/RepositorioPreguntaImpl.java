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
    private final Services services;

    public RepositorioPreguntaImpl() {
        connection = DBConnection.getConnection();
        services = new Services();
    }

    public List<Reto> getRetosPorNivelDificultadInicial(int nivelDificultad, int numFacil, int numResto) {
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + nivelDificultad +
                        " ORDER BY RAND() LIMIT ?) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT ?) " +
                        "UNION ALL" +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT ?) "
                ;
        return getPreguntasHelper(query, numFacil, numResto);
    }

    public List<Reto> getPreguntasHelper(String query, int numFacil, int numResto) {
        List<Reto> preguntas = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, numFacil);
            pstmt.setInt(2, numResto);
            pstmt.setInt(3, numResto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put("enunciado", rs.getString("enunciado"));
                map.put("respuesta1", rs.getString("respuesta1"));
                map.put("respuesta2", rs.getString("respuesta2"));
                map.put("respuesta3", rs.getString("respuesta3"));
                map.put("respuesta4", rs.getString("respuesta4"));
                map.put("respuesta_correcta", rs.getString("respuesta_correcta"));
                List<Integer> Ods = services.buildOds(rs.getString("ODS"));

                preguntas.add(RetoFactory.crearReto(false, 30, 10,
                        rs.getInt("nivel_dificultad"), rs.getInt("nivel_dificultad")*100,
                        Ods, TipoReto.PREGUNTA, map));
            }
            return preguntas;
        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
            return null;
        }
    }

    public int getNumeroTotalRetos(){
        String query = "SELECT COUNT(*) FROM preguntas";
        int result = 0;
        try {
            PreparedStatement repo = connection.prepareStatement(query);
            ResultSet rs = repo.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
            return result;

        }catch (SQLException e){
            System.err.println("Error al obtener numero de retos: " + e.getMessage());
            return -1;
        }
    }

    public int getNumeroTotalRetosPorODS(int ods){
        String query = "SELECT COUNT(*) FROM preguntas p WHERE p.ODS = ?";
        int result = 0;
        try{
            PreparedStatement repo = connection.prepareStatement(query);
            repo.setInt(1, ods);
            ResultSet rs = repo.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }
            return result;
        }catch(SQLException e){
            System.err.println("Error al obtener numero de retos: " + e.getMessage());
            return -1;
        }
    }
}
