package com.example.uicaddressbook.AddEdit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.uicaddressbook.ContactDetailsAdmin;
import com.example.uicaddressbook.Helper.SharedPrefManager;
import com.example.uicaddressbook.Helper.URLs;
import com.example.uicaddressbook.Helper.VolleySingleton;
import com.example.uicaddressbook.LoginActivity;
import com.example.uicnotifier.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddFaculty extends AppCompatActivity {
    EditText etName, etEcode, etPhone, etEmail, etDesig;
    String ecode, name, phone, designation, email;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEcode = findViewById(R.id.ed_ecode);
        etName = findViewById(R.id.ed_name);
        etPhone = findViewById(R.id.ed_phone);
        etEmail = findViewById(R.id.ed_email);
        etDesig = findViewById(R.id.ed_designation);

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


    public void addFaculty(View view) {
        ecode = etEcode.getText().toString();
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        email = etEmail.getText().toString();
        designation = etDesig.getText().toString();

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_FACULTY,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("message").equalsIgnoreCase("Contact Added!!")) {
                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ContactDetailsAdmin.class);
                            intent.putExtra("contactType", 1);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                //  params.put("ID",id);
                params.put("ecode", ecode);
                params.put("name", name);
                params.put("designation", designation);
                params.put("contact", phone);
                params.put("email", email);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}