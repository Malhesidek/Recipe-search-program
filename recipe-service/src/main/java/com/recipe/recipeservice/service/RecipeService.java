package com.recipe.recipeservice.service;


import com.recipe.recipeservice.api.dto.ChefsDto;
import com.recipe.recipeservice.api.dto.SubjsDto;
import com.recipe.recipeservice.repo.RecipeRepo;
import com.recipe.recipeservice.repo.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class RecipeService {

    private final RecipeRepo recipeRepo;
    private final String subjUrl = "http://subj-service:8081/subj/";
    private final String chefUrl = "http://chef-service:8082/chef/";

    public List<Recipe> fetchAll(){
        return recipeRepo.findAll();
    }

    public Recipe fetchById(Long recipe_id) throws IllegalArgumentException{
        Optional<Recipe> maybeRecipe = recipeRepo.findById(recipe_id);

        if (maybeRecipe.isEmpty()) throw new IllegalArgumentException("Recipe not found");
        else return maybeRecipe.get();
    }

    public Long create(String title, String product, String instruction) {
        final Recipe recipe = new Recipe(title, product, instruction);
        final Recipe savedRecipe = recipeRepo.save(recipe);

        return savedRecipe.getRecipe_id();
    }

    public void update(Long recipe_id, String title, String product, String instruction) throws IllegalArgumentException{
        Optional<Recipe> maybeRecipe = recipeRepo.findById(recipe_id);

        if (maybeRecipe.isEmpty()) throw new IllegalArgumentException("Recipe not found");

        final Recipe recipe = maybeRecipe.get();
        if (title != null && !title.isBlank()) recipe.setTitle(title);
        if (product != null && !product.isBlank()) recipe.setProduct(product);
        if (instruction != null && !instruction.isBlank()) recipe.setInstruction(instruction);
        recipeRepo.save(recipe);
    }

    public void delete(Long recipe_id) {
        recipeRepo.deleteById(recipe_id);
    }

    public List<SubjsDto> getSubjsById (Long recipe_id) throws IllegalArgumentException{
        Optional<Recipe> byId = recipeRepo.findById(recipe_id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Subj with id = %d was not found", recipe_id));
        final Recipe recipe = byId.get();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<SubjsDto>> response = restTemplate.exchange(subjUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<SubjsDto>>() {});
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("Recipe with id = %d has no subj", recipe.getRecipe_id()));
        }
        List<SubjsDto> recipe_subjs = new ArrayList<SubjsDto>();
        for( SubjsDto subjs : Objects.requireNonNull(response.getBody())){
            if(subjs.getRecipes_id() == recipe.getRecipe_id()){
                recipe_subjs.add(subjs);
            }
        }
        return recipe_subjs;
    }

    public List<ChefsDto> getChefsById (Long recipe_id) throws IllegalArgumentException{
        Optional<Recipe> byId = recipeRepo.findById(recipe_id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("Recipe with id = %d was not found", recipe_id));
        final Recipe recipe = byId.get();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<List<ChefsDto>> response = restTemplate.exchange(chefUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ChefsDto>>() {});
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("Recipe with id = %d has no chef", recipe.getRecipe_id()));
        }
        List<ChefsDto> recipe_chefs = new ArrayList<ChefsDto>();
        for( ChefsDto chefs : Objects.requireNonNull(response.getBody())){
            if(chefs.getRecipec_id() == recipe.getRecipe_id()){
                recipe_chefs.add(chefs);
            }
        }
        return recipe_chefs;
    }



}
