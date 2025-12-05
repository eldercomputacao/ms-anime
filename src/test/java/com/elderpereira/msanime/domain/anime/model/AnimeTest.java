package com.elderpereira.msanime.domain.anime.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimeTest {

    @Test
    void shouldCreateAnimeWithBuilder() {
        Anime anime = Anime.builder()
                .id(1L)
                .name("Naruto")
                .episodes(220)
                .build();

        assertNotNull(anime);
        assertEquals(1L, anime.getId());
        assertEquals("Naruto", anime.getName());
        assertEquals(220, anime.getEpisodes());
    }

    @Test
    void shouldReturnWordCountForSingleWordName() {
        Anime anime = Anime.builder()
                .name("Naruto")
                .build();

        assertEquals(1, anime.getWordCount());
    }

    @Test
    void shouldReturnWordCountForMultipleWordsName() {
        Anime anime = Anime.builder()
                .name("One Piece")
                .build();

        assertEquals(2, anime.getWordCount());
    }

    @Test
    void shouldReturnWordCountForNameWithMultipleSpaces() {
        Anime anime = Anime.builder()
                .name("Hunter   X   Hunter")
                .build();

        assertEquals(3, anime.getWordCount());
    }

    @Test
    void shouldReturnZeroWordCountForNullName() {
        Anime anime = Anime.builder()
                .name(null)
                .build();

        assertEquals(0, anime.getWordCount());
    }

    @Test
    void shouldReturnZeroWordCountForEmptyName() {
        Anime anime = Anime.builder()
                .name("")
                .build();

        assertEquals(0, anime.getWordCount());
    }

    @Test
    void shouldReturnZeroWordCountForBlankName() {
        Anime anime = Anime.builder()
                .name("   ")
                .build();

        assertEquals(0, anime.getWordCount());
    }

    @Test
    void shouldReturnWordCountForNameWithLeadingAndTrailingSpaces() {
        Anime anime = Anime.builder()
                .name("  Dragon Ball Z  ")
                .build();

        assertEquals(3, anime.getWordCount());
    }
}
