package com.subject.subjectservice.api;

import com.subject.subjectservice.api.dto.RecipesDto;
import com.subject.subjectservice.api.dto.SubjDto;
import com.subject.subjectservice.repo.model.Subj;
import com.subject.subjectservice.service.SubjService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subj")
public final class SubjController {


    private final SubjService subjService;

    @GetMapping
    public ResponseEntity<List<Subj>> index(){
        final List<Subj> subjs = subjService.fetchAll();

        return ResponseEntity.ok(subjs);
    }

    @GetMapping("/{subj_id}")
    public ResponseEntity<Subj> show(@PathVariable Long subj_id){
        try {
            final Subj subj = subjService.fetchById(subj_id);
            return ResponseEntity.ok(subj);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody SubjDto subj) {
        final String title = subj.getTitle();
        final Long recipes_id = subj.getRecipes_id();
        final Long subj_id = subjService.create(title, recipes_id);
        final String location = String.format("/subj/%d", subj_id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{subj_id}")
    public ResponseEntity<Subj> update(@PathVariable Long subj_id, @RequestBody SubjDto subj) {
        final String title = subj.getTitle();
        final Long recipes_id = subj.getRecipes_id();
        try {
            subjService.update(subj_id, title, recipes_id);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{subj_id}")
    public ResponseEntity<Void> delete(@PathVariable Long subj_id){
        subjService.delete(subj_id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{subj_id}/recipe")
    public ResponseEntity<RecipesDto> showRecipeById(@PathVariable long subj_id) {
        try {
            final RecipesDto recipesDto = subjService.getRecipeById(subj_id);

            return ResponseEntity.ok(recipesDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

