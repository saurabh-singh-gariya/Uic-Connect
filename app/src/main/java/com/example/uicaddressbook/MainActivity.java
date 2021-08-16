package com.example.uicaddressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.uicaddressbook.Dashboard.AdminDashboard;
import com.example.uicaddressbook.Dashboard.UserDashboard;
import com.example.uicaddressbook.Helper.SharedPrefManager;
import com.example.uicaddressbook.Model.User;
import com.example.uicnotifier.R;

public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();

        user = SharedPrefManager.getInstance(this).getUser();

        if(SharedPrefManager.getInstance(this).isLoggedIn() && Integer.parseInt(user.getCategory())==0) {
            startActivity(new Intent(MainActivity.this, UserDashboard.class));
        }
        if(SharedPrefManager.getInstance(this).isLoggedIn() && Integer.parseInt(user.getCategory())==1){
            startActivity(new Intent(MainActivity.this, AdminDashboard.class));
        }
        else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        finish();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//            WindowInsetsController windowInsetsController = getWindow().getInsetsController();
//            if(windowInsetsController != null){
//                windowInsetsController.hide(WindowInsets.Type.statusBars());
//            }
//        }
//        else{
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
    }
}