package com.example.uicaddressbook.AddEdit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddOther extends AppCompatActivity {
    Spinner spinner;
    EditText name, Ecode, SpecialName, Category, Contact1, Contact2, Email;
    String t_name, ecode, special_name, category1, contact1, contact2, email;
    int spinnerpos = 0;
    String category[] = {"Please select a category", "DSW", "DCPD", "Cu Admin", "Hods", "Coordinator", "Helpline", "Mentor"};
    int pos = 0;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);
        name = findViewById(R.id.ed_name);
        Ecode = findViewById(R.id.ed_ecode);
        SpecialName = findViewById(R.id.ed_special_name);
        Contact1 = findViewById(R.id.ed_contact1);
        Contact2 = findViewById(R.id.ed_contact2);
        Email = findViewById(R.id.ed_email);
//        getSupportActionBar().hide();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(AddOther.this, "Pls select a category", Toast.LENGTH_SHORT).show();
                } else {
                    spinnerpos = position;
                    Toast.makeText(AddOther.this, category[position] + " selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Pls select a category", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void insData(View view)
    {
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            t_name = name.getText().toString();
            ecode = Ecode.getText().toString();
            category1 = category[spinnerpos];
            special_name = SpecialName.getText().toString();
            contact1 = Contact1.getText().toString();
            contact2 = Contact2.getText().toString();
            email = Email.getText().toString();

            StringRequest request=new StringRequest(Request.Method.POST, URLs.URL_ADD_CONTACT,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            if(jsonObject.getString("message").equalsIgnoreCase("Contact Added!!")){
                                progressDialog.dismiss();

                                Intent intent = new Intent(getApplicationContext(), ContactDetailsAdmin.class);
                                intent.putExtra("contactType", 2);
                                startActivity(intent);
                                finish();
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
                    //  params.put("ID",id);
                    params.put("ecode", ecode);
                    params.put("name", t_name);
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
}
