package com.myodsgame.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST = "sql7.freesqldatabase.com";
    private static final int PUERTO = 3306;
    private static final String DB = "sql7607569";
    private static final String USER = "sql7607569";
    private static final String PASSWORD = "UAfl5PuGzG";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + DB;

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

