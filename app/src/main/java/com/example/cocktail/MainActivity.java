package com.example.cocktail;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            startLoginActivity();
        }

        findViewById(R.id.btnLogout).setOnClickListener(onClickListener);
        
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
    
  
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnLogout:
                    FirebaseAuth.getInstance().signOut();
                    startLoginActivity();
                    break;
            }
        }
    };

    private void startLoginActivity() {
        Intent intent=new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}
