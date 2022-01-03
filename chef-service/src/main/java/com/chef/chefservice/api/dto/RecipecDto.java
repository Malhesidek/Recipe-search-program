package com.chef.chefservice.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipecDto {

    private Long recipe_id;

    private String title;

    private String product;

    private String instruction;
}

