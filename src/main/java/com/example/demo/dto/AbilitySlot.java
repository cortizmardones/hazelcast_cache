package com.example.demo.dto;

import lombok.Data;

@Data
public class AbilitySlot {

    private Ability ability;
    private boolean is_hidden;
    private int slot;

}
