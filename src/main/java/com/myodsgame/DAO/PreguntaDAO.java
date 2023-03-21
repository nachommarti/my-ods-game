package com.myodsgame.DAO;

import com.myodsgame.Models.Pregunta;
import com.myodsgame.Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO {
    private Connection connection;

    public PreguntaDAO() {
        connection = DBConnection.getConnection();
    }


}
