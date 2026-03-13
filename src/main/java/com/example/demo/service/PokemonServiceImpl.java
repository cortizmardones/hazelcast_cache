package com.example.demo.service;

import com.example.demo.dto.PokemonResponse;
import com.example.demo.feign.PokemonClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonServiceImpl implements PokemonService {

    private final PokemonClient pokemonClient;

    @Override
    @Cacheable(cacheNames = "cacheServicePokeApi")
    public PokemonResponse getPokemon(String pokemonName) {

        log.info("Realizando llamada REAL (sin caché) a PokeApi, para el pokemón = {}", pokemonName);
        log.info("Consultando información del Pokémon = '{}'", pokemonName);

        PokemonResponse pokemonResponse = pokemonClient.getPokemonByName(pokemonName.toUpperCase());

        log.info("Respuesta recibida desde PokeAPI. pokemon = '{}'", pokemonResponse);
        return pokemonResponse;

    }
}