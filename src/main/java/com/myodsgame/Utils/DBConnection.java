package com.myodsgame.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST = "localhost";
    private static final int PUERTO = 5432;
    private static final String DB = "pswdatabase";
    private static final String USER = "postgres";
    private static final String PASSWORD = "budafriends";
    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PUERTO + "/" + DB;

    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        return conn;
    }

    public static void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

