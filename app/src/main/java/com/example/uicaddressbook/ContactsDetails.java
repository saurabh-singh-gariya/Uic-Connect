package com.example.uicaddressbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.uicaddressbook.Adapter.AdminOtherContacts;
import com.example.uicaddressbook.Adapter.AdminProfContacts;
import com.example.uicaddressbook.Adapter.PdfLoadAdapter;
import com.example.uicaddressbook.Adapter.ProfContactAdapter;
import com.example.uicaddressbook.Adapter.CuOtherAdapter;
import com.example.uicaddressbook.Helper.URLs;
import com.example.uicaddressbook.Helper.VolleySingleton;
import com.example.uicaddressbook.Model.Contact;
import com.example.uicaddressbook.Model.Pdf;
import com.example.uicnotifier.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactsDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Contact> contactList;
    List<Pdf> pdfList;
    PdfLoadAdapter mPdfLoadAdapter;
    ProfContactAdapter mProfContactAdapter;
    CuOtherAdapter mCuOtherAdapter;
    AdminOtherContacts adminOtherContacts;
    AdminProfContacts adminProfContacts;
    SearchView searchContact;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_details);
        Intent intentx2 = getIntent();
        int option = intentx2.getIntExtra("contactType", 1);
        contactList = new ArrayList<>();
        pdfList = new ArrayList<>();

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

        if (option == 1) {
            getSupportActionBar().setTitle(R.string.admin);
            loadCuAdmins();
        }
        if (option == 2) {
            getSupportActionBar().setTitle(R.string.helpline);
            loadCuHelpline();
        }
        if (option == 3) {
            getSupportActionBar().setTitle(R.string.prof);
            loadFacultyContacts();
        }
        if (option == 4) {
            getSupportActionBar().setTitle(R.string.fav);
            loadFiles();
        }
        if (option == 5) {
            //getSupportActionBar().setTitle(R.string.social);
            //loadProfile();
        }
        if (option == 6) {
            getSupportActionBar().setTitle(R.string.dsw);
            loadDswContacts();
        }

        searchContact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(option==1||option==6||option==2||option==5) {
                    mCuOtherAdapter.getFilter().filter(newText);
                }
                if(option==8){
                    adminOtherContacts.getFilter().filter(newText);
                }
                if(option==7){
                    adminProfContacts.getFilter().filter(newText);
                }
                else {
                    mProfContactAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    private void loadCuHelpline() {
            //Toast.makeText(getApplicationContext(), "Cu admin", Toast.LENGTH_SHORT).show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_CU_HELPLINE,
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

                                    mCuOtherAdapter = new CuOtherAdapter(ContactsDetails.this, contactList);
                                    recyclerView.setAdapter(mCuOtherAdapter);


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

    private void loadDswContacts() {
        //Toast.makeText(getApplicationContext(), "Cu admin", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_DSW,
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

                                mCuOtherAdapter = new CuOtherAdapter(ContactsDetails.this, contactList);
                                recyclerView.setAdapter(mCuOtherAdapter);

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

    private void loadCuAdmins(){
        //Toast.makeText(getApplicationContext(), "Cu admin", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_CU_ADMIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray jsonArray = jsonObject.getJSONArray("contact");
                            contactList.clear();
                            if(jsonObject.getInt("success")==1){
//                                Toast.makeText(getApplicationContext(), "Cu admin success", Toast.LENGTH_SHORT).show();

                                for(int i=0; i< jsonArray.length();i++){
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
                               // Toast.makeText(getApplicationContext(), "Cu admin success", Toast.LENGTH_SHORT).show();

                                mCuOtherAdapter = new CuOtherAdapter(ContactsDetails.this, contactList);
                                recyclerView.setAdapter(mCuOtherAdapter);

                            } else{
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

    private void loadFacultyContacts() {
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
                                   // System.out.println("------------------------------------------------jskj");
                                   // System.out.println(contactJson.getString("specialname"));
                                    contactList.add(contact);
                                }

                                mProfContactAdapter = new ProfContactAdapter(ContactsDetails.this , contactList);
                                recyclerView.setAdapter(mProfContactAdapter);
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

    private void loadFiles(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GET_PDF_FILES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONArray jsonArray = jsonObject.getJSONArray("file");
                            if(jsonObject.getInt("success")==1){
                                pdfList.clear();
//                                Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_LONG).show();
                                for(int i=0; i< jsonArray.length(); i++){
                                    JSONObject pdfJson = jsonArray.getJSONObject(i);
                                    Pdf pdf = new Pdf( pdfJson.getInt("id"),
                                            pdfJson.getString("filename"),
                                            pdfJson.getString("url")
                                    );
                                    // System.out.println("------------------------------------------------jskj");
                                    // System.out.println(contactJson.getString("specialname"));
                                    pdfList.add(pdf);
                                }

                                mPdfLoadAdapter = new PdfLoadAdapter(ContactsDetails.this , pdfList);
                                recyclerView.setAdapter(mPdfLoadAdapter);
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
                        Toast.makeText(getApplicationContext(), "Error Occurred!!", Toast.LENGTH_SHORT).show();
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
//
//    public void searchContact(View view) {
//　  InputMethodManager im = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
//        im.showSoftInput(editText, 0);
//    }
}