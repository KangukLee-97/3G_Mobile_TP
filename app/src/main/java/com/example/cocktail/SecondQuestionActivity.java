package com.example.cocktail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class SecondQuestionActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private TableLayout tableLayout;
    private TextView cockNameView;
    private Button ingredButton;
    private Spinner spinner;
    private Button techButton;
    private Button exitButton, finalButton;

    public String cockName;
    public String selected_technique;
    public Boolean checkIngredient, checkTechnique;

    private ArrayList<String> received;
    private int receivedSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_second);

        FirstQuestionActivity firstQues = new FirstQuestionActivity();
        IngredientActivity secondQues = new IngredientActivity();
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

        Intent intent = getIntent();
        processIntent(intent);

        // 기술 선택 Spinner
        techButton = (Button)findViewById(R.id.select_technique_btn);
        spinner = (Spinner)findViewById(R.id.tech_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_technique = parent.getItemAtPosition(position).toString();

                techButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        db = FirebaseFirestore.getInstance();
                        db.collection("cocktail")
                                .whereEqualTo("Name", cockName)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()) {
                                            for(QueryDocumentSnapshot document : task.getResult()) {
                                                String tech = document.getData().get("Technique").toString();

                                                if(tech.equalsIgnoreCase(selected_technique)) {
                                                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                                                    checkTechnique = true;
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "오답입니다! 다시 선택해주세요", Toast.LENGTH_SHORT).show();
                                                    checkTechnique = false;
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 종료하기 버튼 or 조주하러 가기 버튼
        exitButton = (Button)findViewById(R.id.exit_button);
        finalButton = (Button)findViewById(R.id.final_button);

        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showExitMessage();
            }
        });

        finalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkIngredient = secondQues.getIngredientResult();

                if(checkIngredient == false && checkTechnique == false)
                    Toast.makeText(getApplicationContext(), "재료와 기술 둘다 수정하세요", Toast.LENGTH_SHORT).show();
                else if(checkIngredient == true && checkTechnique == true) {
                    Toast.makeText(getApplicationContext(), "수고하셨습니다! 이제 조주하러 가세요", Toast.LENGTH_SHORT).show();
                    startFinalQuestionActivity();
                }
                else {
                    if(checkIngredient == false)
                        Toast.makeText(getApplicationContext(), "재료를 수정해주세요", Toast.LENGTH_SHORT).show();
                    else if(checkTechnique == false)
                        Toast.makeText(getApplicationContext(), "기술을 수정해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 재료선택 버튼 클릭시 재료선택 페이지로 이동
    private void startIngredientActivity() {
        Intent intent = new Intent(this, IngredientActivity.class);
        startActivity(intent);
    }

    // 메인 페이지로 이동
    private void startMainActivity() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // 최종 실습 페이지로 이동
    private void startFinalQuestionActivity() {
        Intent intent = new Intent(this, FinalQuestionActivity.class);
        startActivity(intent);
    }

    // 종료하기 버튼 클릭시 종료할 것인지 물어보는 function
    public void showExitMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("종료하시면 처음부터 응시하셔야합니다. 종료하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                // 예를 눌렀을 경우? => Main으로 다시?
                startMainActivity();
                Toast.makeText(getApplicationContext(), "시험이 종료되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 아니오 버튼을 눌렀을 경우? => 그대로 진행
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void processIntent(Intent intent) {
        if(intent != null)
        {
            received = (ArrayList<String>)intent.getSerializableExtra("list");
            // receivedSize = (int)intent.getSerializableExtra("size");

            if (received != null)
            {
                tableLayout = (TableLayout)findViewById(R.id.tableLayout);
                for(int i=0; i<received.size(); i++) {
                    TableRow tableRow = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    tableRow.setLayoutParams(lp);

                    TextView ingredName = new TextView(this);
                    EditText ingredQuan = new EditText(this);
                    ingredName.setText(received.get(i));

                    tableRow.addView(ingredName);
                    tableRow.addView(ingredQuan);
                    tableLayout.addView(tableRow);
                }
            }
        }
    }
}