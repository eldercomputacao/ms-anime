package com.elderpereira.msanime.domain.anime.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Anime {

  private Long id;
  private String name;
  private Integer episodes;


  public int getWordCount() {
    if (name == null || name.trim().isEmpty()) {
      return 0;
    }
    return name.trim().split("\\s+").length;
  }
  
}
