package com.example.cocktail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class SecondQuestionActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private ImageView imageView;

    public String glassName;
    public String glassImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        FirstQuestionActivity firstQues = new FirstQuestionActivity();   // ListView에서 클릭한 glass명을 참조하기 위함.
        glassName = firstQues.getClickedGlass();

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
                            Glide.with(getApplicationContext()).load(glassImageUrl).into(imageView);
                        }
                    }
                });
    }
}
