package com.example.uicaddressbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.uicaddressbook.Adapter.AdminOtherContacts;
import com.example.uicaddressbook.Adapter.AdminProfContacts;
import com.example.uicaddressbook.AddEdit.AddFaculty;
import com.example.uicaddressbook.AddEdit.AddOther;
import com.example.uicaddressbook.Dashboard.AdminDashboard;
import com.example.uicaddressbook.Helper.SharedPrefManager;
import com.example.uicaddressbook.Helper.URLs;
import com.example.uicaddressbook.Helper.VolleySingleton;
import com.example.uicaddressbook.Model.Contact;
import com.example.uicnotifier.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailsAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Contact> contactList;
    AdminOtherContacts adminOtherContacts;
    AdminProfContacts adminProfContacts;
    SearchView searchContact;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details_admin);
        Intent intentx2 = getIntent();
        int option = intentx2.getIntExtra("contactType", 1);
        contactList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchContact = findViewById(R.id.search_contact);

        searchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContact.setIconified(false);
            }
        });

        recyclerView = findViewById(R.id.contact_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(option ==1){
            getSupportActionBar().setTitle(R.string.prof);
            loadProfAdmin();
        }

        if(option==2){
            getSupportActionBar().setTitle(R.string.other);
            loadCuOthers();
        }

        searchContact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(option == 2){
                    adminOtherContacts.getFilter().filter(newText);
                }
                else {
                    adminProfContacts.getFilter().filter(newText);
                }
                return false;
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

    private void loadProfAdmin() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_FACULTY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray jsonArray = jsonObject.getJSONArray("contact");
                            contactList.clear();
                            if(jsonObject.getInt("success")==1){
//                                Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_LONG).show();
                                for(int i=0; i< jsonArray.length(); i++){
                                    JSONObject contactJson = jsonArray.getJSONObject(i);
                                    Contact contact = new Contact(contactJson.getInt("id"),
                                            contactJson.getString("ecode"),
                                            contactJson.getString("name"),
                                            contactJson.getString("phone"),
                                            contactJson.getString("email"),
                                            contactJson.getString("specialname"));
                                    contactList.add(contact);
                                }

                                adminProfContacts = new AdminProfContacts(ContactDetailsAdmin.this , contactList);
                                recyclerView.setAdapter(adminProfContacts);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadCuOthers() {

        //Toast.makeText(getApplicationContext(), "Cu admin", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_ALL_CONTACT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray jsonArray = jsonObject.getJSONArray("contact");
                            contactList.clear();
                            if (jsonObject.getInt("success") == 1) {
//                                Toast.makeText(getApplicationContext(), "Cu admin success", Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject contactJson = jsonArray.getJSONObject(i);
                                    Contact contact = new Contact(contactJson.getInt("id"),
                                            contactJson.getString("ecode"),
                                            contactJson.getString("name"),
                                            contactJson.getString("specialname"),
                                            contactJson.getString("category"),
                                            contactJson.getString("phone1"),
                                            contactJson.getString("phone2"),
                                            contactJson.getString("email"));
                                    contactList.add(contact);
                                }
                                //Toast.makeText(getApplicationContext(), "Cu admin success", Toast.LENGTH_SHORT).show();

                                adminOtherContacts = new AdminOtherContacts(ContactDetailsAdmin.this, contactList);
                                recyclerView.setAdapter(adminOtherContacts);


                            } else {
                                Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminDashboard.class));
    }
}