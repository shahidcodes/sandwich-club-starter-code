package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        Log.d(TAG, "parseSandwichJson: " + json);
        try {
            JSONObject sandwichJSONObject = new JSONObject(json);

            JSONObject sandwichNameObject = sandwichJSONObject.getJSONObject("name");

            String placeOfOrigin = sandwichJSONObject.getString("placeOfOrigin");
            String description = sandwichJSONObject.getString("description");
            String imgUrl = sandwichJSONObject.getString("image");
            String sandwichName = sandwichNameObject.getString("mainName");
            // get also known as array
            JSONArray alsoKnowAsArray = sandwichNameObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnowAsList = new ArrayList<>();
            for (int i=0; i<alsoKnowAsArray.length(); i++){
                alsoKnowAsList.add( alsoKnowAsArray.getString(i) );
            }

            JSONArray ingredientsArray = sandwichJSONObject.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int j=0; j<ingredientsArray.length(); j++){
                ingredientsList.add(
                        ingredientsArray.getString(j) );
            }


            return new Sandwich(sandwichName, alsoKnowAsList, placeOfOrigin, description, imgUrl, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
