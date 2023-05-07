package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Utils.EstadoJuego;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
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
    private HBox botones1;
    @FXML
    private HBox botones2;
    @FXML
    private HBox botones3;
    @FXML
    private Label palabraMostrada;
    @FXML
    private HBox labelArray;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private Button consolidarButton;
    @FXML
    private Button botonSalir;

    private Partida partidaActual;
    private RetoAhorcado retoActual;
    private String palabra;
    private int numeroPregunta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ayudaButton.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));
        setKeyBoardListeners(botones1);
        setKeyBoardListeners(botones2);
        setKeyBoardListeners(botones3);

        partidaActual = EstadoJuego.getInstance().getPartida();
        retoActual = (RetoAhorcado) EstadoJuego.getInstance().getPartida().getRetos().get(partidaActual.getRetoActual()-1);
        palabra = retoActual.getPalabra().toUpperCase();
        this.numeroPregunta = partidaActual.getRetoActual();

        loadRetosState();
        loadPalabra();

    }

    private void loadRetosState(){
        for(int i = 0; i < numeroPregunta; i++){
            ((Label) labelArray.getChildren().get(i))
                    .setStyle(partidaActual.getRetosFallados()[i] ? "-fx-background-color: rgb(255,25,25); " : "-fx-background-color: rgba(184, 218, 186, 1)");
        }
    }

    private void setKeyBoardListeners(HBox botones){

        for(Node node : botones.getChildren()){
            Button button = (Button) node;
            button.setOnAction(eventHandler);
        }
    }

    EventHandler<ActionEvent> eventHandler = event -> {
        // Handle the button click
       Button pressedButton = (Button) event.getSource();
       char selectedChar = pressedButton.getText().charAt(0);

       if(palabra.contains(selectedChar+"")) {
           pressedButton.setTextFill(Color.GREEN);
           loadChar(selectedChar);
           checkWin();
       }
       else {
           pressedButton.setTextFill(Color.RED);
           retoActual.setIntentos(retoActual.getIntentos()-1);
           checkLose();
       }



       pressedButton.setDisable(true);

    };

    private void disableKeyboard(){
        for(Node node : botones1.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }

        for(Node node : botones2.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }

        for(Node node : botones3.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }
    }

    private void checkWin(){
        if(palabraMostrada.getText().equals(palabra)) {
            disableKeyboard();
            nextQuestionButton.setDisable(false);
        }
    }

    private void checkLose(){
        if(retoActual.getIntentos() == 0){
            disableKeyboard();
            botonSalir.setDisable(false);
            EstadoJuego.getInstance().getPartida().getRetosFallados()[numeroPregunta-1] = true;
            int vidasPartida = EstadoJuego.getInstance().getPartida().getVidas()-1;
            if(vidasPartida == 0){
                EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
            }
            EstadoJuego.getInstance().getPartida().setVidas(vidasPartida);
        }
    }

    private void loadPalabra(){
        ((Label) labelArray.getChildren().get(numeroPregunta - 1)).setStyle("-fx-background-color: rgb(202,184,218)");

        for(int i = 0; i < palabra.length(); i++)
            palabraMostrada.setText(palabraMostrada.getText() + "_");
    }

    private void loadChar(char selectedChar){
        for(int i = 0; i < palabra.length(); i++) {
            if(palabra.charAt(i) == selectedChar) {
                StringBuilder sb = new StringBuilder(palabraMostrada.getText());
                sb.setCharAt(i, selectedChar);
                palabraMostrada.setText(sb.toString());
            }
        }
    }

    @FXML
    void ayudaButtonClicked(ActionEvent event) {

    }

    @FXML
    void consolidarButtonClicked(ActionEvent event) {

    }

    @FXML
    void botonSalirPulsado(ActionEvent event) {

    }

    @FXML
    void siguientePreguntaClicked(ActionEvent event) {
        EstadoJuego.getInstance().getPartida().setRetoActual(numeroPregunta+1);
        Stage stage = (Stage) nextQuestionButton.getScene().getWindow();
        stage.close();
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
