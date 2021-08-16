package com.example.uicaddressbook.Dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import com.example.uicaddressbook.AddEdit.AddFaculty;
import com.example.uicaddressbook.AddEdit.AddOther;
import com.example.uicaddressbook.ContactDetailsAdmin;
import com.example.uicaddressbook.ContactsDetails;
import com.example.uicaddressbook.Helper.SharedPrefManager;
import com.example.uicaddressbook.LoginActivity;
import com.example.uicnotifier.R;

public class AdminDashboard extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.admin);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout_admin)
        {
            adminLogout();
        }
        if(item.getItemId()==R.id.add_prof){
            startActivity(new Intent(this, AddFaculty.class));
        }
        if(item.getItemId()==R.id.add_others){
            startActivity(new Intent(this, AddOther.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void adminLogout() {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void getProfessorAdmin(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactDetailsAdmin.class);
        intent.putExtra("contactType", 1);
        startActivity(intent);
    }

    public void getOthersAdmin(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactDetailsAdmin.class);
        intent.putExtra("contactType", 2);
        startActivity(intent);
    }
}