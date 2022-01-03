package com.chef.chefservice.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChefDto {

    private String firstname;

    private String lastname;

    private String country;

    private String restaurant;

    private String bio;

    private Long recipec_id;
}
