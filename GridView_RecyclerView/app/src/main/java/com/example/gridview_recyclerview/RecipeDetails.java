package com.example.gridview_recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetails extends AppCompatActivity {

    private TextView recipe_name, recipe_description, recipe_category, recipe_ingredients, recipe_directions;
    private ImageView recipe_image;
    private Button favBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);

        recipe_name = (TextView) findViewById(R.id.txtTitle);
        recipe_description = (TextView) findViewById(R.id.txtDescription);
        recipe_category = (TextView) findViewById(R.id.txtCategory);
        recipe_image = (ImageView) findViewById(R.id.bookthumbnail);
        recipe_ingredients = (TextView) findViewById(R.id.txtIngredients);
        recipe_directions = (TextView) findViewById(R.id.txtDirections);
        favBtn = findViewById(R.id.favBtn);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecipeDetails.this, "Favorite button pressed", Toast.LENGTH_SHORT).show();

                //****how to get this foodItem to hold all variables?*******
                RecipeManager recipeManager = new RecipeManager();
                FoodItem foodItem = new FoodItem();

                FavoritesManager favoritesManager = new FavoritesManager(RecipeDetails.this);

                //*****saving empty foodItem right now?*******
                favoritesManager.saveFavorites(foodItem);
            }
        });

        //***********Receive data*************
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String Category = intent.getExtras().getString("Category");
        int image = intent.getExtras().getInt("Thumbnail");
        String Ingredients = intent.getExtras().getString("Ingredients");
        String Directions = intent.getExtras().getString("Directions");

        //*********Setting values**********

        recipe_name.setText(Title);
        recipe_description.setText(Description);
        recipe_category.setText(Category);
        recipe_image.setImageResource(image);
        recipe_ingredients.setText(Ingredients);
        recipe_directions.setText(Directions);

    }
}