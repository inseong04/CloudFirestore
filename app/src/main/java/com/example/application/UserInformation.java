package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.tags.Tag;

public class UserInformation extends AppCompatActivity {

    private static final String TAG = "UserInformation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        final EditText information_username = findViewById(R.id.information_username);
        Button information_btn = findViewById(R.id.information_btn);
        final Spinner information_grade_spinner = findViewById(R.id.information_grade_spinner);
        TextView information_error_text = findViewById(R.id.information_error_text);
        final FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        information_btn.setOnClickListener(new View.OnClickListener() { // 버튼누르면 팅기고 Cloud firestore에 저장되지않음.
            @Override
            public void onClick(View view) {
                information_grade_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int user_grade_value = position;
                        Log.e(TAG,"before check about username");

                        String username = information_username.getText().toString().trim();
                        Log.e(TAG,"input username to String");
                        if (username != null){
                            String usergrade = "고등학교 " + (user_grade_value)+ "학년";
                            String user_uid = user.getUid();
                            Log.e(TAG,"id :" + user_uid + "grade :"+usergrade);
                            Map<String,Object> user_information = new HashMap<>();
                            user_information.put("Name",username);
                            user_information.put("Grade",usergrade);
                            firestoreDB.collection("users").document(user_uid)
                                    .set(user_information)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"Successfully Document written.");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG,"Error ouccurred! : ",e);
                                        }
                                    });

                        }
                        else{

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });


    }
}