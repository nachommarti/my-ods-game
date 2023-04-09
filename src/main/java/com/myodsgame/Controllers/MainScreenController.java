package com.myodsgame.Controllers;

import com.myodsgame.Utils.UserUtils;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    @FXML
    private Pane pane;
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
        int randomChallenge = 1 + new Random().nextInt(4);
        String viewRoute = null;
        String challengeTitle = null;

        switch (randomChallenge){
            case 1:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto Pregunta";
                break;
            case 2:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto Sopa Letras";
                break;
            case 3:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto Ahorcado";
                break;
            case 4:
                viewRoute = "/com/myodsgame/retoPregunta-view.fxml";
                challengeTitle = "Reto ni idea";
                break;
        }

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource(viewRoute));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(challengeTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
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
        //if(UserUtils.checkUserExists(usernameField.getText(), passwordField.getText())){
            //logear usuario y cambiar de pantalla
        //}else{
            //clearFields();
            //showErrorMessage();
        //}
    }

    //private void clearFields(){
        //usernameField.setText("");
        //passwordField.setText("");
        //notBlankEmail.setValue(Boolean.FALSE);
        //notBlankPassword.setValue(Boolean.FALSE);
        //usernameField.requestFocus();
    //}

    //private void showErrorMessage(){
        //wrongCredentials.setText("Datos introducidos erróneos");
        //wrongCredentials.setTextFill(Color.RED);
    //}

    @FXML
    void logOutClicked(ActionEvent event) {}

    @FXML
    void settingsClicked(ActionEvent event) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(Path.of("", "src", "main", "resources", "images", "mundoGIF.gif").toAbsolutePath().toString());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
        //notBlankEmail = new SimpleBooleanProperty();
        //notBlankPassword = new SimpleBooleanProperty();

        //notBlankEmail.setValue(Boolean.FALSE);
        //notBlankPassword.setValue(Boolean.FALSE);

        //usernameField.requestFocus();

        //usernameField.textProperty().addListener((observable, oldValue, newValue)->{
        //    if (!usernameField.textProperty().getValue().isBlank())
        //        notBlankEmail.setValue(Boolean.TRUE);
        //    else
        //        notBlankEmail.setValue(Boolean.FALSE);
        //    wrongCredentials.setText("");
        //});


        //passwordField.textProperty().addListener((observable, oldValue, newValue) ->{
       //     if (!passwordField.textProperty().getValue().isBlank())
        //        notBlankPassword.setValue(Boolean.TRUE);
        //    else
         //       notBlankPassword.setValue(Boolean.FALSE);
         //   wrongCredentials.setText("");
        //});

        //BooleanBinding validFields = Bindings.and(notBlankPassword, notBlankPassword);
        //loginButton.disableProperty().bind(Bindings.not(validFields));
    }
}