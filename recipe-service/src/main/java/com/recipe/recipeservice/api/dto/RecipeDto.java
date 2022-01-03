package com.recipe.recipeservice.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class RecipeDto {

    private String title;

    private String product;

    private String instruction;
}
