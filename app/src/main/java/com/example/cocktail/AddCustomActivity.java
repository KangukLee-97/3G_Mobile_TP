package com.example.cocktail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail.View.AddInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddCustomActivity extends AppCompatActivity {
    private static final String TAG = "AddCustomActivity";
    private FirebaseUser user;
    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    private Bitmap img;
    String simage = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom);

        imageView = findViewById(R.id.image1);

        findViewById(R.id.addCustombtn).setOnClickListener(onClickListener);
        findViewById(R.id.image).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addCustombtn:
                    AddCustom();
                    break;
                case R.id.image:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE);

                    break;
            }
        }
    };

    private void AddCustom(){
        String name=((EditText)findViewById(R.id.customName)).getText().toString();
        String contents=((EditText)findViewById(R.id.customContent)).getText().toString();
        String taste=((EditText)findViewById(R.id.customTaste)).getText().toString();
        String alcohol=((EditText)findViewById(R.id.customAlcohol)).getText().toString();
        String base=((EditText)findViewById(R.id.customBase)).getText().toString();
        String tech=((EditText)findViewById(R.id.customTech)).getText().toString();
        String glass=((EditText)findViewById(R.id.customGlass)).getText().toString();
        String color=((EditText)findViewById(R.id.customColor)).getText().toString();
        String link=((EditText)findViewById(R.id.customVideoLink)).getText().toString();
        int click = 0;

        if(simage == "ok") {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] reviewImage = stream.toByteArray();
            simage = Base64.encodeToString(reviewImage, Base64.DEFAULT);
//            simage = byteArrayToBinaryString(reviewImage);
        }

        if(name.length() > 0 && contents.length() > 0)
        {
            user = FirebaseAuth.getInstance().getCurrentUser();
            AddInfo addInfo = new AddInfo(name, contents, simage, taste, alcohol, base, tech, glass, color, link, click, user.getUid());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();

                    simage = "ok";
                    imageView.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < b.length; ++i){
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for(int bit=0; bit < 8; bit++) {
            if(((n>>bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    private void startToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }
}
