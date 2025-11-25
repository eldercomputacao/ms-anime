package com.elderpereira.msanime.infra.databases.memory.anime;

import com.elderpereira.msanime.domain.anime.model.Anime;
import com.elderpereira.msanime.domain.anime.repository.AnimeRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class AnimeMemoryRepository implements AnimeRepository {
  
  private final Map<Long, Anime> animes = new HashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  public AnimeMemoryRepository() {
    initializeData();
  }

  private void initializeData() {
    save(Anime.builder().name("Naruto").episodes(720).build());
    save(Anime.builder().name("One Piece").episodes(1000).build());
    save(Anime.builder().name("Dragon Ball Z").episodes(291).build());
    save(Anime.builder().name("Attack on Titan").episodes(75).build());
    save(Anime.builder().name("Death Note").episodes(37).build());
  }

  @Override
  public Anime save(Anime anime) {
    Long id = anime.getId() != null ? anime.getId() : idCounter.incrementAndGet();
    Anime savedAnime = Anime.builder()
        .id(id)
        .name(anime.getName())
        .episodes(anime.getEpisodes())
        .build();
    animes.put(id, savedAnime);
    return savedAnime;
  }

  @Override
  public Optional<Anime> findById(Long id) {
    return Optional.ofNullable(animes.get(id));
  }

  @Override
  public List<Anime> findAll() {
    return new ArrayList<>(animes.values());
  }

  @Override
  public Optional<Anime> findByName(String name) {
    return animes.values().stream()
        .filter(anime -> anime.getName().equalsIgnoreCase(name))
        .findFirst();
  }

  @Override
  public List<Anime> findByEpisodes(Integer episodes) {
    return animes.values().stream()
        .filter(anime -> anime.getEpisodes().equals(episodes))
        .collect(Collectors.toList());
  }

  @Override
  public Anime update(Anime anime) {
    if (anime.getId() == null || !animes.containsKey(anime.getId())) {
      throw new RuntimeException("Anime not found for update");
    }
    animes.put(anime.getId(), anime);
    return anime;
  }

  @Override
  public void deleteById(Long id) {
    animes.remove(id);
  }

  @Override
  public void delete(Anime anime) {
    if (anime.getId() != null) {
      animes.remove(anime.getId());
    }
  }

  @Override
  public boolean existsById(Long id) {
    return animes.containsKey(id);
  }
}
