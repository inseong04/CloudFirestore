package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText login_id = findViewById(R.id.login_id);
        final EditText login_password = findViewById(R.id.login_password);
        Button login_button = findViewById(R.id.login_button);
        final TextView login_error_text = findViewById(R.id.login_error_text);
        final Button login_signup = findViewById(R.id.login_signup);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_id.getText().toString().trim();
                String pwd = login_password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // 이메일주소와 비밀번호를 signInWithEmailAndPassword에서 가져온다.
                                if (task.isSuccessful()) { // 이메일주소와 비밀번호가 맞다면 MainActivity로 넘어간다.
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    login_error_text.setVisibility(View.VISIBLE);
                                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
                                    login_error_text.startAnimation(shake);
                                }
                            }
                        });
            }
        });
        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });
    }
}