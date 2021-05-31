package com.example.cocktail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FinalQuestionActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private ImageView imageView;

    public String glassName;
    public String glassImageUrl;
    public String cockName;
    public String ImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        Button test = (Button) findViewById(R.id.testBtn);
        Button main = (Button) findViewById(R.id.mainBtn);
        final ImageView shacker = (ImageView) findViewById(R.id.shacker);

        Animation anim = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.rotate_anim
        );
        shacker.startAnimation(anim);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirstQuestionActivity.class);
                startActivity(intent);
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        FirstQuestionActivity firstQues = new FirstQuestionActivity();   // ListView에서 클릭한 glass명을 참조하기 위함.
        glassName = firstQues.getClickedGlass();
        cockName = firstQues.getCocktailName();

        db = FirebaseFirestore.getInstance();
        db.collection("glass")
                .whereEqualTo("Name", glassName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                glassImageUrl = document.getData().get("ImageURL").toString();
                                Log.w("tag4", "Result: " + glassImageUrl);
                            }

                            imageView = (ImageView)findViewById(R.id.testImage);
                            // Google Glide library를 이용해서 image upload
                            Glide.with(getApplicationContext()).load(glassImageUrl).into(imageView);

                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    db.collection("cocktail")
                                            .whereEqualTo("Name", cockName)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            ImageUrl = document.getData().get("ImageURL").toString();
                                                        }
                                                        Glide.with(getApplicationContext()).load(ImageUrl).into(imageView);
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    }
                });
    }
}
