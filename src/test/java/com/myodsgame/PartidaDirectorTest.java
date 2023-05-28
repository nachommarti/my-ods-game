package com.myodsgame;

import com.myodsgame.Builder.IPartidaBuilder;
import com.myodsgame.Builder.PartidaDirector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class PartidaDirectorTest {
    private IPartidaBuilder mockBuilder;
    private PartidaDirector director;

    @BeforeEach
    public void setUp() {
        // Crear un mock de IPartidaBuilder
        mockBuilder = Mockito.mock(IPartidaBuilder.class);
        // Crear una instancia de PartidaDirector con el mock builder
        director = new PartidaDirector(mockBuilder);
    }

    @Test
    public void testBuildPartida() {
        // Llamamos a BuildPartida
        director.BuildPartida();

        // Verificamos que los métodos correspondientes de IPartidaBuilder se llaman
        verify(mockBuilder).BuildRetos();
        verify(mockBuilder).BuildMusica();
        verify(mockBuilder).BuildSonidos();
        verify(mockBuilder).BuildImagenes();
        verify(mockBuilder).getPartida();
    }
}
