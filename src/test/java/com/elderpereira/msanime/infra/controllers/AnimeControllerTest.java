package com.elderpereira.msanime.infra.controllers;

import com.elderpereira.msanime.application.services.AnimeFacade;
import com.elderpereira.msanime.domain.anime.model.Anime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimeControllerTest {

    @Mock
    private AnimeFacade animeFacade;

    @InjectMocks
    private AnimeController animeController;

    private Anime anime1;
    private Anime anime2;

    @BeforeEach
    void setUp() {
        anime1 = Anime.builder()
            .id(1L)
            .name("Naruto")
            .episodes(720)
            .build();

        anime2 = Anime.builder()
            .id(2L)
            .name("One Piece")
            .episodes(1000)
            .build();
    }

    @Test
    @DisplayName("Should find anime by name and return 200 OK")
    void shouldFindAnimeByNameAndReturn200() {
        String name = "Naruto";
        when(animeFacade.findByName(name)).thenReturn(anime1);

        ResponseEntity<Anime> response = animeController.findByName(name);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Naruto", response.getBody().getName());
        assertEquals(720, response.getBody().getEpisodes());
        verify(animeFacade, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should throw exception when anime not found by name")
    void shouldThrowExceptionWhenAnimeNotFoundByName() {
        String name = "Non Existing Anime";
        when(animeFacade.findByName(name))
            .thenThrow(new IllegalArgumentException("Anime not found"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            animeController.findByName(name);
        });

        assertEquals("Anime not found", exception.getMessage());
        verify(animeFacade, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should find all animes and return 200 OK")
    void shouldFindAllAnimesAndReturn200() {
        List<Anime> animes = Arrays.asList(anime1, anime2);
        when(animeFacade.findAll()).thenReturn(animes);

        ResponseEntity<List<Anime>> response = animeController.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Naruto", response.getBody().get(0).getName());
        assertEquals("One Piece", response.getBody().get(1).getName());
        verify(animeFacade, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no animes exist")
    void shouldReturnEmptyListWhenNoAnimesExist() {
        when(animeFacade.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<List<Anime>> response = animeController.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(animeFacade, times(1)).findAll();
    }
}
