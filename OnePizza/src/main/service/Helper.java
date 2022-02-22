package com.onepizzahashcode2022.service;

import com.onepizzahashcode2022.model.Customer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ve_code
 */
public class Helper {
    private Map<String, Integer> stringToIngredientId;
    private Map<Integer, String> ingredientIdToString;


    public List<Customer> getInputFromFile(String fileName) throws FileNotFoundException, IOException {
        List<Customer> customers = null;
        try {
            BufferedReader fr = new BufferedReader(new FileReader("input\\" + fileName));
            String line;

            int nbrCustomer = Integer.parseInt(fr.readLine());
            customers = new ArrayList<Customer>(nbrCustomer); 
            stringToIngredientId = new HashMap();
            ingredientIdToString = new HashMap();

            while ((line = fr.readLine()) != null) {

                String likedIngredientInput[] = line.split(" ");
                int likedIngredientCount = Integer.parseInt(likedIngredientInput[0]);
                List<Integer> likedIngredientCustomer = new ArrayList(likedIngredientCount);
            
                for (int i = 1; i < likedIngredientInput.length; i++) {
                    Integer ingredientId = stringToIngredientId.get(likedIngredientInput[i]);
                    if(ingredientId == null) {
                        ingredientId = stringToIngredientId.size();
                        stringToIngredientId.put(likedIngredientInput[i], ingredientId);
                        ingredientIdToString.put(ingredientId, likedIngredientInput[i]);
                    }
                    likedIngredientCustomer.add(ingredientId);
                }


                
                line = fr.readLine();
                String dislikedIngredientInput[] = line.split(" ");
                int dislikedIngredientCount = Integer.parseInt(dislikedIngredientInput[0]);
                List<Integer> dislikedIngredientCustomer = new ArrayList(dislikedIngredientCount);
                
                for (int i = 1; i < dislikedIngredientInput.length; i++) {
                    Integer ingredientId = stringToIngredientId.get(dislikedIngredientInput[i]);
                    if(ingredientId == null) {
                        ingredientId = stringToIngredientId.size();
                        stringToIngredientId.put(dislikedIngredientInput[i], ingredientId);
                        ingredientIdToString.put(ingredientId, dislikedIngredientInput[i]);
                    }
                    dislikedIngredientCustomer.add(ingredientId);
                }

                Customer customer = new Customer(likedIngredientCustomer, dislikedIngredientCustomer);
                customers.add(customer);

            }
            fr.close();

        } catch (IOException ex) {
            System.out.println("ex " + ex.getMessage());
        }
        return customers;
    }

    public Map<String, Integer> getAllIngredient() {
        return stringToIngredientId;
    }

    /*
    public Map<String, Integer> bruteForce(List<Customer> customers, Map<String, Integer> allIngredient) {
            HashMap<String, Integer> ingredientVectorToCustomerCount = new HashMap();


            for(Customer customer : customers){
                HashMap<String, Integer> nextIngredientVectorToCustomerCount = new HashMap();
                for(Map.Entry<String, Integer> ingredientVector : ingredientVectorToCustomerCount.entrySet()){
                    
                }
            }


    }*/

    public List<Integer> algoYatheen(List<Customer> customers){
        int[] ingredientToLikes = new int[stringToIngredientId.size()];
        for(Customer customer : customers){
            for(int ingredient : customer.getLikedIngredients())
                ingredientToLikes[ingredient]++;
            for(int ingredient : customer.getDislikedIngredients())
                ingredientToLikes[ingredient]--;
        }
        List<Integer> choosedIngredients = new LinkedList();
        for(int i = 0; i < ingredientToLikes.length; i++)
            if(ingredientToLikes[i] > 0)
                choosedIngredients.add(i);
        return choosedIngredients;
    }



    

    public List<String> toArrayList(List<Integer> choosedIngredientIds) {
        List<String> choosedIngredients = new ArrayList(choosedIngredientIds.size());
        for (Integer ingredientId  : choosedIngredientIds)
            choosedIngredients.add(ingredientIdToString.get(ingredientId));
        return choosedIngredients;
    }



    public void writeIntoFile(List<String> choosedIngredient, String fileName) {
        try {
            System.out.println("\n------- output " + fileName);
            PrintWriter outputFile = new PrintWriter("output\\" + fileName + ".out", "UTF-8");
            outputFile.print(choosedIngredient.size() + " ");
            for (String ingredient : choosedIngredient) {
                outputFile.print(ingredient + " ");
            }

            outputFile.close();
        } catch (Exception e) {
            System.err.println("" + e);
        }
    }
}


/*


//removes ingredient that is disliked by any customer
    public Map<String, Integer> algorithm_1(List<Customer> customers, Map<String, Integer> stringToIngredientId) {
        for (Customer customer : customers) {
            //iterating through customers dislikes
            for (String dislikedIngredient : customer.getDislikedIngredients()) {
                //removing ingredient that is disliked by any customer
                if (stringToIngredientId.containsKey(dislikedIngredient))
                    stringToIngredientId.remove(dislikedIngredient);
            }
        }

        return stringToIngredientId;

    }

public Map<String, Integer> algorithm_2(List<Customer> customers) {
        Collections.sort(customers, new CustomerComparator());
        Map<String, Integer> choosedIngredient = new HashMap();

        for (Customer customer : customers) {
            boolean isTakenIngredient = true;
            // iterate over whole dislikes ingredient;
            for (String dislikedIngredient : customer.getDislikedIngredients()) {
                if (choosedIngredient.containsKey(dislikedIngredient)) {
                    isTakenIngredient = false;
                    break;
                }
            }

            if (isTakenIngredient) {
                for (String likedIngredient : customer.getLikedIngredients()) {
                    choosedIngredient.put(likedIngredient, 0);
                }
            }

        }
        return choosedIngredient;
    }



//selecting ingredients which are are more liked and less disliked
    public Map<String, Integer> algorithm_3(List<Customer> customers) {

        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, Integer> map1 = new HashMap<String, Integer>();

        for (Customer customer : customers) {

            for (String liked : customer.getLikedIngredients()) {
                if (map.containsKey(liked)) {
                    map.put(liked, map.get(liked) + 1);
                } else {
                    map.put(liked, 1);
                }
            }

            for (String disliked : customer.getDislikedIngredients()) {
                if (map.containsKey(disliked)) {
                    map.put(disliked, map.get(disliked) - 1);
                } else {
                    map.put(disliked, 0);
                }
            }
        }

        // preparing string of final ingredients
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            if (entry.getValue() > 0) {
                map1.put(entry.getKey(), entry.getValue());
            }

        }

        return map1;

    }

    public List<String> toArrayListAlgo3(Map<String, Integer> allIngredient) {
        List<String> choosedIngredient = new ArrayList();

        // preparing string of final ingredients
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            if (entry.getValue() > 0) {
                choosedIngredient.add(entry.getKey());
            }

        }

        return choosedIngredient;
    }



//Approach
    //1. Remove Customer which dislikes are move than 2
    //2. Selecting Ingredients whose likes are more than dislikes
    public Map<String, Integer> algorithm_4(List<Customer> customers){
        
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, Integer> map1 = new HashMap<String, Integer>();

        for (Customer customer : customers) {

            //skipping customer whose disliked are more
            if(customer.getDislikedIngredients().size() > 2) continue;

            for (String liked : customer.getLikedIngredients()) {
                if (map.containsKey(liked)) {
                    map.put(liked, map.get(liked) + 1);
                } else {
                    map.put(liked, 1);
                }
            }

            for (String disliked : customer.getDislikedIngredients()) {
                if (map.containsKey(disliked)) {
                    map.put(disliked, map.get(disliked) - 1);
                } else {
                    map.put(disliked, 0);
                }
            }
        }

        // preparing string of final ingredients
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            if (entry.getValue() > 0) {
                map1.put(entry.getKey(), entry.getValue());
            }

        }

        return map1;
    }

*/