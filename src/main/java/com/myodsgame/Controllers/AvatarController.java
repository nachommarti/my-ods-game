package com.myodsgame.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

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
    private ImageView avatar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void initAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    @FXML
    private void handleAvatar1(ActionEvent event) {
        avatar.setImage(avatar1.getImage());
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar2(ActionEvent event) {
        avatar.setImage(avatar2.getImage());
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar3(ActionEvent event) {
        avatar.setImage(avatar3.getImage());
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar4(ActionEvent event) {
        avatar.setImage(avatar4.getImage());
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    private void handleAvatar5(ActionEvent event) {
        avatar.setImage(avatar5.getImage());
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}