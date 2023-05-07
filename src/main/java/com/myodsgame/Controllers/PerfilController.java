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
    private TextField usuarioField;
    @FXML
    private TextField emailField;
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Button confirmarCambios;
    private Usuario user = EstadoJuego.getInstance().getUsuario();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        puntosAcumulados.setText("Puntos totales: " + user.getEstadistica().getPuntosTotales());
        usuarioField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        usuarioField.textProperty().addListener((ov, p1, p2) ->
        {
            confirmarCambios.setDisable(usuarioField.getText() == user.getUsername());
        });
    }
    @FXML
    void confirmarCambiosPulsado (ActionEvent event) throws IOException
    {
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initModality(Modality.APPLICATION_MODAL);
        ventanaEmergente.setTitle("Ventana Emergente");
        ventanaEmergente.setScene(new Scene(new StackPane(new Button("Vale")), 200, 100));
        ventanaEmergente.show();
        user.setUsername(usuarioField.getText());
        user.setEmail(emailField.getText());
    }
    @FXML
    void cancelarPulsado (ActionEvent event) throws IOException
    {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}
