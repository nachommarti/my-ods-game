package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.UserUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class RepositorioEstadisticasImpl implements Repositorio<Estadisticas, String>{

    private final Connection connection;
    private final IServices services;

    public RepositorioEstadisticasImpl() {
        connection = DBConnection.getConnection();
        services = new Services();
    }

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
            statement.setString(6, services.intArrayToString(estadisticas.getAciertos_individual_ods()));
            statement.setString(7, services.intArrayToString(estadisticas.getFallos_individual_ods()));
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
        String sql = "SELECT * FROM estadisticas ORDER BY puntos_totales DESC";
        List<Estadisticas> estadisticasList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            int i = 1;
            while (resultSet.next()) {
                Estadisticas estadisticas = mapResultSetToEstadisticas(resultSet);
                estadisticas.setPosicion(i++);
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
    public void insert(Estadisticas estadisticas, String username) {
        if (findById(username) == null) create(estadisticas);
        else update(estadisticas, username);
    }

    @Override
    public void update(Estadisticas estadisticas, String username) {
        String sql = "UPDATE estadisticas SET username = ?, puntos_totales = ?, partidas_jugadas = ?, " +
                "numero_aciertos = ?, numero_fallos = ?, aciertos_individual_ods = ?, " +
                "fallos_individual_ods = ?, nivel = ?, preguntasAcertadas = ?, ahorcadosAcertados = ?, frasesAcertadas = ? WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, estadisticas.getUsuario());
            statement.setInt(2, estadisticas.getPuntosTotales());
            statement.setInt(3, estadisticas.getPartidasJugadas());
            statement.setInt(4, estadisticas.getNumeroAciertos());
            statement.setInt(5, estadisticas.getNumeroFallos());
            statement.setString(6, services.intArrayToString(estadisticas.getAciertos_individual_ods()));
            statement.setString(7, services.intArrayToString(estadisticas.getFallos_individual_ods()));
            statement.setInt(8, estadisticas.getNivel());
            statement.setString(9, estadisticas.getPreguntasAcertadas().toString());
            statement.setString(10, estadisticas.getPalabrasAcertadas().toString());
            statement.setString(11, estadisticas.getFrasesAcertadas().toString());
            statement.setString(12, username);
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
        estadisticas.setAciertos_individual_ods(services.stringToIntArray(resultSet.getString("aciertos_individual_ods")));
        estadisticas.setAciertos_individual_ods(services.stringToIntArray(resultSet.getString("fallos_individual_ods")));
        estadisticas.setNivel(resultSet.getInt("nivel"));
        estadisticas.setPreguntasAcertadas(services.parsearStringASet(resultSet.getString("preguntasAcertadas")));
        estadisticas.setPalabrasAcertadas(services.parsearStringASet(resultSet.getString("ahorcadosAcertados")));
        estadisticas.setFrasesAcertadas(services.parsearStringASet(resultSet.getString("frasesAcertadas")));

        return estadisticas;
    }
}
