package com.chef.chefservice.service;

import com.chef.chefservice.api.dto.RecipecDto;
import com.chef.chefservice.repo.ChefRepo;
import com.chef.chefservice.repo.model.Chef;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ChefService {

    private final ChefRepo chefRepo;
    private final String recipeUrl = "http://recipe-service:8080/recipe/";

    public List<Chef> fetchAll(){
        return chefRepo.findAll();
    }

    public Chef fetchById(Long chef_id) throws IllegalArgumentException{
        Optional<Chef> maybeChef = chefRepo.findById(chef_id);

        if (maybeChef.isEmpty()) throw new IllegalArgumentException("Chef not found");
        else return maybeChef.get();
    }

    public Long create(String firstname, String lastname, String country, String restaurant, String bio, Long recipec_id) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(recipec_id);
        try {
            final ResponseEntity<RecipecDto> response = restTemplate.exchange(recipeUrl + recipec_id, HttpMethod.GET, request, RecipecDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException(String.format("Recipe with id = %d does not exist", recipec_id));}

        final Chef chef = new Chef(firstname, lastname, country, restaurant, bio, recipec_id);
        final Chef savedChef = chefRepo.save(chef);

        return savedChef.getChef_id();
    }

    public void update(Long chef_id, String firstname, String lastname, String country, String restaurant, String bio, Long recipec_id) throws IllegalArgumentException{
        Optional<Chef> maybeChef = chefRepo.findById(chef_id);

        if (maybeChef.isEmpty()) throw new IllegalArgumentException("Chef not found");

        final Chef chef = maybeChef.get();
        if (firstname != null && !firstname.isBlank()) chef.setFirstname(firstname);
        if (lastname != null && !lastname.isBlank()) chef.setLastname(lastname);
        if (country != null && !country.isBlank()) chef.setCountry(country);
        if (restaurant != null && !restaurant.isBlank()) chef.setCountry(restaurant);
        if (bio != null && !bio.isBlank()) chef.setBio(bio);
        if (recipec_id != null && !recipec_id.describeConstable().isEmpty()) chef.setRecipec_id(recipec_id);
        chefRepo.save(chef);
    }

    public void delete(Long chef_id) {
        chefRepo.deleteById(chef_id);
    }


    public RecipecDto getRecipeById(Long chef_id) throws IllegalArgumentException{
        Optional<Chef> byId = chefRepo.findById(chef_id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Chef with id = %d does not exist ", chef_id));
        Long recipec_id = byId.get().getRecipec_id();
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(recipec_id);
        final ResponseEntity<RecipecDto> response = restTemplate.exchange(recipeUrl + recipec_id, HttpMethod.GET, request, RecipecDto.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("Recipe with id = %d does not exist", recipec_id));
        }
        return response.getBody();
    }


}

