package com.example.cocktail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class RecipeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ListView list;
    private String [] menu = {"레시피","커스텀 레시피","마이페이지","주조기능사","로그아웃"};

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


        //맨 위에 툴바 적용
        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //navigation menu
        drawerLayout=(DrawerLayout)findViewById(R.id.main);
        drawerView=(View)findViewById(R.id.drawer);
        list = (ListView) findViewById (R.id.list);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1 ,menu);
        list.setAdapter (adapter1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (menu[position]){
                    case "레시피":
                        startRecipeActivity();
                        break;
                    case "마이페이지":
                        startUserInfoActivity();
                        break;
                    case "로그아웃":
                        FirebaseAuth.getInstance().signOut();
                        startLoginActivity();
                        break;
                }
            }
        });
    }

    //툴바 왼쪽에 아이콘 클릭시 navigation menu 열리도록 만듦
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(drawerView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //로그인 activity로 이동
    private void startLoginActivity() {
        Intent intent=new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    //recipe activity로 이동
    private void startRecipeActivity() {
        Intent intent=new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }

    //recipe activity로 이동
    private void startUserInfoActivity() {
        Intent intent=new Intent(this, UserInfoActivity.class);
        startActivity(intent);
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
