package com.bhaa.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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


public class MainActivity extends AppCompatActivity implements forgetPassword.ResetDialogListener {
    private DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;

    final String tag="finalProject.bhaa";
    Button signup;
    TextView email;
    TextView password;
    Button login;
    Button forgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();



        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
          finish();
            //opening profile activity
           startActivity(new Intent(getApplicationContext(), LessonDetails.class));
        }

      //  Log.i(tag,"ah ha ah");
     //   startService(new Intent(MainActivity.this, PopupService.class));
       // Log.i(tag,"58 85");


        signup=(Button)findViewById(R.id.SignUp);
        email=(TextView)findViewById(R.id.emailLogin);
        password=(TextView)findViewById(R.id.passwordLogin);
        findViewById(R.id.login).setOnClickListener(handleClick);
        forgetPassword=(Button)findViewById(R.id.forgetPassword);
       forgetPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               forgetPassword Dialog = new forgetPassword();
               Dialog.show(getSupportFragmentManager(), "reset dialog");
           }
       });

    }


    private View.OnClickListener handleClick = new View.OnClickListener(){
        public void onClick(View arg0) {
            userLogin();
        }
    };

    private void userLogin(){
        String emailLogin = email.getText().toString().trim();
        String passwordLogin  = password.getText().toString().trim();



        //checking if email and passwords are empty
        if(TextUtils.isEmpty(emailLogin)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(passwordLogin)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


        //logging in the user

        firebaseAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                         startActivity(new Intent(getApplicationContext(), LessonDetails.class));

                        }
                    }
                });
    }


///Cookbook - 5. Attribute in View Layout for OnClick Events
    public void go_To_Signup(View view) {
        Intent intent=new Intent(this,SignupActivity.class);
        startActivityForResult(intent,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1) {
            if (resultCode == RESULT_OK) {

                    email.setText(data.getStringExtra("email"));
                    password.setText(data.getStringExtra("password"));

            }
        }


    }


    @Override
    public void applyUpdate(String Email) {
        Log.i(tag,"yyyyyyy");
        firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Check your email .. ",Toast.LENGTH_SHORT);
                }else {
                    String err=task.getException().getMessage();
                    Toast.makeText(MainActivity.this,"Error Occured: "+err,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
