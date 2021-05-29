package com.example.cocktail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondQuestionActivity extends AppCompatActivity {

    private TextView cockNameView;
    private Button ingredButton;

    public String cockName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_second);

        FirstQuestionActivity firstQues = new FirstQuestionActivity();
        cockName = firstQues.getCocktailName();   // 칵테일 이름 받아오기

        cockNameView = (TextView)findViewById(R.id.cocktail_name);
        cockNameView.setText(cockName);

        // 재료선택 버튼 클릭
        ingredButton = (Button)findViewById(R.id.select_ingredient_btn);
        ingredButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startIngredientActivity();
            }
        });
    }

    // 재료선택 버튼 클릭시 재료선택 페이지로 이동
    private void startIngredientActivity() {
        Intent intent = new Intent(this, IngredientActivity.class);
        startActivity(intent);
    }
}
