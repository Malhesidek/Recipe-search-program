package com.subject.subjectservice.service;


import com.subject.subjectservice.api.dto.RecipesDto;
import com.subject.subjectservice.repo.SubjRepo;
import com.subject.subjectservice.repo.model.Subj;
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
public final class SubjService {

    private final SubjRepo subjRepo;
    private final String recipeUrl = "http://recipe-service:8080/recipe/";

    public List<Subj> fetchAll(){
        return subjRepo.findAll();
    }

    public Subj fetchById(Long subj_id) throws IllegalArgumentException{
        Optional<Subj> maybeSubj = subjRepo.findById(subj_id);

        if (maybeSubj.isEmpty()) throw new IllegalArgumentException("Subj not found");
        else return maybeSubj.get();
    }

    public Long create(String title, Long recipes_id) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(recipes_id);
        try {
            final ResponseEntity<RecipesDto> response = restTemplate.exchange(recipeUrl + recipes_id, HttpMethod.GET, request, RecipesDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException(String.format("User with id = %d was not found", recipes_id));}

        final Subj subj = new Subj(title, recipes_id);
        final Subj savedSubj = subjRepo.save(subj);

        return savedSubj.getSubj_id();
    }

    public void update(Long subj_id, String title, Long recipes_id) throws IllegalArgumentException{
        Optional<Subj> maybeSubj = subjRepo.findById(subj_id);

        if (maybeSubj.isEmpty()) throw new IllegalArgumentException("Subj not found");

        final Subj subj = maybeSubj.get();
        if (title != null && !title.isBlank()) subj.setTitle(title);
        if (recipes_id != null && !recipes_id.describeConstable().isEmpty()) subj.setRecipes_id(recipes_id);
        subjRepo.save(subj);
    }

    public void delete(Long subj_id) {
        subjRepo.deleteById(subj_id);
    }

//    public JSONObject getRecipe(Long recipes_id){
//        RestTemplate newRestTemplate = new RestTemplate();
//        try{
//            ResponseEntity<JSONObject> recipeResponse = newRestTemplate.exchange(recipeService + "/" + recipes_id, HttpMethod.GET, null, JSONObject.class);
//            return recipeResponse.getBody();
//        } catch (HttpClientErrorException e){
//            throw new IllegalArgumentException("No recipe");
//        }
//    }

    public RecipesDto getRecipeById(Long subj_id) throws IllegalArgumentException{
        Optional<Subj> byId = subjRepo.findById(subj_id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Subj with id = %d does not exist ", subj_id));
        Long recipes_id = byId.get().getRecipes_id();
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(recipes_id);
        final ResponseEntity<RecipesDto> response = restTemplate.exchange(recipeUrl + recipes_id, HttpMethod.GET, request, RecipesDto.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("Recipe with id = %d does not exist", recipes_id));
        }
        return response.getBody();
    }


}
