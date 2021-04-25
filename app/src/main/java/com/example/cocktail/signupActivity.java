package com.example.cocktail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class signupActivity extends AppCompatActivity {

    private static final String TAG="Sign";
    private FirebaseAuth mAuth;
    Button Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Signup=(Button)findViewById(R.id.btnSignup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    private void signUp(){
        String email=((EditText)findViewById(R.id.email)).getText().toString();
        String password=((EditText)findViewById(R.id.password)).getText().toString();
        String passwordCheck=((EditText)findViewById(R.id.checkpassword)).getText().toString();

        if(password.equals(passwordCheck))
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startToast("회원가입에 성공하였습니다");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Profile();
                                startLoginActivity();
                            } else {
                                if(task.getException()!=null)
                                startToast("회원가입에 실패하였습니다");
                            }
                        }
                    });
        }else{
            startToast("비밀번호가 일치하지 않습니다");
        }
    }

    private void Profile(){
        String nickname=((EditText)findViewById(R.id.nickname)).getText().toString();
        String phone=((EditText)findViewById(R.id.phone)).getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        UserInfo info = new UserInfo(nickname,phone);
        db.collection("users").document(user.getUid()).set(info);
    }

    private void startToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }
    private void startLoginActivity(){
        Intent intent=new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}