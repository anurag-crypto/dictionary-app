package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.file.Files;

public class login extends AppCompatActivity {
     EditText mEmail,mPassword;
     Button mLoginbtn;
     TextView mCreatebtn;
     ProgressBar progressBar;
     FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail=findViewById(R.id.editText1);
        mPassword = findViewById(R.id.editText2);
        progressBar =findViewById(R.id.progressBar2);
        fAuth=FirebaseAuth.getInstance();
        mLoginbtn =findViewById(R.id.button1);
        mCreatebtn =findViewById(R.id.button2) ;
        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("password is required.");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be grater than 5 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login .this,"Log in successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(login .this,"error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }

                });

            }
        });
        mCreatebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),register1.class));
        }
    }) ;
    }
}