package com.myodsgame.Controllers;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Repository.RepositorioUsuario;
import com.myodsgame.Repository.RepositorioUsuarioImpl;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {
    @FXML
    private Label lIncorrectEmail;
    @FXML
    private Label lIncorrectPass;
    @FXML
    private Label lPassdontmatch;
    @FXML
    private Text usernameErrorText;
    @FXML
    private Label lBirthdate;
    @FXML
    private Button buttonHelpPass;
    @FXML
    private Button acceptButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private DatePicker birthdateSelector;
    @FXML
    private ImageView avatar;

    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;
    private BooleanProperty validUsername;
    private BooleanProperty validBirthdate;
    private BooleanBinding validFields;

    private RepositorioUsuario repositorioUsuario;

    private void manageError(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, textField);
        textField.requestFocus();
    }

    private void manageError(Text errorText, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorText, textField);
        textField.requestFocus();
    }

    private void manageCorrect(Label errorLabel, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, textField);
    }

    private void manageCorrect(Text errorText, TextField textField, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorText, textField);
    }

    private void manageCorrect(Label errorLabel, DatePicker datePicker, BooleanProperty boolProp) {
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, datePicker);
    }

    private void showErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }

    private void showErrorMessage(Text errorText, TextField textField) {
        errorText.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }

    private void showErrorMessage(Label errorLabel, DatePicker datepicker) {
        errorLabel.visibleProperty().set(true);
        datepicker.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }

    private void hideErrorMessage(Label errorLabel, TextField textField) {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }

    private void hideErrorMessage(Text errorText, TextField textField) {
        errorText.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }

    private void hideErrorMessage(Label errorLabel, DatePicker datePicker) {
        errorLabel.visibleProperty().set(false);
        datePicker.styleProperty().setValue("");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonHelpPass.focusTraversableProperty().set(false);

        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();
        equalPasswords = new SimpleBooleanProperty();
        validUsername = new SimpleBooleanProperty();
        validBirthdate = new SimpleBooleanProperty();

        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        validUsername.setValue(Boolean.FALSE);
        validBirthdate.setValue(Boolean.FALSE);


        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { //focus lost.
                checkEditEmail();
            }
        });

        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkEditPass();
            }
        });

        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkUsername();
            }
        });

        repeatPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkEqualPass();
            }
        });


        birthdateSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            checkBirthdate();

        });

        validFields = Bindings.and(validEmail, validPassword)
                .and(equalPasswords).and(validUsername).and(validBirthdate);

        acceptButton.disableProperty().bind(Bindings.not(validFields));

        this.repositorioUsuario = new RepositorioUsuarioImpl();
    }

    private void checkUsername() {
        if (!UserUtils.checkUsername(usernameField.textProperty().getValueSafe())) {
            usernameErrorText.textProperty().setValue("Incorrect username. A username is " +
                    "valid if it is between 5 and 20 characters long and " +
                    "contains uppercase or lowercase letters or the " +
                    "hyphens '-' and '_' . (cannot appear consecutive nor first nor last)");
            manageError(usernameErrorText, usernameField, validUsername);
        }
        else if(UserUtils.checkUserExists(usernameField.textProperty().getValueSafe())){
            usernameErrorText.textProperty().setValue("Ya existe un usuario con este nombre!");
            manageError(usernameErrorText, usernameField, validUsername);
        }
        else {
            usernameErrorText.textProperty().setValue("");
            manageCorrect(usernameErrorText, usernameField, validUsername);
        }

    }

    private void checkEditEmail() {
        if (!UserUtils.checkEmail(emailField.textProperty().getValueSafe()))
            manageError(lIncorrectEmail, emailField, validEmail);
        else
            manageCorrect(lIncorrectEmail, emailField, validEmail);
    }

    private void checkEditPass() {
        if (!UserUtils.checkPassword(passwordField.textProperty().getValueSafe())) {
            lIncorrectPass.setText("Incorrect password.");
            manageError(lIncorrectPass, passwordField, validPassword);
        } else {
            manageCorrect(lIncorrectPass, passwordField, validPassword);
        }
    }

    private void checkEqualPass() {
        if (passwordField.textProperty().getValueSafe().compareTo(repeatPasswordField.textProperty().getValueSafe()) != 0) {
            showErrorMessage(lPassdontmatch, repeatPasswordField);
            equalPasswords.setValue(Boolean.FALSE);
            repeatPasswordField.textProperty().setValue("");
            passwordField.requestFocus();
        } else manageCorrect(lPassdontmatch, repeatPasswordField, equalPasswords);
    }

    private void checkBirthdate() {
        if (birthdateSelector.getValue() == null || Period.between(birthdateSelector.getValue(), LocalDate.now()).getYears() < 12 ||
                Period.between(birthdateSelector.getValue(), LocalDate.now()).getYears() > 100) {
            showErrorMessage(lBirthdate, birthdateSelector);
            validBirthdate.setValue(Boolean.FALSE);
            birthdateSelector.setValue(null);
            birthdateSelector.requestFocus();
        } else manageCorrect(lBirthdate, birthdateSelector, validBirthdate);
    }


    @FXML
    private void dateEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && validFields.getValue()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration confirmed");
            alert.setHeaderText(null);
            alert.setContentText("You have been succesfully registered!");
            alert.initOwner(emailField.getScene().getWindow());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Node source = (Node) event.getSource();
                Stage oldStage = (Stage) source.getScene().getWindow();
                oldStage.close();
            }
        }
    }

    @FXML
    private void avatarEnterPressed(KeyEvent event) throws Exception {
        dateEnterPressed(event);
    }


    @FXML
    void acceptButtonClicked(ActionEvent event) {

        Usuario usuario = new Usuario();
        usuario.setUsername(usernameField.getText());
        usuario.setPassword(passwordField.getText());
        usuario.setEmail(emailField.getText());
        usuario.setBirthdate(Date.from(birthdateSelector.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        usuario.setEstadistica(new Estadisticas());

        String avatarRegistro = EstadoJuego.getInstance().getUrlAvatarRegistro();
        usuario.setAvatar(avatarRegistro == null ? "src/main/resources/images/avatar1.png" : avatarRegistro);

        repositorioUsuario.saveUsuario(usuario);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration confirmed");
        alert.setHeaderText(null);
        alert.setContentText("You have been succesfully registered!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Node source = (Node) event.getSource();
            Stage oldStage = (Stage) source.getScene().getWindow();
            oldStage.close();
        }
    }


    @FXML
    void cancelButtonClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }


    @FXML
    void passwordInfoButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información contraseña");
        alert.setHeaderText("Una contraseña es correcta si:");
        alert.setContentText(
                "- contiene almenos 8 caracteres\n" +
                        "- contiene almenos una letra mayúscula y mínúscula\n" +
                        "- contiene almenos un dígito\n" +
                        "- contiene almenos uno de los siguientes caracteres especiales: @#$%^&+=\n" +
                        "- no contiene ningún espacio en blanco"
        );
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(emailField.getScene().getWindow());
        alert.showAndWait();
    }

    @FXML
    void avatarButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/avatar-view.fxml"));
        Pane root = myLoader.load();
        AvatarController avatarController = myLoader.<AvatarController>getController();
        avatarController.initAvatarRegi(avatar);
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Avatar Selector");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.setResizable(false);
        stage.showAndWait();
    }

}
