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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ListView listView;
    private String [] menu = {"레시피","커스텀 레시피","마이페이지","주조기능사","로그아웃"};

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

        findViewById(R.id.btnrecipe).setOnClickListener(onClickListener);

        listView = (ListView) findViewById (R.id. listview);
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1 ,menu);
        listView.setAdapter (adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (menu[position]){
                    case "레시피":
                        startRecipeActivity();
                        break;
                    case "로그아웃":
                        FirebaseAuth.getInstance().signOut();
                        startLoginActivity();
                        break;
                }
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(drawerView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnrecipe:
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
