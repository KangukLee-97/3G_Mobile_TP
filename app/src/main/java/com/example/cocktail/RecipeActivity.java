package com.example.cocktail;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        ListView listView = findViewById(R.id.listView);
        CocktailAdapter adapter = new CocktailAdapter();
        adapter.addItem(new CocktailRecipe("Cocktail name", "tag", R.drawable.design));
        adapter.addItem(new CocktailRecipe("Cocktail name", "tag", R.drawable.design));
        adapter.addItem(new CocktailRecipe("Cocktail name", "tag", R.drawable.design));
        adapter.addItem(new CocktailRecipe("Cocktail name", "tag", R.drawable.design));
        adapter.addItem(new CocktailRecipe("Cocktail name", "tag", R.drawable.design));
        adapter.addItem(new CocktailRecipe("Cocktail name", "tag", R.drawable.design));
        listView.setAdapter(adapter);
        
    }
    
    class CocktailAdapter extends BaseAdapter {
        ArrayList<CocktailRecipe> recipes = new ArrayList<CocktailRecipe>();

        @Override
        public int getCount() {
            return recipes.size();
        }

        public void addItem(CocktailRecipe recipe){
            recipes.add(recipe);
        }

        @Override
        public Object getItem(int position) {
            return recipes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CocktailRecipeView cocktailRecipeView = null;

            if(convertView == null) {
                cocktailRecipeView = new CocktailRecipeView(getApplicationContext());
            } else {
                cocktailRecipeView = (CocktailRecipeView)convertView;
            }
            CocktailRecipe recipe = recipes.get(position);
            cocktailRecipeView.setName(recipe.getName());
            cocktailRecipeView.setTag(recipe.getTag());
            cocktailRecipeView.setImage(recipe.getResId());
            return cocktailRecipeView;
        }
    }
}
