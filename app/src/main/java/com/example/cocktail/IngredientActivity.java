package com.example.cocktail;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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

    private ListView ingred_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ingredientList = new ArrayList<String>();
        ingred_listview = (ListView)findViewById(R.id.ingredient_listview);


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
                                ingredientList.add(ingredient);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, ingredientList);
                            ingred_listview.setAdapter(adapter);
                        }
                    }
                });


    }
}
