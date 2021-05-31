package com.example.cocktail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class IngredientActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ArrayList<String> ingredientList;
    public ArrayList<String> selectedIngredList;
    private ArrayList<String> cock_ingredList;
    private String[] ingredStr;
    private ArrayList<String> ingredName;
    private ArrayList<String> ingredQuantity;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;
    private String totalIngredStr;
    public String cockName;

    private ListView ingred_listview;
    private Button ingred_button;
    private EditText ingredSearch;

    public static boolean checkIngredient = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        FirstQuestionActivity firstQues = new FirstQuestionActivity();
        cockName = firstQues.getCocktailName();   // 칵테일 이름 받아오기

        ingredientList = new ArrayList<String>();
        selectedIngredList = new ArrayList<String>();
        cock_ingredList = new ArrayList<String>();
        ingredName = new ArrayList<String>();
        ingredQuantity = new ArrayList<String>();

        ingred_listview = (ListView)findViewById(R.id.ingredient_listview);
        ingred_button = (Button)findViewById(R.id.ingredient_submit_btn);
        ingredSearch = (EditText)findViewById(R.id.ingredient_search);

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

                            Collections.sort(ingredientList);

                            // ListView에 전체 재료들 Upload
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, ingredientList);
                            ingred_listview.setAdapter(adapter);

                            ingredSearch.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void afterTextChanged(Editable edit) {
                                    String filterText = edit.toString();

                                    if (filterText.length() > 0)
                                        ingred_listview.setFilterText(filterText);
                                    else
                                        ingred_listview.clearTextFilter();
                                }

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            });

                            // 선택하기 버튼
                            ingred_button.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    SparseBooleanArray checkedItems = ingred_listview.getCheckedItemPositions();

                                    for(int i=0; i<ingredientList.size(); i++) {
                                        if(checkedItems.get(i) == true)
                                            selectedIngredList.add(ingredientList.get(i));   // 선택된 재료들 list
                                    }

                                    // 선택된 재료들이 해당 칵테일의 재료들이 맞는지 확인하기
                                    db.collection("cocktail")
                                            .whereEqualTo("Name", cockName)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        for(QueryDocumentSnapshot document : task.getResult()) {
                                                            String ingred1 = document.getData().get("Ingredients").toString();
                                                            String ingred2 = document.getData().get("Ingredients2").toString();
                                                            String ingred3 = document.getData().get("Ingredients3").toString();
                                                            String ingred4 = document.getData().get("Ingredients4").toString();
                                                            String ingred5 = document.getData().get("Ingredients5").toString();
                                                            String ingred6 = document.getData().get("Ingredients6").toString();
                                                            String ingred7 = document.getData().get("Ingredients7").toString();

                                                            totalIngredStr = ingred1 + "/" + ingred2 + "/" + ingred3 + "/"
                                                                    + ingred4 + "/" + ingred5 + "/" + ingred6 + "/" + ingred7 + "/";
                                                        }

                                                        // 슬래쉬를 기준으로 List로 나누기
                                                        ingredStr = totalIngredStr.split("/");

                                                        // 재료 배열과 수량 배열로 나누기
                                                        for(int i=0; i<ingredStr.length; i++) {
                                                            String[] splited = ingredStr[i].split(",");
                                                            ingredName.add(splited[0]);
                                                            ingredQuantity.add(splited[1]);
                                                        }

                                                        // 칵테일에 해당하는 재료 갯수 count
                                                        // int ingredCount = getIngredCount(ingredStr);

                                                        // 선택한 재료와 칵테일에 해당하는 재료가 맞는지 check
                                                        // 만약 맞다면? -> 다음 화면으로 넘어가기
                                                        if(isIngredSame(selectedIngredList, ingredName)) {
                                                            // Log.w("selected", "Result: " + selectedIngredList);
                                                            // Log.w("selected2", "Result2: " + selectedIngredList.size());
                                                            Toast.makeText(getApplicationContext(), "정답입니다! 각 재료의 수량을 입력해주세요", Toast.LENGTH_LONG).show();
                                                            checkIngredient = true;
                                                            Intent intent = new Intent(getApplicationContext(), SecondQuestionActivity.class);
                                                            intent.putExtra("list", selectedIngredList);
                                                            intent.putExtra("size", selectedIngredList.size());
                                                            startActivity(intent);
                                                        }
                                                        else {   // 틀렸다면?
                                                            Toast.makeText(getApplicationContext(), "오답입니다! 다시 재료를 선택해주세요", Toast.LENGTH_LONG).show();
                                                            checkIngredient = false;
                                                        }
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    }
                });
    }

    // 선택한 재료와 칵테일에 해당하는 재료가 같은지 확인
    private boolean isIngredSame(ArrayList<String> selected, ArrayList<String> ingredName) {
        return selected.containsAll(ingredName);
    }

    // 재료선택 결과 Return
    public static boolean getIngredientResult() {
        return checkIngredient;
    }
}