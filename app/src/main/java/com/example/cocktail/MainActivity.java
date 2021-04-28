package com.example.cocktail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View drawerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            startLoginActivity();
        }
        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout=(DrawerLayout)findViewById(R.id.main);
        drawerView=(View)findViewById(R.id.drawer);

        findViewById(R.id.btnLogout).setOnClickListener(onClickListener);
        findViewById(R.id.btnrecipe).setOnClickListener(onClickListener);
        findViewById(R.id.btnrecipe2).setOnClickListener(onClickListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                drawerLayout.openDrawer(drawerView);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnLogout:
                    FirebaseAuth.getInstance().signOut();
                    startLoginActivity();
                    break;
                case R.id.btnrecipe:
                    startRecipeActivity();
                    break;
                case R.id.btnrecipe2:
                    startRecipeActivity();
                    break;
            }
        }
    };

    private void startLoginActivity() {
        Intent intent=new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    private void startRecipeActivity() {
        Intent intent=new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }
}
