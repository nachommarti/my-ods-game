package com.myodsgame.Controllers;

import com.myodsgame.Models.Palabra;
import com.myodsgame.Repository.RepositorioPalabra;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RetoAhorcadoController implements Initializable {

    @FXML
    private ImageView ahorcadoDisplay;

    @FXML
    private Button ayudaButton;

    @FXML
    private Label palabraDisplay;

    @FXML
    private TextField palabraField;

    @FXML
    private Button sendPalabraButton;

    @FXML
    private Button sendWordButton;

    @FXML
    private TextField wordField;

    @FXML
    void ayudaButtonClicked(ActionEvent event) {

    }

    @FXML
    void sendPalabraButtonClicked(ActionEvent event) {

    }

    @FXML
    void sendWordButtonClicked(ActionEvent event) {

    }

    private RepositorioPalabra repositorioPalabra;
    private List<Palabra> palabras;
    private Palabra palabraActual;
    private int indicePalabra;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.repositorioPalabra = new RepositorioPalabraImpl();
        palabras = repositorioPalabra.getPalabras();
        loadPalabra();

    }

    private void loadPalabra(){
        this.palabraActual = palabras.get(indicePalabra++);

        palabraDisplay.setText(palabraActual.getPalabra());
    }
}
