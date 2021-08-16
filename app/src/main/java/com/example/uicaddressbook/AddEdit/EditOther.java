package com.example.uicaddressbook.AddEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class EditOther extends AppCompatActivity {

    Spinner spinner;
    EditText etName, etEcode, etSpecialName, etContact1, etContact2, etEmail;
    String name, ecode, special_name, category1, contact1, contact2, email, prevCategory;
    int id, spinnerpos = 0;
    String category[] = {"Please select a category", "DSW", "DCPD", "Cu Admin", "Hods", "Coordinator", "Helpline", "Mentor"};
    Button btn;
    Toolbar toolbar;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_other);

        spinner = findViewById(R.id.spinner);
        etName = findViewById(R.id.ed_name);
        etEcode = findViewById(R.id.ed_ecode);
        etSpecialName = findViewById(R.id.ed_special_name);
        etContact1 = findViewById(R.id.ed_contact1);
        etContact2 = findViewById(R.id.ed_contact2);
        etEmail = findViewById(R.id.ed_email);
        btn = findViewById(R.id.btn_update);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        contact = (Contact) getIntent().getSerializableExtra("currentContact");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit");

        setExistingValue();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
            }
        });
    }

    private void updateValues() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        name = etName.getText().toString();
        ecode = etEcode.getText().toString();
        category1 = category[spinnerpos];
        special_name = etSpecialName.getText().toString();
        contact1 = etContact1.getText().toString();
        contact2 = etContact2.getText().toString();
        email = etEmail.getText().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(EditOther.this, "Pls select a category", Toast.LENGTH_SHORT).show();
                } else {
                    spinnerpos = position;
                    Toast.makeText(EditOther.this, category[position] + " selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Pls select a category", Toast.LENGTH_SHORT).show();
            }
        });

        StringRequest request=new StringRequest(Request.Method.POST, URLs.URL_EDIT_OTHER,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(jsonObject.getString("message").equalsIgnoreCase("Contact Added!!")){
                            progressDialog.dismiss();

                            finish();
                            Intent intent = new Intent(this, ContactDetailsAdmin.class);
                            intent.putExtra("contactType", 2);
                            startActivity(intent);
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("id", String.valueOf(id));
                params.put("ecode", ecode);
                params.put("name", name);
                params.put("category", category1);
                params.put("specialName", special_name);
                params.put("contact1", contact1);
                params.put("contact2", contact2);
                params.put("email", email);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void setExistingValue() {
        prevCategory = contact.getCategory();
        etName.setText(contact.getName());
        etEcode.setText(contact.getEcode());
        etSpecialName.setText(contact.getSpecialName());
        etContact1.setText(contact.getPhone());
        etContact2.setText(contact.getPhone2());
        etEmail.setText(contact.getEmail());
        id = contact.getId();

        for(int i= 1; i<category.length; i++){
            if(prevCategory == category[i]){
                spinner.setSelection(i);
            }
        }
    }


}