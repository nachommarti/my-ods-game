package com.myodsgame.Controllers;

import com.myodsgame.Models.Usuario;
import com.myodsgame.Utils.EstadoJuego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {
    @FXML
    private Label puntosAcumulados;
    @FXML
    private ImageView fotoUsuario;
    @FXML
    private Button modificarPerfilBoton;
    @FXML
    private Button salirBoton;
    @FXML
    private Label usuarioLabel;
    @FXML
    private Label emailLabel;
    private Usuario user = EstadoJuego.getInstance().getUsuario();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        puntosAcumulados.setText("Puntos totales: " + user.getEstadistica().getPuntosTotales());
        usuarioLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
    }
    @FXML
    void modificarPerfilBotonPulsado (ActionEvent event)
    {
    }
    @FXML
    void salirBotonPulsado (ActionEvent event)
    {
    }
}
