package com.elderpereira.msanime.infra.databases.memory;

import com.elderpereira.msanime.domain.anime.model.Anime;
import com.elderpereira.msanime.infra.databases.memory.anime.AnimeMemoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnimeMemoryRepositoryTest {

    private AnimeMemoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new AnimeMemoryRepository();
    }

    @Test
    @DisplayName("Should initialize with 5 mock animes")
    void shouldInitializeWithMockData() {
        List<Anime> animes = repository.findAll();
        assertEquals(5, animes.size());
    }

    @Test
    @DisplayName("Should save new anime and generate ID")
    void shouldSaveNewAnime() {
        Anime newAnime = Anime.builder()
            .name("My Hero Academia")
            .episodes(138)
            .build();

        Anime savedAnime = repository.save(newAnime);

        assertNotNull(savedAnime.getId());
        assertEquals("My Hero Academia", savedAnime.getName());
        assertEquals(138, savedAnime.getEpisodes());
        assertEquals(6, repository.findAll().size());
    }

    @Test
    @DisplayName("Should find anime by ID")
    void shouldFindAnimeById() {
        Optional<Anime> anime = repository.findById(1L);
        
        assertTrue(anime.isPresent());
        assertEquals("Naruto", anime.get().getName());
        assertEquals(720, anime.get().getEpisodes());
    }

    @Test
    @DisplayName("Should return empty when anime not found by ID")
    void shouldReturnEmptyWhenAnimeNotFoundById() {
        Optional<Anime> anime = repository.findById(999L);
        assertFalse(anime.isPresent());
    }

    @Test
    @DisplayName("Should find anime by name case insensitive")
    void shouldFindAnimeByNameCaseInsensitive() {
        Optional<Anime> anime = repository.findByName("naruto");
        
        assertTrue(anime.isPresent());
        assertEquals("Naruto", anime.get().getName());
    }

    @Test
    @DisplayName("Should find animes by number of episodes")
    void shouldFindAnimesByEpisodes() {
        List<Anime> animes = repository.findByEpisodes(75);
        
        assertEquals(1, animes.size());
        assertEquals("Attack on Titan", animes.get(0).getName());
    }

    @Test
    @DisplayName("Should update existing anime")
    void shouldUpdateExistingAnime() {
        Anime updatedAnime = Anime.builder()
            .id(1L)
            .name("Naruto Shippuden")
            .episodes(500)
            .build();

        Anime result = repository.update(updatedAnime);

        assertEquals("Naruto Shippuden", result.getName());
        assertEquals(500, result.getEpisodes());
        
        Optional<Anime> found = repository.findById(1L);
        assertTrue(found.isPresent());
        assertEquals("Naruto Shippuden", found.get().getName());
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing anime")
    void shouldThrowExceptionWhenUpdatingNonExistingAnime() {
        Anime nonExistingAnime = Anime.builder()
            .id(999L)
            .name("Non Existing")
            .episodes(10)
            .build();

        assertThrows(RuntimeException.class, () -> repository.update(nonExistingAnime));
    }

    @Test
    @DisplayName("Should delete anime by ID")
    void shouldDeleteAnimeById() {
        assertTrue(repository.existsById(1L));
        
        repository.deleteById(1L);
        
        assertFalse(repository.existsById(1L));
        assertEquals(4, repository.findAll().size());
    }

    @Test
    @DisplayName("Should delete anime by object")
    void shouldDeleteAnimeByObject() {
        Optional<Anime> anime = repository.findById(2L);
        assertTrue(anime.isPresent());
        
        repository.delete(anime.get());
        
        assertFalse(repository.existsById(2L));
        assertEquals(4, repository.findAll().size());
    }

    @Test
    @DisplayName("Should check if anime exists by ID")
    void shouldCheckIfAnimeExistsById() {
        assertTrue(repository.existsById(1L));
        assertFalse(repository.existsById(999L));
    }

    @Test
    @DisplayName("Should return all animes")
    void shouldReturnAllAnimes() {
        List<Anime> animes = repository.findAll();
        
        assertEquals(5, animes.size());
        assertTrue(animes.stream().anyMatch(a -> a.getName().equals("Naruto")));
        assertTrue(animes.stream().anyMatch(a -> a.getName().equals("One Piece")));
        assertTrue(animes.stream().anyMatch(a -> a.getName().equals("Dragon Ball Z")));
        assertTrue(animes.stream().anyMatch(a -> a.getName().equals("Attack on Titan")));
        assertTrue(animes.stream().anyMatch(a -> a.getName().equals("Death Note")));
    }

    @Test
    @DisplayName("Should return empty list when searching for non-existing episodes count")
    void shouldReturnEmptyListWhenSearchingNonExistingEpisodes() {
        List<Anime> animes = repository.findByEpisodes(999);
        assertTrue(animes.isEmpty());
    }

    @Test
    @DisplayName("Should return empty optional when searching for non-existing name")
    void shouldReturnEmptyOptionalWhenSearchingNonExistingName() {
        Optional<Anime> anime = repository.findByName("Non Existing Anime");
        assertFalse(anime.isPresent());
    }
}