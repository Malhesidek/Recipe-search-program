package com.recipe.recipeservice.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjsDto {

    private Long subj_id;

    private String title;

    private Long recipes_id;
}
