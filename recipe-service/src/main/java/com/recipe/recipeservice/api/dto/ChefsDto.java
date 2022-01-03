package com.recipe.recipeservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChefsDto {

    private String chef_id;

    private String firstname;

    private String lastname;

    private String country;

    private String restaurant;

    private String bio;

    private Long recipec_id;
}
