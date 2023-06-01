package com.myodsgame.Utils;

import com.myodsgame.Builder.PartidaDirector;
import com.myodsgame.Builder.PartidaMixtaBuilder;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoPregunta;

import java.util.List;

public class TestData {
    public static void main(String[] args) {
        PartidaMixtaBuilder builder = new PartidaMixtaBuilder();
        PartidaDirector director = new PartidaDirector(builder);
        director.BuildPartida();
        DBConnection db = new DBConnection();
    }
}
