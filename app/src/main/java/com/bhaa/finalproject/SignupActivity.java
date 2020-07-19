package com.bhaa.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;

    Button signupToDatabase;
    TextView firstName;
    TextView lastName;
    TextView userName;
    TextView phone;
    TextView email;
    TextView password;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign up");

        mDatabase=FirebaseDatabase.getInstance().getReference("users");

        firebaseAuth =FirebaseAuth.getInstance();

        intent =new Intent(this,MainActivity.class);

        signupToDatabase = (Button) findViewById(R.id.signuptodatabase);
        email = (TextView) findViewById(R.id.Email);
        password = (TextView) findViewById(R.id.password);
        signupToDatabase.setOnClickListener(this);

    }

    private void registerUser(){
        //getting email and password from edit texts
        String emailString = email.getText().toString().trim();
        String passwordString  = password.getText().toString().trim();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(emailString)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(passwordString)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

  /*      String key = mDatabase.push().getKey();

      user newUser=new user(firstName.getText().toString(),lastName.getText().toString(),userName.getText().toString()
                                ,phone.getText().toString(),email.getText().toString(),password.getText().toString());
        mDatabase.child(key).setValue(newUser);*/

        //creating a new user

        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(SignupActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(SignupActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

///Cookbook 4
    @Override
    public void onClick(View v) {
        registerUser();

        intent.putExtra("email",email.getText().toString().trim());
        intent.putExtra("password",password.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();


    }
}
