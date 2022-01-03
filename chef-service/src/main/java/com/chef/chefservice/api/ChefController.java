package com.chef.chefservice.api;

import com.chef.chefservice.api.dto.ChefDto;
import com.chef.chefservice.api.dto.RecipecDto;
import com.chef.chefservice.repo.model.Chef;
import com.chef.chefservice.service.ChefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chef")
public final class ChefController {


    private final ChefService chefService;

    @GetMapping
    public ResponseEntity<List<Chef>> index(){
        final List<Chef> chefs = chefService.fetchAll();

        return ResponseEntity.ok(chefs);
    }

    @GetMapping("/{chef_id}")
    public ResponseEntity<Chef> show(@PathVariable Long chef_id){
        try {
            final Chef subj = chefService.fetchById(chef_id);
            return ResponseEntity.ok(subj);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ChefDto chef) {
        final String firstname = chef.getFirstname();
        final String lastname = chef.getLastname();
        final String country = chef.getCountry();
        final String restaurant = chef.getRestaurant();
        final String bio = chef.getBio();
        final Long recipec_id = chef.getRecipec_id();
        final Long chef_id = chefService.create(firstname, lastname, country, restaurant, bio, recipec_id);
        final String location = String.format("/chef/%d", chef_id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{chef_id}")
    public ResponseEntity<Chef> update(@PathVariable Long chef_id, @RequestBody ChefDto chef) {
        final String firstname = chef.getFirstname();
        final String lastname = chef.getLastname();
        final String country = chef.getCountry();
        final String restaurant = chef.getRestaurant();
        final String bio = chef.getBio();
        final Long recipec_id = chef.getRecipec_id();
        try {
            chefService.update(chef_id, firstname, lastname, country, restaurant, bio, recipec_id);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{chef_id}")
    public ResponseEntity<Void> delete(@PathVariable Long chef_id){
        chefService.delete(chef_id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{chef_id}/recipe")
    public ResponseEntity<RecipecDto> showRecipeById(@PathVariable long chef_id) {
        try {
            final RecipecDto recipecDto = chefService.getRecipeById(chef_id);

            return ResponseEntity.ok(recipecDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}


