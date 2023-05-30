package com.myodsgame.Repository;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Utils.TipoReto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RepositorioPreguntaImpl<T extends Reto> extends RepositorioRetos<T> implements Repositorio<T, Integer>{
    @Override
    public void create(T entidad) {
        String sql = "INSERT INTO preguntas (id, enunciado, respuesta1, respuesta2, respuesta3, respuesta4, " +
                "respuesta_correcta, nivel_dificultad, ODS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        RetoPregunta reto = null;
        if (entidad instanceof RetoPregunta) reto = (RetoPregunta) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reto.getId());
            statement.setString(2, reto.getEnunciado());
            statement.setString(3, reto.getRespuesta1());
            statement.setString(4, reto.getRespuesta2());
            statement.setString(5, reto.getRespuesta3());
            statement.setString(6, reto.getRespuesta4());
            statement.setString(7, reto.getRespuestaCorrecta());
            statement.setInt(8, reto.getDificultad());
            statement.setString(9, services.intListToString(reto.getODS()));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T findById(Integer id) {
        T retoPregunta = null;
        try {
            String sql = "SELECT * FROM preguntas WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) retoPregunta = mapResultSetToRetoPregunta(resultSet);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retoPregunta;
    }

    @Override
    public List<T> findByLimit(Integer numFacil, Integer numResto) {
        List<T> preguntas = new ArrayList<>();
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + 1 +
                        " ORDER BY RAND() LIMIT ?) AS dificultad_incial " +
                        "UNION ALL " +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + 2 +
                        " ORDER BY RAND() LIMIT ?) " +
                        "UNION ALL " +
                        "(SELECT * FROM preguntas WHERE nivel_dificultad = " + 3 +
                        " ORDER BY RAND() LIMIT ?) "
                ;

        try {
            ResultSet resultSet = findBylimitHelper(query, numFacil, numResto);
            while (resultSet.next()) {
                preguntas.add(mapResultSetToRetoPregunta(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
        }
        return preguntas;
    }

    @Override
    public void insert(T reto, Integer id) {
        if (findById(id) == null) create(reto);
        else update(reto, id);
    }

    @Override
    public void update(T entidad, Integer id) {
        String sql = "UPDATE preguntas SET enunciado = ?, respuesta1 = ?, respuesta2 = ?, respuesta3 = ?, " +
                "respuesta4 = ?, respuesta_correcta = ?, nivel_dificultad = ?, ODS = ? WHERE id = ?";
        RetoPregunta reto = null;
        if (entidad instanceof RetoPregunta) reto = (RetoPregunta) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, reto.getEnunciado());
            statement.setString(2, reto.getRespuesta1());
            statement.setString(3, reto.getRespuesta2());
            statement.setString(4, reto.getRespuesta3());
            statement.setString(5, reto.getRespuesta4());
            statement.setString(6, reto.getRespuestaCorrecta());
            statement.setInt(7, reto.getDificultad());
            statement.setString(8, services.intListToString(reto.getODS()));
            statement.setInt(9, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T reto) {
        String sql = "DELETE FROM preguntas WHERE id = ?";
        if (reto.getTipoReto() != TipoReto.PREGUNTA) throw new IllegalArgumentException("Tipo de reto incorrecto");
        deleteHelper(reto, sql);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM preguntas WHERE id = ?";
        deleteByIDHelper(id, sql);
    }
}
