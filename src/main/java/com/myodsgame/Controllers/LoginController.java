package com.myodsgame.Controllers;

import com.myodsgame.Strategy.EstrategiaLoginEmail;
import com.myodsgame.Strategy.EstrategiaLoginUsuario;
import com.myodsgame.Strategy.LoginManager;
import com.myodsgame.Utils.UserUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notBlankEmail = new SimpleBooleanProperty();
        notBlankPassword = new SimpleBooleanProperty();
        wrongCredentials.setVisible(true);

        notBlankEmail.setValue(Boolean.FALSE);
        notBlankPassword.setValue(Boolean.FALSE);

        usernameField.requestFocus();

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!usernameField.textProperty().getValue().isBlank())
                notBlankEmail.setValue(Boolean.TRUE);
            else
                notBlankEmail.setValue(Boolean.FALSE);
            wrongCredentials.setText("");
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!passwordField.textProperty().getValue().isBlank())
                notBlankPassword.setValue(Boolean.TRUE);
            else
                notBlankPassword.setValue(Boolean.FALSE);
            wrongCredentials.setText("");
        });

        BooleanBinding validFields = Bindings.and(notBlankPassword, notBlankPassword);
        loginButton.disableProperty().bind(Bindings.not(validFields));
    }

    @FXML
    void loginButtonClicked(ActionEvent event) throws IOException {
        LoginManager loginManager = new LoginManager();

        if(UserUtils.isEmail(usernameField.getText())){
            loginManager.setEstrategia(new EstrategiaLoginEmail());
        }
        else{
            loginManager.setEstrategia(new EstrategiaLoginUsuario());
        }

        if (loginManager.login(usernameField.getText(), passwordField.getText())) {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/pantallaPartidas.fxml"));
            BorderPane root = myLoader.load();

            Scene scene = new Scene (root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menú");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
            stage.setResizable(false);
            stage.show();

            Node source = (Node) event.getSource();
            Stage oldStage = (Stage) source.getScene().getWindow();
            oldStage.close();

            ObservableList<Window> windows = Stage.getWindows();
            for (Window window : windows)
            {
                if (((Stage) window).getTitle().equals("ODS Game"))
                {
                    ((Stage) window).close();
                }
            }

        } else {
            clearFields();
            showErrorMessage();
            System.out.println(wrongCredentials.getText());
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        notBlankEmail.setValue(Boolean.FALSE);
        notBlankPassword.setValue(Boolean.FALSE);
        usernameField.requestFocus();
    }

    private void showErrorMessage() {
        wrongCredentials.setText("Datos introducidos erróneos");
        wrongCredentials.setTextFill(Color.RED);
    }

    @FXML
    void registerButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/registroUsuario-view.fxml"));
        BorderPane root = myLoader.load();

        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Formulario de registro");
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();

        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void cancelButtonClicked(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }
}
