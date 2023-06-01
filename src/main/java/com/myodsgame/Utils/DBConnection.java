package com.myodsgame.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST = "odsgamedatabase.ccuybsqtevai.eu-west-3.rds.amazonaws.com";
    private static final int PUERTO = 3306;
    private static final String DB = "odsGameDatabase";
    private static final String USER = "adminUsername";
    private static final String PASSWORD = "adminPassword";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + DB;

    private static Connection conn = null;

    private DBConnection(){}

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

