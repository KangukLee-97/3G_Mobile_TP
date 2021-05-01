package com.example.cocktail;

import android.content.Intent;
import android.os.Bundle;
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

public class loginActivity extends AppCompatActivity {

    private static final String TAG="Sign";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //파이어베이스 사용자 (Auth) 초기화
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnLogin).setOnClickListener(onClickListener);
        findViewById(R.id.btnSign).setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnLogin:
                    FirebaseAuth.getInstance().signOut();
                    Login();
                    break;
                case R.id.btnSign:
                    startSignActivity();
                    break;
            }
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    //로그인 창에서 뒤로가기를 눌렀을 시 앱 강제 종료
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    //로그인 절차
    private void Login(){
        String email=((EditText)findViewById(R.id.ID)).getText().toString();
        String password=((EditText)findViewById(R.id.Password)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startToast("로그인에 성공하였습니다");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startMainActivity();
                        } else {
                            if(task.getException()!=null)
                                startToast("로그인에 실패하였습니다");
                        }
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }
    private void startSignActivity(){
        Intent intent=new Intent(this, signupActivity.class);
        startActivity(intent);
    }

    private void startMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        //Main activity에서 뒤로가기를 눌렀을 시 앱 이 종료되도록 여태까지 stack에 쌓였던 activity를 없앰
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}