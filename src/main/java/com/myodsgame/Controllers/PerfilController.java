package com.myodsgame.Controllers;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {
    @FXML
    private Label puntosAcumulados;
    @FXML
    private ImageView fotoUsuario;
    @FXML
    private Button cambiarAvatar;
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
        services = new Services();
        puntosAcumulados.setText(Integer.toString(est.getPuntosTotales()));
        usuarioLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
        fotoUsuario.setImage(new Image(Path.of(user.getAvatar()).toAbsolutePath().toString()));
        int numeroAciertosTotales = est.getPalabrasAcertadas().size() + est.getPreguntasAcertadas().size() + est.getFrasesAcertadas().size();
        double porcentaje = ((double) numeroAciertosTotales / services.getNumeroTotalRetos()) * 100;

        porcentajeODS.setText(String.format("%.2f",porcentaje) +" %");
        barraProgreso.setProgress(porcentaje / 100);
        saveButton.setVisible(false);
        usuarioTextField.setVisible(false);
        usuarioLabel.setVisible(true);
        emailTextField.setVisible(false);
        emailLabel.setVisible(true);
        modificarButton.setDisable(false);

    }
    @FXML
    void modificarPerfilBotonPulsado (ActionEvent event) {
        saveButton.setVisible(true);
        cambiarAvatar.setVisible(true);
        usuarioTextField.setVisible(true);
        usuarioLabel.setVisible(false);
        emailTextField.setVisible(true);
        emailLabel.setVisible(false);
        modificarButton.setDisable(true);
    }

    @FXML
    void saveButtonClicked (ActionEvent event) {
        saveButton.setVisible(false);
        cambiarAvatar.setVisible(false);
        usuarioTextField.setVisible(false);
        usuarioLabel.setVisible(true);
        emailTextField.setVisible(false);
        emailLabel.setVisible(true);
        modificarButton.setDisable(false);
        String oldUser = usuarioLabel.getText();
        String email = emailTextField.getText();
        if (!email.equals("")) {
            user.setEmail(email);
            emailLabel.setText(email);
        }
        String username = usuarioTextField.getText();
        if (!username.equals("")) {
            user.setUsername(username);
            usuarioLabel.setText(user.getUsername());
        }
        EstadoJuego.getInstance().setUsuario(user);
        services.updateUser(user, oldUser);
    }

    @FXML
    void cambiarAvatarClicked(ActionEvent event) throws IOException{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/avatar-view.fxml"));
        Pane root = myLoader.load();
        AvatarController avatarController = myLoader.<AvatarController>getController();
        avatarController.initAvatarModi(fotoUsuario, user);
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Avatar Selector");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    void salirBotonPulsado (ActionEvent event) {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/pantallaPartidas.fxml"));
        BorderPane root = null;
        try {
            root = myLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.setResizable(false);
        stage.show();

        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}
