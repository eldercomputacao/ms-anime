package com.elderpereira.msanime.infra.controllers;

import com.elderpereira.msanime.application.services.AnimeFacade;
import com.elderpereira.msanime.domain.anime.model.Anime;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animes")
@AllArgsConstructor
public class AnimeController {

    private final AnimeFacade animeFacade;

    @GetMapping("/{name}")
    public ResponseEntity<Anime> findByName(@PathVariable String name) {
        Anime anime = animeFacade.findByName(name);
        return ResponseEntity.ok(anime);
    }

    @GetMapping
    public ResponseEntity<List<Anime>> findAll() {
        List<Anime> animes = animeFacade.findAll();
        return ResponseEntity.ok(animes);
    }
}




