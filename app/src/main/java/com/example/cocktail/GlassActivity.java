package com.example.cocktail;

import android.os.Bundle;
import android.util.Log;
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

import static android.R.layout.simple_list_item_1;

public class GlassActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ListView glassList;

    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glass);

        // Firestore에서 Glass Name들 가져오기
        glassList = (ListView)findViewById(R.id.glass_listview);
        list = new ArrayList<String>();

        db = FirebaseFirestore.getInstance();
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
                        } else {
                            Log.w("tag", "Error getting documents", task.getException());
                        }
                    }
                });
    }
}
