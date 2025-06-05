package com.example.demo.service;

import com.example.demo.dto.PokemonResponse;
import com.example.demo.feign.PokemonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonClient pokemonClient;

    public PokemonServiceImpl(PokemonClient pokemonClient) {
        this.pokemonClient = pokemonClient;
    }

    @Override
    @Cacheable(cacheNames = "pokemon")
    public PokemonResponse getPokemon(String name) {
        System.out.println("Haciendo llamada real a POKEAPI con pokemon :  " + name);
        return pokemonClient.getPokemonByName(name.toLowerCase());
    }
}