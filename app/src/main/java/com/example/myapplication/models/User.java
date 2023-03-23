package com.example.myapplication.models;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String name;
    private String myCode;
    private String phone;


    public User() {
    }

    public User(String id, String name, String myCode, String phone) {
        this.id = id;
        this.name = name;
        this.myCode = myCode;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMyCode() {
        return myCode;
    }

    public void setMyCode(String myCode) {
        this.myCode = myCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("myCode", myCode);
        map.put("phone", phone);
        return map;
    }
}
