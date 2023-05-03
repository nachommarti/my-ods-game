package com.myodsgame.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class EstadisticaController implements Initializable {
    @FXML
    private Label tusPuntos;

    @FXML
    private Label labelPuntos;

    @FXML
    private Button botonAtras;

    @FXML
    private TableView<?> ranking;

    @FXML
    private TableColumn<?, ?> colPosicion;

    @FXML
    private TableColumn<?, ?> colPuntos;

    @FXML
    private TableColumn<?, ?> colUsuarios;

    @FXML
    private TableColumn<?, ?> colAciertos;

    @FXML
    private BarChart<?, ?> graficaOds;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void botonAtrasPulsado(ActionEvent event) {

    }


}
