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
public class RepositorioEstadisticasImpl implements Repositorio<Estadisticas, String, String, String>{

    private final Connection connection;

    private final Services services = new Services();

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

            int[] aciertosIndividualOds = estadisticas.getAciertos_individual_ods();
            StringBuilder aciertosIndividualOdsBuilder = new StringBuilder();
            for (int i = 0; i < aciertosIndividualOds.length; i++) {
                aciertosIndividualOdsBuilder.append(aciertosIndividualOds[i]);
                if (i < aciertosIndividualOds.length - 1) {
                    aciertosIndividualOdsBuilder.append(",");
                }
            }
            statement.setString(6, aciertosIndividualOdsBuilder.toString());

            int[] fallosIndividualOds = estadisticas.getFallos_individual_ods();
            StringBuilder fallosIndividualOdsBuilder = new StringBuilder();
            for (int i = 0; i < fallosIndividualOds.length; i++) {
                fallosIndividualOdsBuilder.append(fallosIndividualOds[i]);
                if (i < fallosIndividualOds.length - 1) {
                    fallosIndividualOdsBuilder.append(",");
                }
            }
            statement.setString(7, fallosIndividualOdsBuilder.toString());

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticasList;
    }

    @Override
    public List<Estadisticas> findByProperty(String username, String nivel) {
        String sql = "SELECT * FROM estadisticas WHERE username = ? AND nivel = ?";
        List<Estadisticas> estadisticasList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, nivel);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Estadisticas estadisticas = mapResultSetToEstadisticas(resultSet);
                estadisticasList.add(estadisticas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticasList;
    }

    @Override
    public void update(Estadisticas entidad) {

    }

    @Override
    public void delete(Estadisticas entidad) {

    }

    @Override
    public void deleteById(String s) {

    }

    /*
    @Override
    public void update(Estadisticas estadisticas) {
        String sql = "UPDATE estadisticas SET puntos_totales = ?, partidas_jugadas = ?, " +
                "numero_aciertos = ?, numero_fallos = ?, aciertos_individual_ods = ?, " +
                "fallos_individual_ods = ?, nivel = ? WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, estadisticas.getPuntosTotales());
            statement.setInt(2, estadisticas.getPartidasJugadas());
            statement.setInt(3, estadisticas.getNumeroAciertos());
            statement.setInt(4, estadisticas.getNumeroFallos());
            // Establece los valores restantes aciertos_individual_ods, fallos_individual_ods, nivel
            statement.setString(8, estadisticas.getUsuario());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar excepciones según sea necesario
        }
    }

    @Override
    public void delete(Estadisticas estadisticas) {
        String sql = "DELETE FROM estadisticas WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, estadisticas.getUsuario());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar excepciones según sea necesario
        }
    }

    @Override
    public void deleteById(String username) {
        String sql = "DELETE FROM estadisticas WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar excepciones según sea necesario
        }
    }

     */

    private Estadisticas mapResultSetToEstadisticas(ResultSet resultSet) throws SQLException {
        Estadisticas estadisticas = new Estadisticas();
        estadisticas.setUsuario(resultSet.getString("username"));
        estadisticas.setPuntosTotales(resultSet.getInt("puntos_totales"));
        estadisticas.setPartidasJugadas(resultSet.getInt("partidas_jugadas"));
        estadisticas.setNumeroAciertos(resultSet.getInt("numero_aciertos"));
        estadisticas.setNumeroFallos(resultSet.getInt("numero_fallos"));

        String aciertosIndividualOdsString = resultSet.getString("aciertos_individual_ods");
        String[] aciertosIndividualOdsArray = aciertosIndividualOdsString.split(",");
        int[] aciertosIndividualOds = new int[17];
        for (int i = 0; i < aciertosIndividualOdsArray.length; i++) {
            aciertosIndividualOds[i] = Integer.parseInt(aciertosIndividualOdsArray[i]);
        }
        estadisticas.setAciertos_individual_ods(aciertosIndividualOds);

        String fallosIndividualOdsString = resultSet.getString("fallos_individual_ods");
        String[] fallosIndividualOdsArray = fallosIndividualOdsString.split(",");
        int[] fallosIndividualOds = new int[17];
        for (int i = 0; i < fallosIndividualOdsArray.length; i++) {
            fallosIndividualOds[i] = Integer.parseInt(fallosIndividualOdsArray[i]);
        }
        estadisticas.setFallos_individual_ods(fallosIndividualOds);

        estadisticas.setNivel(resultSet.getInt("nivel"));

        return estadisticas;
    }
}
