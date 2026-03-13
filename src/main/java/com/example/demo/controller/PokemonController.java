package com.example.demo.controller;

import com.example.demo.dto.PokemonResponse;
import com.example.demo.service.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/pokemon/{name}")
    public PokemonResponse getPokemon(@PathVariable("name") String name) {

        log.info("Solicitud recibida para consultar el Pokémon con nombre: {}", name);
        PokemonResponse response = pokemonService.getPokemon(name);
        log.info("Pokémon consultado correctamente: {}", name);
        return response;

    }

}
