package com.myodsgame.Controllers;

import com.myodsgame.Models.Estadisticas;
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
        Estadisticas est = user.getEstadistica();
        puntosAcumulados.setText(Integer.toString(est.getPuntosTotales()));
        usuarioLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
        double porcentaje;
        if (est.getNumeroAciertos() == 0) porcentaje = 0;
        else if (est.getNumeroFallos() == 0) porcentaje = 100;
        else {
            porcentaje = (double) est.getNumeroAciertos() / est.getNumeroFallos();
        }
        porcentajeODS.setText(porcentaje +" %");
        barraProgreso.setProgress(porcentaje);
        saveButton.setVisible(false);
        usuarioTextField.setVisible(false);
        usuarioLabel.setVisible(true);
        // usuarioTextField.setStyle("-fx-background-color: transparent;");
        emailTextField.setVisible(false);
        emailLabel.setVisible(true);
        // emailTextField.setStyle("-fx-background-color: transparent;");
        modificarButton.setDisable(false);
        services = new Services();
    }
    @FXML
    void modificarPerfilBotonPulsado (ActionEvent event) {
        saveButton.setVisible(true);
        usuarioTextField.setVisible(true);
        usuarioLabel.setVisible(false);
        // usuarioTextField.setStyle("-fx-background-color: white;");
        emailTextField.setVisible(true);
        emailLabel.setVisible(false);
        // emailTextField.setStyle("-fx-background-color: white;");
        modificarButton.setDisable(true);
    }

    @FXML
    void saveButtonClicked (ActionEvent event) {
        saveButton.setVisible(false);
        usuarioTextField.setVisible(false);
        usuarioLabel.setVisible(true);
        // usuarioTextField.setStyle("-fx-background-color: transparent;");
        emailTextField.setVisible(false);
        emailLabel.setVisible(true);
        // emailTextField.setStyle("-fx-background-color: transparent;");
        modificarButton.setDisable(false);
        String oldUser = usuarioLabel.getText();
        String email = emailTextField.getText();
        user.setUsername(usuarioTextField.getText());
        user.setEmail(email);
        EstadoJuego.getInstance().setUsuario(user);
        usuarioLabel.setText(user.getUsername());
        emailLabel.setText(email);
        services.updateUser(user.getUsername(), oldUser, email);
    }

    @FXML
    void salirBotonPulsado (ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}
