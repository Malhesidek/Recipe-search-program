package com.recipe.recipeservice.repo.model;


import javax.persistence.*;

@Entity
@Table(name = "recipe")
public final class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recipe_id;

    private String title;

    private String product;

    private String instruction;

    public Recipe() {
    }

    public Recipe(String title, String product, String instruction) {
        this.title = title;
        this.product = product;
        this.instruction = instruction;
    }

    public Long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
