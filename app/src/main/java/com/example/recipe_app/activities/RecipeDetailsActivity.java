package com.example.recipe_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.recipe_app.R;
import com.example.recipe_app.managers.FavoritesManager;
import com.example.recipe_app.models.Recipe;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RecipeDetailsActivity extends AppCompatActivity {

    private TextView recipeName, recipeDescription, recipeCategory, recipeIngredients, recipeDirections;
    private ImageView recipeImage;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);

        //Init FB
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);

        recipeName = (TextView) findViewById(R.id.txtTitle);
        recipeDescription = (TextView) findViewById(R.id.txtDescription);
        recipeCategory = (TextView) findViewById(R.id.txtCategory);
        recipeImage = (ImageView) findViewById(R.id.bookThumbnail);
        recipeIngredients = (TextView) findViewById(R.id.txtIngredients);
        recipeDirections = (TextView) findViewById(R.id.txtDirections);

        //***********Receive data*************
        Intent intent = getIntent();
        final String id = intent.getExtras().getString("Title");
        final String title = intent.getExtras().getString("Title");
        final String category = intent.getExtras().getString("Category");
        final String description = intent.getExtras().getString("Description");
        final String ingredients = intent.getExtras().getString("Ingredients");
        final String directions = intent.getExtras().getString("Directions");
        final int image = intent.getExtras().getInt("Thumbnail");

        final ToggleButton favBtn = findViewById(R.id.favBtn);
        final FavoritesManager favoritesManager = new FavoritesManager(RecipeDetailsActivity.this);
        final Recipe foodItem = new Recipe(id, title, category, description, ingredients, directions, image);
        favBtn.setChecked(favoritesManager.isFavorited(foodItem.getuId()));

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favBtn.isChecked()) {
                    Toast.makeText(RecipeDetailsActivity.this, "Recipe favorited", Toast.LENGTH_SHORT).show();
                    favoritesManager.saveFavorites(foodItem.getuId());
                    favBtn.setChecked(true);
                } else {
                    Toast.makeText(RecipeDetailsActivity.this, "Recipe unfavorited", Toast.LENGTH_SHORT).show();
                    favoritesManager.removeFavorites(foodItem.getuId());
                    favBtn.setChecked(false);
                }
            }
        });

        Bitmap fbshare = BitmapFactory.decodeResource(getResources(), R.drawable.recipe_app_main);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(fbshare)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareButton.setShareContent(content);

        //*********Setting values**********
        recipeName.setText(title);
        recipeDescription.setText(description);
        recipeCategory.setText(category);
        recipeImage.setImageResource(image);
        recipeIngredients.setText(ingredients);
        recipeDirections.setText(directions);

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
        
//        printKeyHash();
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.recipeapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shareRecipe) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Link to Recipe App"; // link to recipe
            String shareSubject = "Link to Recipe App";

            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

            startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        }
        return super.onOptionsItemSelected(item);
    }
}