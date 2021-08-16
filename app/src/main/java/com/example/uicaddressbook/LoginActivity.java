package com.example.uicaddressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.uicaddressbook.Dashboard.AdminDashboard;
import com.example.uicaddressbook.Dashboard.UserDashboard;
import com.example.uicaddressbook.Helper.SharedPrefManager;
import com.example.uicaddressbook.Helper.URLs;
import com.example.uicaddressbook.Helper.VolleySingleton;
import com.example.uicaddressbook.Model.User;
import com.example.uicnotifier.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private String username, password;
    private Button loginBtn;
    private ProgressBar loginProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, UserDashboard.class));
        }

        username = password = "";
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btnLogin);
        loginProgress = findViewById(R.id.login_progressbar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                userLogin();
            }
        });
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();

        if(view!=null){
            InputMethodManager manager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(
                    view.getWindowToken(), 0
            );
        }
    }

    private void userLogin() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter UID");
            etUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please Enter Password");
            etPassword.requestFocus();
            return;
        } else {
            loginProgress.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loginProgress.setVisibility(View.GONE);
                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
//                                JSONObject obj = new JSONObject(response);
                                //if no error in response
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    //getting the user from the response
                                    JSONObject userJson = obj.getJSONObject("user");

                                    //creating a new user object
                                    User user = new User(
                                            userJson.getInt("id"),
                                            userJson.getString("username"),
                                            userJson.getString("name"),
                                            userJson.getString("section"),
                                            userJson.getString("group"),
                                            userJson.getString("email"),
                                            userJson.getInt("category")
                                    );

                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    //starting the profile activity
                                    finish();

                                    if(Integer.parseInt(user.getCategory()) == 1) {
                                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));

                                    } else {
                                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));

                                    }
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Welcome " + user.getName(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Nahi Hua ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Something Wrong!!!", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
