package com.myodsgame.Controllers;

import com.myodsgame.Utils.UserUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
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
    private ImageView avatar;
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

    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;
    private BooleanProperty validUsername;
    private BooleanProperty validBirthdate;
    private BooleanBinding validFields;

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

    //=========================================================
    // you must initialize here all related with the object
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
    }

    ;

    private void checkUsername() {
        if (!UserUtils.checkUsername(usernameField.textProperty().getValueSafe())) {
            usernameErrorText.textProperty().setValue("Incorrect username. A username is " +
                    "valid if it is between 5 and 20 characters long and " +
                    "contains uppercase or lowercase letters or the " +
                    "hyphens '-' and '_' . (cannot appear consecutive nor first nor last)");
            manageError(usernameErrorText, usernameField, validUsername);
        } else {
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
    private void dateEnterPressed(KeyEvent event) throws Exception {
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
    void selectAvatarClicked(ActionEvent event) {

    }


}