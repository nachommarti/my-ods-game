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
        mockBuilder = Mockito.mock(IPartidaBuilder.class);
        director = new PartidaDirector(mockBuilder);
    }

    @Test
    public void testBuildPartida() {
        director.BuildPartida();

        verify(mockBuilder).BuildRetos();
        verify(mockBuilder).BuildMusica();
        verify(mockBuilder).BuildSonidos();
        verify(mockBuilder).BuildImagenes();
        verify(mockBuilder).getPartida();
    }
}
