package com.myodsgame.Controllers;

import com.myodsgame.Models.Usuario;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button salirBoton;
    @FXML
    private Label usuarioLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private ProgressBar barraProgreso;
    @FXML
    private Label porcentajeODS;
    @FXML
    private Button saveButton;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button modificarButton;

    private Usuario user;
    private IServices services;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = EstadoJuego.getInstance().getUsuario();
        puntosAcumulados.setText(Integer.toString(user.getEstadistica().getPuntosTotales()));
        usuarioLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
        porcentajeODS.setText(user.getEstadistica().getPuntosTotales() +" %");
        barraProgreso.setProgress(user.getEstadistica().getPuntosTotales());
        saveButton.setVisible(false);
        usuarioTextField.setVisible(false);
        emailTextField.setVisible(false);
        modificarButton.setDisable(false);
        services = new Services();
    }
    @FXML
    void modificarPerfilBotonPulsado (ActionEvent event) {
        saveButton.setVisible(true);
        usuarioTextField.setVisible(true);
        emailTextField.setVisible(true);
        modificarButton.setDisable(true);
    }

    @FXML
    void saveButtonClicked (ActionEvent event) {
        saveButton.setVisible(false);
        usuarioTextField.setVisible(false);
        emailTextField.setVisible(false);
        modificarButton.setDisable(false);
        user.setUsername(usuarioTextField.getText());
        user.setEmail(emailTextField.getText());
        EstadoJuego.getInstance().setUsuario(user);
        usuarioLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
        services.saveUser(user);
    }

    @FXML
    void salirBotonPulsado (ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}
