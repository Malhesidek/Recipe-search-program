package com.chef.chefservice.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "chef")
public final class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chef_id;

    private String firstname;

    private String lastname;

    private String country;

    private String restaurant;

    private String bio;

    private Long recipec_id;

    public Chef() {
    }

    public Chef(String firstname, String lastname, String country, String restaurant, String bio, Long recipec_id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.restaurant = restaurant;
        this.bio = bio;
        this.recipec_id = recipec_id;
    }

    public Long getChef_id() {
        return chef_id;
    }

    public void setChef_id(Long chef_id) {
        this.chef_id = chef_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getRecipec_id() {
        return recipec_id;
    }

    public void setRecipec_id(Long recipec_id) {
        this.recipec_id = recipec_id;
    }
}

