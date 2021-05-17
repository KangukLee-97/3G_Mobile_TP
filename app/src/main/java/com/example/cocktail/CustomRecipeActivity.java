package com.example.cocktail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomRecipeActivity extends AppCompatActivity {
    TextView gettitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customrecipe);

        Intent intent=getIntent();
        String title=intent.getStringExtra("Title");
        gettitle=(TextView)findViewById(R.id.titleget);
        gettitle.setText(title);
    }
}
