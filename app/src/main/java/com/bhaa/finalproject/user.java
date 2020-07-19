package com.bhaa.finalproject;

import android.widget.TextView;

public class user {

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userName;
    private String phone;
    private String email;
    private String password;

    public user(String firstName,String lastName, String userName,String phone, String email,String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.phone=phone;
        this.email=email;
        this.password=password;
    }


}
