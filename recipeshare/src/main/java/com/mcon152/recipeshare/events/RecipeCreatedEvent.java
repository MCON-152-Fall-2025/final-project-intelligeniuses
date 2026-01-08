package com.mcon152.recipeshare.events;

import org.springframework.context.ApplicationEvent;

public class RecipeCreatedEvent extends ApplicationEvent {

    // fields
    private final Long recipeId;
    private final Long authorId;
    private final String recipeTitle;

    // constructor
    public RecipeCreatedEvent(Object source, Long recipeId, Long authorId, String recipeTitle) {
        super(source);
        this.recipeId = recipeId;
        this.authorId = authorId;
        this.recipeTitle = recipeTitle;
    }

    // getters
    public Long getRecipeId() {
        return recipeId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }
}
