package com.elderpereira.msanime.application.services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import com.elderpereira.msanime.domain.anime.model.Anime;
import com.elderpereira.msanime.domain.anime.repository.AnimeRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class AnimeFacade {

  private final AnimeRepository animeRepository;

  public Anime findByName(String nome) {
    return animeRepository.findByName(nome)
    .orElseThrow(() -> new IllegalArgumentException("Anime not found"));
  }

  public List<Anime> findAll() {
    return animeRepository.findAll();
  }
  
}
