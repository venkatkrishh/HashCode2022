package com.onepizzahashcode2022.model;

import java.util.List;

/**
 *
 * @author ve_code
 */
public class Customer {
    private List<Integer> likedIngredients;
    private List<Integer> dislikedIngredients;

    public Customer(List<Integer> likedIngredients, List<Integer> dislikedIngredients) {
        this.likedIngredients = likedIngredients;
        this.dislikedIngredients = dislikedIngredients;
    }

    public List<Integer> getLikedIngredients() {
        return likedIngredients;
    }

    public List<Integer> getDislikedIngredients() {
        return dislikedIngredients;
    }
    
}
