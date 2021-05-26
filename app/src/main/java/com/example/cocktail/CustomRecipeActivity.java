package com.example.cocktail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail.Fragment.CustomFragment;
import com.example.cocktail.View.AddInfo;
import com.example.cocktail.adapter.RecipeAdpater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CustomRecipeActivity extends AppCompatActivity {
    private TextView gettitle;
    private ImageView getimage;
    FirebaseFirestore data;
    private String image;
    private String title;
    private static final String TAG="CustomRecipe";
    private DrawerLayout drawerLayout;
    private View drawerView;
    private ListView listView;
    private String [] menu = {"레시피","커스텀 레시피","마이페이지","주조기능사","로그아웃"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customrecipe);

        //맨 위에 툴바 적용
        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //navigation menu
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerRecipe);
        drawerView=(View)findViewById(R.id.drawer);
        listView = (ListView) findViewById (R.id. list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1 ,menu);
        listView.setAdapter (adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (menu[position]){
                    case "레시피":
                        startRecipeActivity();
                        break;
                    case "마이페이지":
                        startUserInfoActivity();
                        break;
                    case "커스텀 레시피":
                        startCustomActivity();
                        break;
                    case "주조기능사":
                        startTestActivity();
                        break;
                    case "로그아웃":
                        FirebaseAuth.getInstance().signOut();
                        startLoginActivity();
                        break;
                }
            }
        });

        Intent intent=getIntent();
        title=intent.getStringExtra("Title");

        data = FirebaseFirestore.getInstance();
        data.collection("customs").whereEqualTo("title",title).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                image=document.getData().get("image").toString();
                            }
                            getimage=(ImageView)findViewById(R.id.imageget);
                            getimage.setImageBitmap(StringToBitmap(image));
                        }else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        gettitle=(TextView)findViewById(R.id.titleget);
        gettitle.setText(title);
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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

    //userInfo activity로 이동
    private void startUserInfoActivity() {
        Intent intent=new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    //custom recipe activity로 이동
    private void startCustomActivity() {
        Intent intent=new Intent(this, CustomActivity.class);
        startActivity(intent);
    }

    // test activity로 이동
    private void startTestActivity() {
        Intent intent = new Intent(this, testActivity.class);
        startActivity(intent);
    }
}
