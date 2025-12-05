package com.elderpereira.msanime.application.services;

import com.elderpereira.msanime.domain.anime.model.Anime;
import com.elderpereira.msanime.domain.anime.repository.AnimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimeFacadeTest {

    @Mock
    private AnimeRepository animeRepository;

    @InjectMocks
    private AnimeFacade animeFacade;

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
    @DisplayName("Should find anime by name successfully")
    void shouldFindAnimeByName() {
        String name = "Naruto";
        when(animeRepository.findByName(name)).thenReturn(Optional.of(anime1));

        Anime result = animeFacade.findByName(name);

        assertNotNull(result);
        assertEquals("Naruto", result.getName());
        assertEquals(720, result.getEpisodes());
        verify(animeRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should throw exception when anime not found by name")
    void shouldThrowExceptionWhenAnimeNotFoundByName() {
        String name = "Non Existing Anime";
        when(animeRepository.findByName(name)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            animeFacade.findByName(name);
        });

        assertEquals("Anime not found", exception.getMessage());
        verify(animeRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Should return all animes")
    void shouldReturnAllAnimes() {
        List<Anime> animes = Arrays.asList(anime1, anime2);
        when(animeRepository.findAll()).thenReturn(animes);

        List<Anime> result = animeFacade.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Naruto", result.get(0).getName());
        assertEquals("One Piece", result.get(1).getName());
        verify(animeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no animes exist")
    void shouldReturnEmptyListWhenNoAnimesExist() {
        when(animeRepository.findAll()).thenReturn(Arrays.asList());

        List<Anime> result = animeFacade.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(animeRepository, times(1)).findAll();
    }
}
