package com.example.cocktail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail.Fragment.CustomFragment;
import com.example.cocktail.View.AddInfo;
import com.example.cocktail.adapter.RecipeAdpater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CustomRecipeActivity extends AppCompatActivity {
    TextView gettitle;
    ImageView getimage;
    FirebaseFirestore db;
    String image;
    String title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customrecipe);

        Intent intent=getIntent();
        title=intent.getStringExtra("Title");
    }

    @Override
    public void onResume() {
        super.onResume();
        db = FirebaseFirestore.getInstance();
        db.collection("customs").whereEqualTo("title",title).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                image=document.getData().get("image").toString();
                            }
                        }
                    }
                });

        System.out.println("string:"+image);
        gettitle=(TextView)findViewById(R.id.titleget);
        gettitle.setText(title);
        getimage=(ImageView)findViewById(R.id.imageget);
        getimage.setImageBitmap(StringToBitmap(image));
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
}
