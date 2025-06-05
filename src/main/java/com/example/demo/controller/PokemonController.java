package com.example.demo.controller;

import com.example.demo.dto.PokemonResponse;
import com.example.demo.service.PokemonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemon/{name}")
    public PokemonResponse getPokemon(@PathVariable("name") String name) {
        return pokemonService.getPokemon(name);
    }

}
