package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Utils.DBConnection;

import java.sql.*;

public class RepositorioUsuarioImpl implements RepositorioUsuario{

    private final Connection connection;

    public RepositorioUsuarioImpl() {
        connection = DBConnection.getConnection();
    }

    public Usuario getUsuarioPorUsernameYContraseña(String username, String password)  {
        Usuario user = null;
        Estadisticas estadisticas;

        try {
            String sql = "SELECT u.username, u.email, u.password, u.birthdate, u.avatar, " +
                    "e.puntos_totales, e.partidas_jugadas, e.numero_aciertos, " +
                    "e.numero_fallos, e.aciertos_individual_ods, e.fallos_individual_ods " +
                    "FROM usuarios u " +
                    "INNER JOIN estadisticas e ON u.username = e.username " +
                    "WHERE u.username = ? AND u.password = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute query and retrieve results
            ResultSet result = statement.executeQuery();

            // Parse results and create User and Statistics objects
            if (result.next()) {
                user = new Usuario();
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setBirthdate(result.getDate("birthdate"));
                user.setAvatar(result.getString("avatar"));

                estadisticas = new Estadisticas();
                estadisticas.setPuntosTotales(result.getInt("puntos_totales"));
                estadisticas.setPartidasJugadas(result.getInt("partidas_jugadas"));
                estadisticas.setNumeroAciertos(result.getInt("numero_aciertos"));
                estadisticas.setNumeroFallos(result.getInt("numero_fallos"));

                // Parse progresoIndividualOds from the database result
                String aciertosIndividualOdsString = result.getString("aciertos_individual_ods");
                String[] aciertosIndividualOdsArray = aciertosIndividualOdsString.split(",");
                int[] aciertosIndividualOds = new int[17];
                for (int i = 0; i < aciertosIndividualOdsArray.length; i++) {
                    int odsNumber = Integer.parseInt(aciertosIndividualOdsArray[i]);
                    aciertosIndividualOds[i] = odsNumber;
                }
                estadisticas.setAciertos_individual_ods(aciertosIndividualOds);

                String fallosIndividualOdsString = result.getString("fallos_individual_ods");
                String[] fallosIndividualOdsArray = fallosIndividualOdsString.split(",");
                int[] fallosIndividualOds = new int[17];
                for (int i = 0; i < fallosIndividualOdsArray.length; i++) {
                    int odsNumber = Integer.parseInt(fallosIndividualOdsArray[i]);
                    fallosIndividualOds[i] = odsNumber;
                }
                estadisticas.setFallos_individual_ods(fallosIndividualOds);

                user.setEstadistica(estadisticas);
            }

            // Close JDBC objects
            result.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }

        return user;
    }

    @Override
    public boolean checkIfUserExists(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from usuarios where username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            return result.next();
        }catch (SQLException e){
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return false;
    }


    public void saveUsuario(Usuario user) {
        try {
            // Insert statement for the usuario table
            String usuarioInsert = "INSERT INTO usuarios (username, email, password, birthdate, avatar) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement usuarioStatement = connection.prepareStatement(usuarioInsert);
            usuarioStatement.setString(1, user.getUsername());
            usuarioStatement.setString(2, user.getEmail());
            usuarioStatement.setString(3, user.getPassword());
            usuarioStatement.setDate(4, new java.sql.Date(user.getBirthdate().getTime()));
            usuarioStatement.setString(5, user.getAvatar());
            usuarioStatement.executeUpdate();
            usuarioStatement.close();

            // Insert statement for the estadisticas table
            String estadisticasInsert = "INSERT INTO estadisticas (username, puntos_totales, partidas_jugadas, " +
                    "numero_aciertos, numero_fallos, aciertos_individual_ods, fallos_individual_ods) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement estadisticasStatement = connection.prepareStatement(estadisticasInsert);
            estadisticasStatement.setString(1, user.getUsername());
            estadisticasStatement.setInt(2, user.getEstadistica().getPuntosTotales());
            estadisticasStatement.setInt(3, user.getEstadistica().getPartidasJugadas());
            estadisticasStatement.setInt(4, user.getEstadistica().getNumeroAciertos());
            estadisticasStatement.setInt(5, user.getEstadistica().getNumeroFallos());

            // Convert aciertosIndividualOds array to a comma-separated string
            int[] aciertosIndividualOds = user.getEstadistica().getAciertos_individual_ods();
            StringBuilder aciertosIndividualOdsBuilder = new StringBuilder();
            for (int i = 0; i < aciertosIndividualOds.length; i++) {
                aciertosIndividualOdsBuilder.append(aciertosIndividualOds[i]);
                if (i < aciertosIndividualOds.length - 1) {
                    aciertosIndividualOdsBuilder.append(",");
                }
            }
            estadisticasStatement.setString(6, aciertosIndividualOdsBuilder.toString());

            // Convert fallosIndividualOds array to a comma-separated string
            int[] fallosIndividualOds = user.getEstadistica().getFallos_individual_ods();
            StringBuilder fallosIndividualOdsBuilder = new StringBuilder();
            for (int i = 0; i < fallosIndividualOds.length; i++) {
                fallosIndividualOdsBuilder.append(fallosIndividualOds[i]);
                if (i < fallosIndividualOds.length - 1) {
                    fallosIndividualOdsBuilder.append(",");
                }
            }
            estadisticasStatement.setString(7, fallosIndividualOdsBuilder.toString());

            estadisticasStatement.executeUpdate();
            estadisticasStatement.close();

            // Close JDBC objects
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    public void updateUsuarioEstadisticas(Usuario user) {
        try {
            // Update statement for the estadisticas table
            String estadisticasUpdate = "UPDATE estadisticas SET puntos_totales=?, partidas_jugadas=?, " +
                    "numero_aciertos=?, numero_fallos=?, aciertos_individual_ods=?, fallos_individual_ods=? " +
                    "WHERE username=?";

            PreparedStatement estadisticasStatement = connection.prepareStatement(estadisticasUpdate);
            estadisticasStatement.setInt(1, user.getEstadistica().getPuntosTotales());
            estadisticasStatement.setInt(2, user.getEstadistica().getPartidasJugadas());
            estadisticasStatement.setInt(3, user.getEstadistica().getNumeroAciertos());
            estadisticasStatement.setInt(4, user.getEstadistica().getNumeroFallos());

            // Convert aciertosIndividualOds array to a comma-separated string
            int[] aciertosIndividualOds = user.getEstadistica().getAciertos_individual_ods();
            StringBuilder aciertosIndividualOdsBuilder = new StringBuilder();
            for (int i = 0; i < aciertosIndividualOds.length; i++) {
                aciertosIndividualOdsBuilder.append(aciertosIndividualOds[i]);
                if (i < aciertosIndividualOds.length - 1) {
                    aciertosIndividualOdsBuilder.append(",");
                }
            }
            estadisticasStatement.setString(5, aciertosIndividualOdsBuilder.toString());

            // Convert fallosIndividualOds array to a comma-separated string
            int[] fallosIndividualOds = user.getEstadistica().getFallos_individual_ods();
            StringBuilder fallosIndividualOdsBuilder = new StringBuilder();
            for (int i = 0; i < fallosIndividualOds.length; i++) {
                fallosIndividualOdsBuilder.append(fallosIndividualOds[i]);
                if (i < fallosIndividualOds.length - 1) {
                    fallosIndividualOdsBuilder.append(",");
                }
            }
            estadisticasStatement.setString(6, fallosIndividualOdsBuilder.toString());

            estadisticasStatement.setString(7, user.getUsername());
            estadisticasStatement.executeUpdate();
            estadisticasStatement.close();

            // Close JDBC objects
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void updateUsuario(String newUser, String oldUser, String email, String avatar) {
        try {
            String usuarioUpdate = "UPDATE usuarios SET username=?, email=?, avatar=? WHERE username=?";
            PreparedStatement usuarioStatement = connection.prepareStatement(usuarioUpdate);
            usuarioStatement.setString(1, newUser);
            usuarioStatement.setString(2, email);
            usuarioStatement.setString(3, avatar);
            usuarioStatement.setString(4, oldUser);
            usuarioStatement.executeUpdate();
            usuarioStatement.close();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }
}
