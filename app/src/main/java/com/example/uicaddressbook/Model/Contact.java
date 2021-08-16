package com.example.uicaddressbook.Model;

import java.io.Serializable;

public class Contact implements Serializable {

    private int mId;
    private String mEcode;
    private String mName;
    private String mPhone;
    private String mPhone2;
    private String mEmail;
    private String mSpecialName;
    private String mCategory;

    public Contact(int id, String ecode, String name, String phone, String email,String specialname){
        this.mId = id;
        this.mEcode = ecode;
        this.mName = name;
        this.mPhone = phone;
        this.mEmail = email;
        this.mSpecialName=specialname;
    }

    public Contact(int id, String ecode, String name, String specialName, String category, String phone1, String phone2, String email){
        this.mId = id;
        this.mEcode = ecode;
        this.mName = name;
        this.mPhone = phone1;
        this.mPhone2 = phone2;
        this.mEmail = email;
        this.mSpecialName = specialName;
        this.mCategory = category;
    }

    public int getId() {
        return mId;
    }

    public String getEcode() {
        return mEcode;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getSpecialName() {
        return mSpecialName;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getPhone2() {
        return mPhone2;
    }
}
