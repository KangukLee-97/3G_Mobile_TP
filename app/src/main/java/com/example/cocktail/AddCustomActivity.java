package com.example.cocktail;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCustomActivity extends AppCompatActivity {
    private static final String TAG = "AddCustomActivity";
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom);

        findViewById(R.id.addCustombtn).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addCustombtn:
                    signUp();
                    break;
            }
        }
    };

    private void signUp(){
        String name=((EditText)findViewById(R.id.customName)).getText().toString();
        String contents=((EditText)findViewById(R.id.customContent)).getText().toString();

        if(name.length() > 0 && contents.length() > 0)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AddInfo addInfo = new AddInfo(name, contents, user.getUid());
            uploader(addInfo);

        }else{
            startToast("내용을 입력해주세요.");
        }
    }

    private void uploader(AddInfo addInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("customs").add(addInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        startToast("등록 성공하였습니다.");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("등록 실패햐였습니다.");
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }
}
