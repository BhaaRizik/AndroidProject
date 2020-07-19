package com.bhaa.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class settingAvticity extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  setContentView(R.layout.activity_setting_avticity);
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new settingPreferences())
                .commit();

    }
}
