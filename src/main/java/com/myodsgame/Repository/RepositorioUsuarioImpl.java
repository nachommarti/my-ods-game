package com.myodsgame.Repository;

import com.myodsgame.Models.Pregunta;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarioImpl implements RepositorioUsuario{

    private Connection connection;

    public RepositorioUsuarioImpl() {
        connection = DBConnection.getConnection();
    }

    public Usuario getUsuarioPorUsername(String username)  {
        try {
            String query = "SELECT * FROM usuario WHERE username = " + username ;
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            rs.next();

        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
        }

        return null;
    }

    public List<Usuario> getAllUsuarios(){
        String query = "SELECT * FROM usuario";
                return null;
    }

    public void saveUsuario(Usuario user) throws SQLException {
        try {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setDate(4, new Date(user.getBirthdate().getTime()));
        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
        }
    }

}
