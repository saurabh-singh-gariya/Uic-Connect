package com.example.uicaddressbook.AddEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.uicaddressbook.ContactDetailsAdmin;
import com.example.uicaddressbook.Helper.URLs;
import com.example.uicaddressbook.Helper.VolleySingleton;
import com.example.uicaddressbook.Model.Contact;
import com.example.uicnotifier.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditFaculty extends AppCompatActivity {
    EditText etEcode, etName, etPhone, etEmail;
    Button updateBtn;
    Toolbar toolbar;
    Contact contact;
    String ecode, name, phone, email;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_faculty);

        contact = (Contact) getIntent().getSerializableExtra("currentContact");
        id = contact.getId();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit");

        etEcode = findViewById(R.id.ed_ecode);
        etName = findViewById(R.id.ed_name);
        etPhone = findViewById(R.id.ed_phone);
        etEmail = findViewById(R.id.ed_email);
        updateBtn = findViewById(R.id.btn_update);

        setExistingData();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeContact();
            }
        });
    }

    private void changeContact() {

        ecode = etEcode.getText().toString();
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        email = etEmail.getText().toString();

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Updating...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_EDIT_FACULTY,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        if(jsonObject.getString("message").equalsIgnoreCase("Contact Added!!")) {
                            progressDialog.dismiss();

                            Intent intent = new Intent(this, ContactDetailsAdmin.class);
                            intent.putExtra("contactType", 1);
                            startActivity(intent);
                            finish();
                        }
                        else {

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
                params.put("id", String.valueOf(id));
                params.put("ecode", ecode);
                params.put("name", name);
                params.put("contact", phone);
                params.put("email", email);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setExistingData() {
        etEcode.setText(contact.getEcode());
        etName.setText(contact.getName());
        etPhone.setText(contact.getPhone());
        etEmail.setText(contact.getEmail());
    }
}