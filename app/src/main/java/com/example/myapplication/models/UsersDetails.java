package com.example.myapplication.models;

import java.util.HashMap;
import java.util.Map;

public class UsersDetails {
     private String id;
    private String myCode;
    private String invitationCode;
    private double profits;
    private double potentialEarn;

    public UsersDetails() {
    }

    public UsersDetails(String id, String myCode, String invitationCode, double profits, double potentialEarn) {
        this.id = id;
        this.myCode = myCode;
        this.invitationCode = invitationCode;
        this.profits = profits;
        this.potentialEarn = potentialEarn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyCode() {
        return myCode;
    }

    public void setMyCode(String myCode) {
        this.myCode = myCode;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public double getProfits() {
        return profits;
    }

    public void setProfits(double profits) {
        this.profits = profits;
    }

    public double getPotentialEarn() {
        return potentialEarn;
    }

    public void setPotentialEarn(double potentialEarn) {
        this.potentialEarn = potentialEarn;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("myCode", myCode);
        map.put("invitationCode", invitationCode);
        map.put("profits", profits);
        map.put("potentialEarn", potentialEarn);
        return map;
    }
}
