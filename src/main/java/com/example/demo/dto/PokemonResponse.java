package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PokemonResponse {

    private int id;
    private String name;
    private int height;
    private int weight;
    private String location_area_encounters;
    private List<AbilitySlot> abilities;

}
