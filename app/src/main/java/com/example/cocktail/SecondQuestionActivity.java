package com.example.cocktail;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SecondQuestionActivity extends AppCompatActivity {

    public String glassName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpo);

        FirstQuestionActivity firstQues = new FirstQuestionActivity();   // ListView에서 클릭한 glass명을 참조하기 위함.
        glassName = firstQues.getClickedGlass();
    }
}
