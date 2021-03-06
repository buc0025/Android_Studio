package com.example.recipe_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipe_app.managers.CustomRecipeManager;
import com.example.recipe_app.R;
import com.example.recipe_app.models.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CreateRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Button createRecipeBtn = (Button) findViewById(R.id.createRecipeBtn);
        createRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText ownRecipeTitle = findViewById(R.id.ownRecipeTitle);
                EditText ownRecipeCategory = findViewById(R.id.ownRecipeCategory);
                EditText ownRecipeDescription = findViewById(R.id.ownRecipeDescription);
                EditText ownRecipeIngredients = findViewById(R.id.ownRecipeIngredients);
                EditText ownRecipeDirections = findViewById(R.id.ownRecipeDirections);

                CustomRecipeManager customRecipeManager = new CustomRecipeManager(CreateRecipeActivity.this);

                //***********Uses Recipe template**************
                final String customTitle = ownRecipeTitle.getText().toString();
                final Recipe customRecipe = new Recipe(customTitle.replace(" ", "") + Math.random() * Integer.MAX_VALUE,
                        customTitle,
                        ownRecipeCategory.getText().toString(),
                        ownRecipeDescription.getText().toString(),
                        ownRecipeIngredients.getText().toString(),
                        ownRecipeDirections.getText().toString(),
                        R.drawable.stock_food_pic);

                customRecipeManager.saveRecipe(customRecipe);
                                openOwnRecipeActivity();

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.homeNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.favoritesNavigation:
                        startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.homeNavigation:
                        startActivity(new Intent(getApplicationContext(), RecipeCategoriesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.customNavigation:
                        startActivity(new Intent(getApplicationContext(), CustomRecipesListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void openOwnRecipeActivity() {
        EditText ownRecipeTitle = findViewById(R.id.ownRecipeTitle);
        String title = ownRecipeTitle.getText().toString();
        EditText ownRecipeCategory = findViewById(R.id.ownRecipeCategory);
        String category = ownRecipeCategory.getText().toString();
        EditText ownRecipeDescription = findViewById(R.id.ownRecipeDescription);
        String description = ownRecipeDescription.getText().toString();
        EditText ownRecipeIngredients = findViewById(R.id.ownRecipeIngredients);
        String ingredients = ownRecipeIngredients.getText().toString();
        EditText ownRecipeDirections = findViewById(R.id.ownRecipeDirections);
        String directions = ownRecipeDirections.getText().toString();

        Intent intent = new Intent(this, OwnRecipeActivity.class);
        intent.putExtra("Custom Title", title);
        intent.putExtra("Custom Category", category);
        intent.putExtra("Custom Description", description);
        intent.putExtra("Custom Ingredients", ingredients);
        intent.putExtra("Custom Directions", directions);

        startActivity(intent);
    }
}