package com.myodsgame.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private Button fastGameButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void fastGameButtonClicked(ActionEvent event) {
        System.out.println("hi");
    }

    @FXML
    void loginButtonClicked(ActionEvent event) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}