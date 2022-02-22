package com.onepizzahashcode2022.service;

import com.onepizzahashcode2022.model.Customer;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author ve_code
 */
public class LocalScoreBoardSimulator {

    List<Customer> customers;
    HashSet<Integer> choosedIngredientIds;

    public LocalScoreBoardSimulator(List<Customer> customers, List<Integer> choosedIngredientIds) {
        this.customers = customers;
        this.choosedIngredientIds = new HashSet(choosedIngredientIds);
    }

    public int getScore() {
        int customerCount = 0;

        for (Customer customer : customers) {

            Boolean is_selected = true;

            //checking if any ingredient of customer that is not in our selection 
            for (Integer likedIngredient : customer.getLikedIngredients()) {
                if (!choosedIngredientIds.contains(likedIngredient)) {
                    is_selected = false;
                    break;
                }
            }
            
            if(!is_selected) continue;

            //checking if any ingredient of customer that is disliked by customer
            for (Integer dislikedIngredient : customer.getDislikedIngredients()) {
                if (choosedIngredientIds.contains(dislikedIngredient)) {
                    is_selected = false;
                    break;
                }
            }

            //increase count of customers if choosen ingredients contains all ingredients
            //liked by customer and none of the ingredients disliked by that customer
            if (is_selected)
                customerCount++;
        }

        return customerCount;
    }
}
