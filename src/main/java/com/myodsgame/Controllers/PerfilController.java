package com.myodsgame.Controllers;

import com.myodsgame.Models.Usuario;
import com.myodsgame.Utils.EstadoJuego;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {
    @FXML
    private Label puntosAcumulados;
    @FXML
    private TextField usuarioField;
    @FXML
    private TextField emailField;
    @FXML
    private ImageView imagenUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}
