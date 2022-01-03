package com.subject.subjectservice.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipesDto {

    private Long recipe_id;

    private String title;

    private String product;

    private String instruction;
}
