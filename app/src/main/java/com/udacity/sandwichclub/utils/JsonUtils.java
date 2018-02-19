package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String DETAIL_NAME = "name";
    private static final String DETAIL_MAIN_NAME = "mainName";
    private static final String DETAIL_AKA = "alsoKnownAs";
    private static final String DETAIL_ORIGIN = "placeOfOrigin";
    private static final String DETAIL_DESCRIPTION = "description";
    private static final String DETAIL_IMAGE = "image";
    private static final String DETAIL_INGREDIENTS = "ingredients";

    /**
     * Parse a json string containing the sandwich details.
     *
     * @param   json    String representing the details of a sandwich
     * @return  Returns a Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        Sandwich sandwich = new Sandwich();
        JSONObject sandwichDetails = new JSONObject(json);

        // Get the name object
        JSONObject jsonObjectName = sandwichDetails.getJSONObject(DETAIL_NAME);

        // Get the main name
        sandwich.setMainName(jsonObjectName.optString(DETAIL_MAIN_NAME));

        // Get the AKA array and convert it to a string list
        JSONArray jsonArrayAka = jsonObjectName.getJSONArray(DETAIL_AKA);
        List<String> aka = new ArrayList<>();
        for (int i=0; i<jsonArrayAka.length();i++) {
            aka.add((String) jsonArrayAka.get(i));
        }
        sandwich.setAlsoKnownAs(aka);

        // Get the description
        sandwich.setDescription(sandwichDetails.optString(DETAIL_DESCRIPTION));

        // Get the image
        sandwich.setImage(sandwichDetails.optString(DETAIL_IMAGE));

        // Get the ingredients
        JSONArray jsonArrayIngredients = sandwichDetails.getJSONArray(DETAIL_INGREDIENTS);
        List<String> ingredients = new ArrayList<>();
        for (int i=0; i<jsonArrayIngredients.length();i++) {
            ingredients.add((String) jsonArrayIngredients.get(i));
        }
        sandwich.setIngredients(ingredients);

        // Get the origin
        sandwich.setPlaceOfOrigin(sandwichDetails.optString(DETAIL_ORIGIN));

        return sandwich;
    }
}
