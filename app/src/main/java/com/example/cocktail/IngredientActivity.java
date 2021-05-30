package com.example.cocktail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ArrayList<String> ingredientList;
    private ArrayList<String> selectedIngredList;
    private ArrayAdapter<String> adapter;

    private ListView ingred_listview;
    private Button ingred_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ingredientList = new ArrayList<String>();
        selectedIngredList = new ArrayList<String>();
        ingred_listview = (ListView)findViewById(R.id.ingredient_listview);
        ingred_button = (Button)findViewById(R.id.ingredient_submit_btn);

        // Firestore에서 ingredient 참조 후 가져오기
        db = FirebaseFirestore.getInstance();
        db.collection("ingredient")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                String ingredient = document.getData().get("Name").toString();
                                ingredientList.add(ingredient);   // 전체 재료 List에 추가
                            }

                            // ListView에 전체 재료들 Upload
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, ingredientList);
                            ingred_listview.setAdapter(adapter);

                            // 선택하기 버튼
                            ingred_button.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    SparseBooleanArray checkedItems = ingred_listview.getCheckedItemPositions();

                                    for(int i=0; i<ingredientList.size(); i++) {
                                        if(checkedItems.get(i) == true)
                                            selectedIngredList.add(ingredientList.get(i));   // 선택된 재료들 list
                                    }
                                }
                            });
                        }
                    }
                });
    }
}
