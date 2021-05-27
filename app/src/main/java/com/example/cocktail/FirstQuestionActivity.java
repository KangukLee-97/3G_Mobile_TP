package com.example.cocktail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class FirstQuestionActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private Button exitButton;
    private Button submitButton;
    private TextView cocktailName;
    private ListView glassList;
    private ArrayList<String> list;

    private String clickedGlass;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        exitButton = (Button)findViewById(R.id.question1_exit_btn);
        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showExitMessage();
            }
        });

        cocktailName = (TextView)findViewById(R.id.question1_title);

        ArrayList<String> cockNameArr = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        db.collection("cocktail")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String name = document.getData().get("Name").toString();
                                cockNameArr.add(name);   // 리스트에 칵테일 이름들 추가하기
                            }
                        } else {
                            Log.w("tag", "Error getting documents", task.getException());
                        }

                        int length = cockNameArr.size();
                        int randomIdx = (int)(Math.random() * (length+1));
                        cocktailName.setText(cockNameArr.get(randomIdx).toString());
                    }
                });

        glassList = (ListView)findViewById(R.id.glass_listview);
        list = new ArrayList<String>();

        db.collection("glass")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String name = document.getData().get("Name").toString();
                                list.add(name);   // 리스트에 칵테일 이름들 추가하기
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                            glassList.setAdapter(adapter);

                            // 글래스 선택후 제출하기 클릭
                            submitButton = (Button)findViewById(R.id.submit);

                            glassList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                                    clickedGlass = adapterView.getItemAtPosition(position).toString();
                                    // Log.w("tag2", "name: " + clickedGlass);
                                    submitButton.setOnClickListener(new View.OnClickListener(){   // 글래스 선택 후 제출하기 버튼
                                        @Override
                                        public void onClick(View v){
                                            db.collection("cocktail")
                                                    .whereEqualTo("Name", cocktailName.getText())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                for(QueryDocumentSnapshot document : task.getResult()){
                                                                    if(clickedGlass.equalsIgnoreCase(document.getData().get("Glass").toString())){   // Glass를 맞췄을 경우
                                                                        // Toast.makeText(getApplicationContext(), "정답입니다", Toast.LENGTH_SHORT).show();
                                                                        startTpoActivity();
                                                                    }
                                                                    else
                                                                        Toast.makeText(getApplicationContext(), "오답입니다! Glass를 다시 선택해주세요.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });
                        } else {
                            Log.w("tag", "Error getting documents", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        showExitMessage();
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

    // 메인 페이지로 이동
    private void startMainActivity() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // tpo 실습 페이지 이동
    private void startTpoActivity() {
        Intent intent = new Intent(this, tpoActivity.class);
        startActivity(intent);
    }
}
