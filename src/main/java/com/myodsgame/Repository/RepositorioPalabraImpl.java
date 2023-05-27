package com.myodsgame.Repository;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Utils.TipoReto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPalabraImpl<T extends Reto> extends RepositorioRetos<T> implements Repositorio<T, Integer> {
    @Override
    public void create(T entidad) {
        String sql = "INSERT INTO palabras (id, palabra, pista, ODS, nivel_dificultad) " +
                "VALUES (?, ?, ?, ?, ?)";
        RetoAhorcado reto = null;
        if (entidad instanceof RetoAhorcado) reto = (RetoAhorcado) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reto.getId());
            statement.setString(2, reto.getPalabra());
            statement.setString(3, reto.getPista());
            statement.setString(4, services.intListToString(reto.getODS()));
            statement.setInt(5, reto.getDificultad());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T findById(Integer id) {
        T retoAhorcado = null;
        try {
            String sql = "SELECT * FROM preguntas WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) retoAhorcado = mapResultSetToRetoAhorcado(resultSet);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retoAhorcado;
    }

    @Override
    public List<T> findByLimit(Integer numFacil, Integer numResto) {
        List<T> palabras = new ArrayList<>();
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + 1 +
                        " ORDER BY RAND() LIMIT ?) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + 2 +
                        " ORDER BY RAND() LIMIT ?) " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + 3 +
                        " ORDER BY RAND() LIMIT ?) "
                ;

        try {
            ResultSet resultSet = findBylimitHelper(query, numFacil, numResto);
            while (resultSet.next()) {
                palabras.add(mapResultSetToRetoAhorcado(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener palabras: " + e.getMessage());
        }
        return palabras;
    }

    @Override
    public void insert(T reto) {
        if (findById(reto.getId()) == null) create(reto);
        else update(reto);
    }

    @Override
    public void update(T entidad) {
        String sql = "UPDATE palabras SET palabra = ?, pista = ?, ODS = ?, nivel_dificultad = ? WHERE id = ? ";
        RetoAhorcado reto = null;
        if (entidad instanceof RetoAhorcado) reto = (RetoAhorcado) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, reto.getPalabra());
            statement.setString(2, reto.getPista());
            statement.setString(3, services.intListToString(reto.getODS()));
            statement.setInt(4, reto.getDificultad());
            statement.setInt(5, reto.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T reto) {
        String sql = "DELETE FROM palabras WHERE id = ?";
        if (reto.getTipoReto() != TipoReto.AHORACADO) throw new IllegalArgumentException("Tipo de reto incorrecto");
        deleteHelper(reto, sql);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM palabras WHERE id = ?";
        deleteByIDHelper(id, sql);
    }
}
