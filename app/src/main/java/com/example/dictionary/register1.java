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

 public class register1 extends AppCompatActivity {
    EditText  mFullName,mEmail,mPassword,mPhone;
    Button mRegisterbtn;
    TextView mLoginbtn;
     FirebaseAuth fAuth;
     ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        mFullName =findViewById(R.id.FullName) ;
        mEmail =findViewById(R.id.Email);
        mPassword =findViewById(R.id.Password) ;
        mRegisterbtn =findViewById(R.id.registerbtn);
        mLoginbtn =findViewById(R.id.loginbtn);

        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);


        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        mRegisterbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                    mPassword.setError("Password must be greater than 5 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                         if(task.isSuccessful()){
                             Toast.makeText(register1.this,"user created",Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(),MainActivity.class));
                         }
                        else{
                            Toast.makeText(register1.this,"error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                         }

                    }
                });

            }
        });
        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        }) ;



    }
}