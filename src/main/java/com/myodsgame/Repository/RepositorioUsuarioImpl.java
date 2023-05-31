package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RepositorioUsuarioImpl implements Repositorio<Usuario, String>{
    private final Connection connection;
    private final Repositorio<Estadisticas, String> repositorioEstadisticas;

    public RepositorioUsuarioImpl() {
        connection = DBConnection.getConnection();
        repositorioEstadisticas = new RepositorioEstadisticasImpl();
    }

    @Override
    public void create(Usuario user) {
        String usuarioInsert = "INSERT INTO usuarios (username, email, password, birthdate, avatar) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement usuarioStatement = connection.prepareStatement(usuarioInsert);
            usuarioStatement.setString(1, user.getUsername());
            usuarioStatement.setString(2, user.getEmail());
            usuarioStatement.setString(3, user.getPassword());
            usuarioStatement.setDate(4, new java.sql.Date(user.getBirthdate().getTime()));
            usuarioStatement.setString(5, user.getAvatar());
            usuarioStatement.executeUpdate();
            usuarioStatement.close();

            Estadisticas estadisticas = new Estadisticas();
            estadisticas.setUsuario(user.getUsername());
            estadisticas.setPuntosTotales(estadisticas.getPuntosTotales());
            estadisticas.setPartidasJugadas(estadisticas.getPartidasJugadas());
            estadisticas.setNumeroAciertos(estadisticas.getNumeroAciertos());
            estadisticas.setNumeroFallos(estadisticas.getNumeroFallos());
            estadisticas.setAciertos_individual_ods(estadisticas.getAciertos_individual_ods());
            estadisticas.setFallos_individual_ods(estadisticas.getFallos_individual_ods());
            estadisticas.setNivel(estadisticas.getNivel());
            estadisticas.setPreguntasAcertadas(new HashSet<>());
            estadisticas.setPalabrasAcertadas(new HashSet<>());
            estadisticas.setFrasesAcertadas(new HashSet<>());
            repositorioEstadisticas.create(estadisticas);

            user.setEstadistica(estadisticas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario findById(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        Usuario user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = mapResultSetToUsuario(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuariosList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = mapResultSetToUsuario(resultSet);
                usuariosList.add(usuario);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuariosList;
    }

    @Override
    public List<Usuario> findByLimit(Integer valor1, Integer valor2) {
        String sql = "SELECT * FROM usuarios ORDER BY RAND() LIMIT ?";
        List<Usuario> usuarioList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, valor1+valor2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = mapResultSetToUsuario(resultSet);
                usuarioList.add(usuario);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarioList;
    }

    @Override
    public void insert(Usuario usuario, String username) {
        if (findById(username) == null) create(usuario);
        else update(usuario, username);
    }

    @Override
    public void update(Usuario user, String username) {
        try {
            String usuarioUpdate = "UPDATE usuarios SET username=?, email=?, avatar=? WHERE username=?";
            PreparedStatement usuarioStatement = connection.prepareStatement(usuarioUpdate);
            usuarioStatement.setString(1, user.getUsername());
            usuarioStatement.setString(2, user.getEmail());
            usuarioStatement.setString(3, user.getAvatar());
            usuarioStatement.setString(4, username);
            usuarioStatement.executeUpdate();
            usuarioStatement.close();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void delete(Usuario usuario) {
        String sql = "DELETE FROM usuarios WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getUsername());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String username) {
        String sql = "DELETE FROM usuarios WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario findByUserAndPassword(String username, String password)  {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        Usuario user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = mapResultSetToUsuario(result);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }

        return user;
    }

    public Usuario findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        Usuario user = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = mapResultSetToUsuario(result);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }

        return user;
    }

    private Usuario mapResultSetToUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setUsername(resultSet.getString("username"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setPassword(resultSet.getString("password"));
        usuario.setBirthdate(new java.util.Date(resultSet.getDate("birthdate").getTime()));
        usuario.setAvatar(resultSet.getString("avatar"));
        usuario.setEstadistica(repositorioEstadisticas.findById(usuario.getUsername()));
        return usuario;
    }
}
