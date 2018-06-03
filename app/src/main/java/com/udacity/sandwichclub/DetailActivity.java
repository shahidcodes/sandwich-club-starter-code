package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView description, knownAs, origin, ingridients,  originLabel, knownAsLabel, ingridientsLabel;
        description = findViewById(R.id.description_tv);
        knownAs = findViewById(R.id.also_known_tv);
        origin = findViewById(R.id.origin_tv);

        originLabel = findViewById(R.id.origin_label);
        knownAsLabel = findViewById(R.id.known_label);
        ingridientsLabel = findViewById(R.id.ingridient_label);

        ingridients = findViewById(R.id.ingridients_tv);

        description.setText(sandwich.getDescription());

        if (sandwich.getAlsoKnownAs().size() == 0){
            knownAsLabel.setVisibility(View.GONE);
            knownAs.setVisibility(View.GONE);
        }else {
            for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
                knownAs.append(alsoKnownAs + ", ");
            }
        }


        if (sandwich.getPlaceOfOrigin().equals("")){
            origin.setVisibility(View.GONE);
            originLabel.setVisibility(View.GONE);
        }else origin.setText(sandwich.getPlaceOfOrigin());

        if (sandwich.getIngredients().size() == 0){
            ingridients.setVisibility(View.GONE);
            ingridientsLabel.setVisibility(View.GONE);
        }

        for (String ingrid : sandwich.getIngredients()) {
            ingridients.append(ingrid + ", ");
        }


    }
}
