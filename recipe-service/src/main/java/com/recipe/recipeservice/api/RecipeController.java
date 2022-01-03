package com.recipe.recipeservice.api;

import com.recipe.recipeservice.api.dto.ChefsDto;
import com.recipe.recipeservice.api.dto.RecipeDto;
import com.recipe.recipeservice.api.dto.SubjsDto;
import com.recipe.recipeservice.repo.RecipeRepo;
import com.recipe.recipeservice.repo.model.Recipe;
import com.recipe.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipe")
public final class RecipeController {


    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<Recipe>> index(){
        final List<Recipe> recipes = recipeService.fetchAll();

        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{recipe_id}")
    public ResponseEntity<Recipe> show(@PathVariable Long recipe_id){
        try {
            final Recipe recipe = recipeService.fetchById(recipe_id);
            return ResponseEntity.ok(recipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody RecipeDto recipe) {
        final String title = recipe.getTitle();
        final String product = recipe.getProduct();
        final String instruction = recipe.getInstruction();
        final Long recipe_id = recipeService.create(title, product, instruction);
        final String location = String.format("/recipe/%d", recipe_id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{recipe_id}")
    public ResponseEntity<Recipe> update(@PathVariable Long recipe_id, @RequestBody RecipeDto recipe) {
        final String title = recipe.getTitle();
        final String product = recipe.getProduct();
        final String instruction = recipe.getInstruction();
        try {
            recipeService.update(recipe_id, title, product, instruction);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{recipe_id}")
    public ResponseEntity<Void> delete(@PathVariable Long recipe_id){
        recipeService.delete(recipe_id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{recipe_id}/subj")
    public ResponseEntity<List<SubjsDto>> showSubjById(@PathVariable Long recipe_id){
        try {
            final List<SubjsDto> subjs = recipeService.getSubjsById(recipe_id);

            return ResponseEntity.ok(subjs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{recipe_id}/chef")
    public ResponseEntity<List<ChefsDto>> showChefById(@PathVariable Long recipe_id){
        try {
            final List<ChefsDto> chefs = recipeService.getChefsById(recipe_id);

            return ResponseEntity.ok(chefs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
