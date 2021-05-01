package com.example.cocktail;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        TextView nickinfo=(TextView)findViewById(R.id.nickinfo);
        TextView phoneinfo=(TextView)findViewById(R.id.phoneinfo);

        DocumentReference documentReference= FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener((task)  ->  {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            nickinfo.setText(document.getData().get("nickname").toString());
                            phoneinfo.setText(document.getData().get("phone").toString());
                        } else {
                        }
                    }
                }
            });
        }
    }

