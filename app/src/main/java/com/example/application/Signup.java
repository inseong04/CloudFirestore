package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView signup_username_text = findViewById(R.id.signup_username_text);
        final EditText signup_edittext_user_id = findViewById(R.id.signup_edittext_user_id);
        final EditText signup_edittext_user_password = findViewById(R.id.signup_edittext_user_password);
        Button signup_button = findViewById(R.id.signup_button);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); // firebaseAuth의 인스턴스를 가져온다.
        String username;

        // 이름받아옴
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        signup_username_text.setText(username);
        // 이름받아옴

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = signup_edittext_user_id.getText().toString().trim();
                String pwd = signup_edittext_user_password.getText().toString().trim();


                firebaseAuth.createUserWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
// 이메일주소와 비밀번호를 creatUserWithEmailAndPassword에 전달하여 신규 계정을 생성한다.
                                if (task.isSuccessful()){ // 성공적일경우 로그인화면으로 넘어간다.

                                    Intent intent = new Intent(Signup.this,Login.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else { // 아닐경우 등록 에러가 발생했다는 문구를 띄운다.
                                    Toast.makeText(Signup.this,"등록 에러가 발생했습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
        });

    }

}