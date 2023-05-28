package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class RepositorioEstadisticasImpl implements Repositorio<Estadisticas, String>{

    private final Connection connection;

    public RepositorioEstadisticasImpl() {connection = DBConnection.getConnection();}

    @Override
    public void create(Estadisticas estadisticas) {
        String sql = "INSERT INTO estadisticas (username, puntos_totales, partidas_jugadas, " +
                "numero_aciertos, numero_fallos, aciertos_individual_ods, fallos_individual_ods, nivel) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, estadisticas.getUsuario());
            statement.setInt(2, estadisticas.getPuntosTotales());
            statement.setInt(3, estadisticas.getPartidasJugadas());
            statement.setInt(4, estadisticas.getNumeroAciertos());
            statement.setInt(5, estadisticas.getNumeroFallos());
            statement.setString(6, intArrayToString(estadisticas.getAciertos_individual_ods()));
            statement.setString(7, intArrayToString(estadisticas.getFallos_individual_ods()));
            statement.setInt(8, estadisticas.getNivel());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Estadisticas findById(String username) {
        String sql = "SELECT * FROM estadisticas WHERE username = ?";
        Estadisticas estadisticas = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                estadisticas = mapResultSetToEstadisticas(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticas;
    }

    @Override
    public List<Estadisticas> findAll() {
        String sql = "SELECT * FROM estadisticas";
        List<Estadisticas> estadisticasList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Estadisticas estadisticas = mapResultSetToEstadisticas(resultSet);
                estadisticasList.add(estadisticas);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticasList;
    }

    @Override
    public List<Estadisticas> findByLimit(Integer valor1, Integer valor2) {
        String sql = "SELECT * FROM estadisticas ORDER BY RAND() LIMIT ?";
        List<Estadisticas> estadisticasList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, valor1+valor2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Estadisticas estadisticas = mapResultSetToEstadisticas(resultSet);
                estadisticasList.add(estadisticas);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticasList;
    }

    @Override
    public void insert(Estadisticas estadisticas) {
        if (findById(estadisticas.getUsuario()) == null) create(estadisticas);
        else update(estadisticas);
    }

    @Override
    public void update(Estadisticas estadisticas) {
        String sql = "UPDATE estadisticas SET username = ?, puntos_totales = ?, partidas_jugadas = ?, " +
                "numero_aciertos = ?, numero_fallos = ?, aciertos_individual_ods = ?, " +
                "fallos_individual_ods = ?, nivel = ? WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, estadisticas.getUsuario());
            statement.setInt(2, estadisticas.getPuntosTotales());
            statement.setInt(3, estadisticas.getPartidasJugadas());
            statement.setInt(4, estadisticas.getNumeroAciertos());
            statement.setInt(5, estadisticas.getNumeroFallos());
            statement.setString(6, intArrayToString(estadisticas.getAciertos_individual_ods()));
            statement.setString(7, intArrayToString(estadisticas.getFallos_individual_ods()));
            statement.setInt(8, estadisticas.getNivel());
            statement.setString(9, estadisticas.getUsuario());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Estadisticas estadisticas) {
        String sql = "DELETE FROM estadisticas WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, estadisticas.getUsuario());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String username) {
        String sql = "DELETE FROM estadisticas WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Estadisticas mapResultSetToEstadisticas(ResultSet resultSet) throws SQLException {
        Estadisticas estadisticas = new Estadisticas();
        estadisticas.setUsuario(resultSet.getString("username"));
        estadisticas.setPuntosTotales(resultSet.getInt("puntos_totales"));
        estadisticas.setPartidasJugadas(resultSet.getInt("partidas_jugadas"));
        estadisticas.setNumeroAciertos(resultSet.getInt("numero_aciertos"));
        estadisticas.setNumeroFallos(resultSet.getInt("numero_fallos"));
        estadisticas.setAciertos_individual_ods(stringToIntArray(resultSet.getString("aciertos_individual_ods")));
        estadisticas.setAciertos_individual_ods(stringToIntArray(resultSet.getString("fallos_individual_ods")));
        estadisticas.setNivel(resultSet.getInt("nivel"));

        return estadisticas;
    }

    private String intArrayToString(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]);
            if (i < array.length - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    private int[] stringToIntArray(String string) {
        String[] split = string.split(",");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        return array;
    }
}
