package com.example.uicaddressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.uicaddressbook.Model.User;
import com.example.uicnotifier.R;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        getSupportActionBar().hide();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

                boolean isFirstVisit = onBoardingScreen.getBoolean("firstVisit", true);

                if(isFirstVisit){

                    SharedPreferences.Editor editor  = onBoardingScreen.edit();
                    editor.putBoolean("firstVisit", false);
                    editor.commit();

                    Intent intent = new Intent(SplashScreen.this, OnBoarding.class);
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this , MainActivity.class));
                    finish();
                }
            }
        }, 1500);
    }
}