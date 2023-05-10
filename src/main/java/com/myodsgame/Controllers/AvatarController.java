package com.myodsgame.Controllers;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.myodsgame.Models.Usuario;
import com.myodsgame.Utils.EstadoJuego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AvatarController implements Initializable {
    @FXML
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ImageView avatar4;
    @FXML
    private ImageView avatar5;
    private ImageView avatarRegi;
    private ImageView avatarModi;
    private Usuario user;
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void initAvatarRegi(ImageView avatar) {
        this.avatarRegi = avatar;
    }
    public void initAvatarModi(ImageView avatar, Usuario user) {
        this.avatarModi = avatar;
        this.user = user;
    }

    @FXML
    private void handleAvatar1(ActionEvent event) {
        if (avatarRegi != null) {
            avatarRegi.setImage(avatar1.getImage());
            EstadoJuego.getInstance().setUrlAvatarRegistro("src/main/resources/images/avatar1.png");
        } else {
            avatarModi.setImage(avatar1.getImage());
            user.setAvatar("src/main/resources/images/avatar1.png");
        }
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar2(ActionEvent event) {
        if (avatarRegi != null) {
            avatarRegi.setImage(avatar2.getImage());
            EstadoJuego.getInstance().setUrlAvatarRegistro("src/main/resources/images/avatar2.png");
        } else {
            avatarModi.setImage(avatar2.getImage());
            user.setAvatar("src/main/resources/images/avatar2.png");
        }
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar3(ActionEvent event) {
        if (avatarRegi != null) {
            avatarRegi.setImage(avatar3.getImage());
            EstadoJuego.getInstance().setUrlAvatarRegistro("src/main/resources/images/avatar3.png");
        } else {
            avatarModi.setImage(avatar3.getImage());
            user.setAvatar("src/main/resources/images/avatar3.png");
        }
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar4(ActionEvent event) {
        if (avatarRegi != null) {
            avatarRegi.setImage(avatar4.getImage());
            EstadoJuego.getInstance().setUrlAvatarRegistro("src/main/resources/images/avatar4.png");
        } else {
            avatarModi.setImage(avatar4.getImage());
            user.setAvatar("src/main/resources/images/avatar4.png");
        }
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar5(ActionEvent event) {
        if (avatarRegi != null) {
            avatarRegi.setImage(avatar5.getImage());
            EstadoJuego.getInstance().setUrlAvatarRegistro("src/main/resources/images/avatar5.png");
        } else {
            avatarModi.setImage(avatar5.getImage());
            user.setAvatar("src/main/resources/images/avatar5.png");
        }
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}