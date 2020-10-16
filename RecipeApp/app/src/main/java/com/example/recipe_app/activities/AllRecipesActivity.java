package com.example.recipe_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.recipe_app.R;
import com.example.recipe_app.managers.AllRecipesManager;
import com.example.recipe_app.models.AllRecipesRecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AllRecipesActivity extends AppCompatActivity {

    private AllRecipesRecyclerViewAdapter allRecipesRecyclerViewAdapter;
    final int gridLayoutWidth = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);

        AllRecipesManager allRecipesManager = new AllRecipesManager(AllRecipesActivity.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.allRecipesRecyclerViewId);
        allRecipesRecyclerViewAdapter = new AllRecipesRecyclerViewAdapter(this, allRecipesManager.getAllRecipes());
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridLayoutWidth));
        recyclerView.setAdapter(allRecipesRecyclerViewAdapter);

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
}