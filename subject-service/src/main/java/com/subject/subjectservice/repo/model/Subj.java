package com.subject.subjectservice.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "subj")
public final class Subj {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subj_id;

    private String title;

    private Long recipes_id;

    public Subj() {
    }

    public Subj(String title, Long recipes_id) {
        this.title = title;
        this.recipes_id = recipes_id;
    }

    public Long getSubj_id() {
        return subj_id;
    }

    public void setSubj_id(Long subj_id) {
        this.subj_id = subj_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRecipes_id() {
        return recipes_id;
    }

    public void setRecipes_id(Long recipes_id) {
        this.recipes_id = recipes_id;
    }
}

