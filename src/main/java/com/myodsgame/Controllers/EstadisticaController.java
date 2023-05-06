package com.myodsgame.Controllers;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Repository.RepositorioEstadisticas;
import com.myodsgame.Repository.RepositorioEstadisticasImpl;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EstadisticaController implements Initializable {
    @FXML
    private Label tusPuntos;

    @FXML
    private Label labelPuntos;

    @FXML
    private Button botonAtras;

    @FXML
    private TableView<Estadisticas> ranking;

    @FXML
    private TableColumn<Estadisticas, Integer> colPuntos;

    @FXML
    private TableColumn<Estadisticas, String> colUsuarios;

    @FXML
    private TableColumn<Estadisticas, Integer> colAciertos;

    @FXML
    private BarChart<?, ?> graficaOds;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Estadisticas> estadisticas = new RepositorioEstadisticasImpl().getEstadisticas();


    }
    @FXML
    void botonAtrasPulsado(ActionEvent event) {

    }


}
