package com.example.cocktail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomRecipeActivity extends AppCompatActivity {
    TextView gettitle;
    ImageView getimage;
    TextView getcontent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customrecipe);

        Intent intent=getIntent();
        String title=intent.getStringExtra("Title");
        String image=intent.getStringExtra("Image");
        String content=intent.getStringExtra("Content");

        gettitle=(TextView)findViewById(R.id.titleget);
        getimage=(ImageView) findViewById(R.id.imageget);
        getcontent=(TextView)findViewById(R.id.contentget);

        gettitle.setText(title);
        getimage.setImageBitmap(StringToBitmap(image));
        getcontent.setText(content);
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
