package com.example.uicaddressbook.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.uicaddressbook.LoginActivity;
import com.example.uicaddressbook.Model.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "uicuserlogin";
    private static final String KEY_NAME="keyname";
    private static final String KEY_EMAIL="keyemail";
    private static final String KEY_SECTION="keysection";
    private static final String KEY_GROUP="keygroup";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_CATEGORY = "keycategory";
    private static final String KEY_ID = "keyid";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_SECTION, user.getSection());
        editor.putString(KEY_GROUP, user.getGroup());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_CATEGORY, user.getCategory());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_SECTION, null),
                sharedPreferences.getString(KEY_GROUP, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_CATEGORY, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}
