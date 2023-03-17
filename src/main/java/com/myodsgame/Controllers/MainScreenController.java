package com.myodsgame.Controllers;

import com.myodsgame.Utils.UserUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private Button fastGameButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label wrongCredentials;

    private BooleanProperty notBlankEmail;
    private BooleanProperty notBlankPassword;

    @FXML
    void fastGameButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/retoPregunta-view.fxml"));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Reto Pregunta");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
        System.out.println("hi");
    }

    @FXML
    void registerButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/registroUsuario-view.fxml"));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Formulario de registro");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
        System.out.println("hi");
    }

    @FXML
    void loginButtonClicked(ActionEvent event) {
        if(UserUtils.checkUserExists(usernameField.getText(), passwordField.getText())){
            //logear usuario y cambiar de pantalla
        }else{
            clearFields();
            showErrorMessage();
        }
    }

    private void clearFields(){
        usernameField.setText("");
        passwordField.setText("");
        notBlankEmail.setValue(Boolean.FALSE);
        notBlankPassword.setValue(Boolean.FALSE);
        usernameField.requestFocus();
    }

    private void showErrorMessage(){
        wrongCredentials.setText("Datos introducidos erróneos");
        wrongCredentials.setTextFill(Color.RED);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notBlankEmail = new SimpleBooleanProperty();
        notBlankPassword = new SimpleBooleanProperty();

        notBlankEmail.setValue(Boolean.FALSE);
        notBlankPassword.setValue(Boolean.FALSE);

        usernameField.requestFocus();

        usernameField.textProperty().addListener((observable, oldValue, newValue)->{
            if (!usernameField.textProperty().getValue().isBlank())
                notBlankEmail.setValue(Boolean.TRUE);
            else
                notBlankEmail.setValue(Boolean.FALSE);
            wrongCredentials.setText("");
        });


        passwordField.textProperty().addListener((observable, oldValue, newValue) ->{
            if (!passwordField.textProperty().getValue().isBlank())
                notBlankPassword.setValue(Boolean.TRUE);
            else
                notBlankPassword.setValue(Boolean.FALSE);
            wrongCredentials.setText("");
        });

        BooleanBinding validFields = Bindings.and(notBlankPassword, notBlankPassword);
        loginButton.disableProperty().bind(Bindings.not(validFields));
    }
}