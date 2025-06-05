package com.example.demo.service;

import com.example.demo.dto.PokemonResponse;

public interface PokemonService {

    PokemonResponse getPokemon(String name);

}