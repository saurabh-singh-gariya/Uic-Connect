package com.example.uicaddressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.uicaddressbook.Helper.SharedPrefManager;
import com.example.uicaddressbook.Model.User;
import com.example.uicnotifier.R;

public class UserProfile extends AppCompatActivity {

    TextView uName,uUid,uSection,uGroup,uEmail;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        uName=findViewById(R.id.user_name);
        uUid=findViewById(R.id.user_uid);
        uSection=findViewById(R.id.user_section);
        uGroup=findViewById(R.id.user_group);
        uEmail=findViewById(R.id.user_email);

        loadStudentDetails();
    }

    private void loadStudentDetails() {
        user = SharedPrefManager.getInstance(this).getUser();

        uName.setText(user.getName());
        uUid.setText(user.getUsername());
        uSection.setText(user.getSection());
        uGroup.setText(user.getGroup());
        uEmail.setText(user.getEmail());
    }

}