package com.elderpereira.msanime.domain.anime.repository;

import com.elderpereira.msanime.domain.anime.model.Anime;
import java.util.List;
import java.util.Optional;

public interface AnimeRepository {

  // Create
  Anime save(Anime anime);
  
  // Read
  Optional<Anime> findById(Long id);
  List<Anime> findAll();
  Optional<Anime> findByName(String name);
  List<Anime> findByEpisodes(Integer episodes);
  
  // Update
  Anime update(Anime anime);
  
  // Delete
  void deleteById(Long id);
  void delete(Anime anime);
  boolean existsById(Long id);
  
}
