package com.example.cocktail.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail.AddCustomActivity;
import com.example.cocktail.CustomActivity;
import com.example.cocktail.R;
import com.example.cocktail.View.AddInfo;
import com.example.cocktail.adapter.RecipeAdpater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CustomFragment extends Fragment {
    private static final String TAG="Custom";
    private RecyclerView recyclerView;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_custom, container, false);
        rootView.findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);

        recyclerView= rootView.findViewById(R.id.customrecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        final ArrayList<AddInfo> CustomList=new ArrayList<>();
        db.collection("customs").orderBy("title", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CustomList.add(new AddInfo(
                                        document.getData().get("title").toString(),
                                        document.getData().get("contents").toString(),
                                        document.getData().get("publisher").toString()));
                                Log.d(TAG, "Error"+document.getData().get("title").toString());
                            }
                            RecyclerView.Adapter mAdapter=new RecipeAdpater(CustomFragment.this, CustomList);
                            recyclerView.setAdapter(mAdapter);
                        } else {
                        }
                    }
                });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.floatingActionButton:
                    addCustomActivity();
                    break;
            }
        }
    };

    private void addCustomActivity() {
        Intent intent=new Intent(getContext(), AddCustomActivity.class);
        startActivity(intent);
    }
}
