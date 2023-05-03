package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;

public class PartidaDirector {
    private IPartidaBuilder builder;

    /*
    4 faciles, 3 medias, 3 dificiles
    5 4 4
    OK OK OK OK
    */
    public PartidaDirector(IPartidaBuilder builder) {
        this.builder = builder;
    }
    public Partida BuildPartida() {
        builder.BuildRetos();
        builder.BuildMusica();
        builder.BuildSonidos();
        builder.BuildImagenes();
        return builder.getPartida();
    }
}
