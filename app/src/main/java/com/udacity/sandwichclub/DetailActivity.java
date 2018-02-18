package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String N_A = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView sandwichIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException exc) {
            Log.e(TAG, "Could not parse Json data: " + exc.getMessage());
            sandwich = null;
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView akaTextView = (TextView) findViewById(R.id.also_known_tv);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        TextView originTextView = (TextView) findViewById(R.id.origin_tv);

        // Origin
        String origin = sandwich.getPlaceOfOrigin();
        if(null == origin || origin.isEmpty()) {
            originTextView.setText(N_A);
        } else {
            originTextView.setText(origin);
        }

        // Ingredients
        List<String> ingredients = sandwich.getIngredients();
        if(null == ingredients || ingredients.isEmpty()){
            ingredientsTextView.setText(N_A);
        } else {
            ingredientsTextView.setText(implode(ingredients));
        }

        // Description
        String description = sandwich.getDescription();
        if(null == description || description.isEmpty()) {
            descriptionTextView.setText(N_A);
        } else {
            descriptionTextView.setText(description);
        }

        // Also Known As
        List<String> akaList = sandwich.getAlsoKnownAs();
        if(null == akaList || akaList.isEmpty()){
            akaTextView.setText(N_A);
        } else {
            akaTextView.setText(implode(akaList));
        }
    }

    private String implode(List<String> stringList) {
        String result = "";
        for (String item : stringList) {
            if(result.isEmpty()) {
                result = item;
            } else {
                result = item + ", " + result;
            }
        }
        return result;
    }
}
