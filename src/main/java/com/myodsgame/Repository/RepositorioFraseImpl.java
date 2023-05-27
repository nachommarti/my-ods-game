package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoFrase;
import com.myodsgame.Utils.TipoReto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioFraseImpl<T extends Reto> extends RepositorioRetos<T> implements Repositorio<T, Integer>{
    @Override
    public void create(T entidad) {
        String sql = "INSERT INTO frases (id, frase, pista, nivel_dificultad, ODS) " +
                "VALUES (?, ?, ?, ?, ?)";
        RetoFrase reto = null;
        if (entidad instanceof RetoFrase) reto = (RetoFrase) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reto.getId());
            statement.setString(2, reto.getFrase());
            statement.setString(3, reto.getPista());
            statement.setInt(4, reto.getDificultad());
            statement.setString(5, services.intListToString(reto.getODS()));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T findById(Integer id) {
        T retoFrase = null;
        try {
            String sql = "SELECT * FROM frases WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) retoFrase = mapResultSetToRetoFrase(resultSet);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retoFrase;
    }

    @Override
    public List<T> findByLimit(Integer numFacil, Integer numResto) {
        List<T> frases = new ArrayList<>();
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM frases WHERE nivel_dificultad = " + 1 +
                        " ORDER BY RAND() LIMIT ?) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM frases WHERE nivel_dificultad = " + 2 +
                        " ORDER BY RAND() LIMIT ?) " +
                        "UNION ALL" +
                        "(SELECT * FROM frases WHERE nivel_dificultad = " + 3 +
                        " ORDER BY RAND() LIMIT ?) "
                ;

        try {
            ResultSet resultSet = findBylimitHelper(query, numFacil, numResto);
            while (resultSet.next()) {
                frases.add(mapResultSetToRetoAhorcado(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener frases: " + e.getMessage());
        }
        return frases;
    }

    @Override
    public void insert(T reto) {
        if (findById(reto.getId()) == null) create(reto);
        else update(reto);
    }

    @Override
    public void update(T entidad) {
        String sql = "UPDATE frases SET frase = ?, pista = ?, nivel_dificultad = ?, ODS = ? WHERE id = ? ";
        RetoFrase reto = null;
        if (entidad instanceof RetoFrase) reto = (RetoFrase) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, reto.getFrase());
            statement.setString(2, reto.getPista());
            statement.setInt(3, reto.getDificultad());
            statement.setString(4, services.intListToString(reto.getODS()));
            statement.setInt(5, reto.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T reto) {
        String sql = "DELETE FROM frases WHERE id = ?";
        if (reto.getTipoReto() != TipoReto.FRASE) throw new IllegalArgumentException("Tipo de reto incorrecto");
        deleteHelper(reto, sql);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM frases WHERE id = ?";
        deleteByIDHelper(id, sql);
    }
}
