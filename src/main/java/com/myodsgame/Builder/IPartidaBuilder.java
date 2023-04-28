package com.myodsgame.Builder;

import com.myodsgame.Models.Partida;

public interface IPartidaBuilder {
    public void BuildRetos();
    public void BuildMusica();
    public void BuildSonidos();
    public void BuildImagenes();
    public Partida getPartida();
}
