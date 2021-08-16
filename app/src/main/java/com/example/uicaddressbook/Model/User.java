package com.example.uicaddressbook.Model;

import com.android.volley.toolbox.StringRequest;

public class User {
    public static int id;
    private String username;
    private String name;
    private String section;
    private String group;
    private String email;
    private String category;

    public User(int id, String username, String name, String section, String group, String email, int category) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.section = section;
        this.group = group;
        this.email = email;
        this.category = String.valueOf(category);
    }

    public User(int id, String username, String name, String section, String group, String email, String category) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.section = section;
        this.group = group;
        this.email = email;
        this.category = category;
    }

    public User(int id, String username, String category){
        this.id = id;
        this.username = username;
        this.category = category;
    }

    public User(int id, String username, int category){
        this.id = id;
        this.username = username;
        this.category = String.valueOf(category);
    }

    public static int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public String getGroup() {
        return group;
    }

    public String getEmail() {
        return email;
    }

    public String getCategory() {
        return category;
    }
}
