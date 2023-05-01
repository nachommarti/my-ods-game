package com.myodsgame.Controllers;

import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Repository.RepositorioPalabra;
import com.myodsgame.Repository.RepositorioPalabraImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class RetoAhorcadoController implements Initializable {

    @FXML
    private ImageView vidas;

    @FXML
    private Button ayudaButton;

    @FXML
    private Label timer;
    @FXML
    private Label frasePista;
    @FXML
    private ImageView imagenODS;

    @FXML
    private ImageView imagenAhorcado;
    @FXML
    private Label palabraOculta;

    @FXML
    private Button botonA;
    @FXML
    private Button botonB;
    @FXML
    private Button botonC;
    @FXML
    private Button botonD;
    @FXML
    private Button botonE;
    @FXML
    private Button botonF;
    @FXML
    private Button botonG;
    @FXML
    private Button botonH;
    @FXML
    private Button botonI;
    @FXML
    private Button botonJ;
    @FXML
    private Button botonK;
    @FXML
    private Button botonL;
    @FXML
    private Button botonÑ;
    @FXML
    private Button botonO;
    @FXML
    private Button botonP;
    @FXML
    private Button botonQ;
    @FXML
    private Button botonR;
    @FXML
    private Button botonS;
    @FXML
    private Button botonT;
    @FXML
    private Button botonU;
    @FXML
    private Button botonV;
    @FXML
    private Button botonW;
    @FXML
    private Button botonX;
    @FXML
    private Button botonY;
    @FXML
    private Button botonZ;



    @FXML
    void ayudaButtonClicked(ActionEvent event) {

    }
    private RepositorioPalabra repositorioPalabra;
    private List<RetoAhorcado> retoAhorcados;
    private RetoAhorcado retoAhorcadoActual;
    private int indicePalabra;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ayudaButton.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));

    }

    private void loadPalabra(){

    }

    @FXML
    void botonAPulsado(ActionEvent event) {

    }
    @FXML
    void botonBPulsado(ActionEvent event) {

    }
    @FXML
    void botonCPulsado(ActionEvent event) {

    }
    @FXML
    void botonDPulsado(ActionEvent event) {

    }
    @FXML
    void botonEPulsado(ActionEvent event) {

    }
    @FXML
    void botonFPulsado(ActionEvent event) {

    }
    @FXML
    void botonGPulsado(ActionEvent event) {

    }
    @FXML
    void botonHPulsado(ActionEvent event) {

    }
    @FXML
    void botonIPulsado(ActionEvent event) {

    }
    @FXML
    void botonJPulsado(ActionEvent event) {

    }
    @FXML
    void botonKPulsado(ActionEvent event) {

    }
    @FXML
    void botonLPulsado(ActionEvent event) {

    }
    @FXML
    void botonMPulsado(ActionEvent event) {

    }
    @FXML
    void botonNPulsado(ActionEvent event) {

    }
    @FXML
    void botonÑPulsado(ActionEvent event) {

    }
    @FXML
    void botonOPulsado(ActionEvent event) {

    }
    @FXML
    void botonPPulsado(ActionEvent event) {

    }
    @FXML
    void botonQPulsado(ActionEvent event) {

    }
    @FXML
    void botonRPulsado(ActionEvent event) {

    }

    @FXML
    void botonSPulsado(ActionEvent event) {

    }
    @FXML
    void botonTPulsado(ActionEvent event) {

    }
    @FXML
    void botonUPulsado(ActionEvent event) {

    }
    @FXML
    void botonVPulsado(ActionEvent event) {

    }
    @FXML
    void botonWPulsado(ActionEvent event) {

    }
    @FXML
    void botonXPulsado(ActionEvent event) {

    }
    @FXML
    void botonYPulsado(ActionEvent event) {

    }
    @FXML
    void botonZPulsado(ActionEvent event) {

    }

}
