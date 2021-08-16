package com.example.uicaddressbook.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.ImsMmTelManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uicaddressbook.ContactsDetails;
import com.example.uicaddressbook.Helper.SharedPrefManager;
import com.example.uicaddressbook.LoginActivity;
import com.example.uicnotifier.R;
import com.example.uicaddressbook.UserProfile;

public class UserDashboard extends AppCompatActivity {
    Button logOutBtn;
    CardView cuAdmin, helpline, professors, favorite, social, transport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
//        getSupportActionBar().hide();
//
//        LottieAnimationView animationView1 = findViewById(R.id.image1);
//        animationView1.setAnimation(R.raw.teacher_anim);
//
//        LottieAnimationView animationView2 = findViewById(R.id.image2);
//        animationView2.setAnimation(R.raw.teacher_anim);
//
//        LottieAnimationView animationView3 = findViewById(R.id.image3);
//        animationView3.setAnimation(R.raw.teacher_anim);
//
//        LottieAnimationView animationView4 = findViewById(R.id.image4);
//        animationView4.setAnimation(R.raw.teacher_anim);

//        cuAdmin = findViewById(R.id.cu_admin_card);
//        cuAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ContactsDetails.class));
//            }
//        });
//
//        helpline = findViewById(R.id.cu_helpline_card);
//        helpline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ContactsDetails.class));
//            }
//        });
//
//        professors = findViewById(R.id.cu_professor_card);
//        professors.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ContactsDetails.class));
//            }
//        });
//
//        favorite = findViewById(R.id.cu_fav_card);
//        favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ContactsDetails.class));
//            }
//        });
//
//        social = findViewById(R.id.cu_social_card);
//        cuAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ContactsDetails.class));
//            }
//        });
//
//        transport = findViewById(R.id.cu_transport_card);
//        transport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ContactsDetails.class));
//            }
//        });

        logOutBtn = findViewById(R.id.logout_btn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    public void getCuAdmins(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactsDetails.class);
        intent.putExtra("contactType", 1);
        startActivity(intent);
    }

    public void getCuHelpline(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactsDetails.class);
        intent.putExtra("contactType", 2);
        startActivity(intent);
    }

    public void getCuProfessor(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactsDetails.class);
        intent.putExtra("contactType", 3);
        startActivity(intent);
    }
//    public void getFavorite(View view) {
//        Intent intent = new Intent(getApplicationContext(), ContactsDetails.class);
//        intent.putExtra("contactType", 4);
//        startActivity(intent);
//    }

    public void getUserProfile(View view) {
        startActivity(new Intent(this, UserProfile.class));
    }

    public void getUserTimetable(View view) {
        Toast.makeText(getApplicationContext(), "Time Table", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ContactsDetails.class);
        intent.putExtra("contactType", 4);
        startActivity(intent);
    }

    public void getCuReach(View view) {
    }
//    public void getCuOthers(View view) {
//        Intent intent = new Intent(getApplicationContext(), ContactsDetails.class);
//        intent.putExtra("contactType", 6);
//        startActivity(intent);
//    }
}