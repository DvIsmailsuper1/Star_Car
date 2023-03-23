package com.example.myapplication.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.myapplication.AppController;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UsersDetails;

enum PrefKeys {
    loggedIn,myCode, fullName,phoneNum ,invitationCode,profits,potentialEarn,id,verificationId
}

public class AppSharedPreferences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private AppSharedPreferences() {
        sharedPreferences = AppController.getInstance().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
    }

    private static AppSharedPreferences instance;

    public static AppSharedPreferences getInstance() {
        if (instance == null) {
            instance = new AppSharedPreferences();
        }

        return instance;
    }

    public void saveUser(User user) {
        editor = sharedPreferences.edit();
        editor.putString(PrefKeys.id.name(), user.getId());
        editor.putString(PrefKeys.fullName.name(), user.getName());
        editor.putString(PrefKeys.myCode.name(), user.getMyCode());
        editor.putString(PrefKeys.phoneNum.name(), user.getPhone());
        editor.apply();
    }
    public User getUser(){
        User user=new User();
        user.setId(sharedPreferences.getString(PrefKeys.id.name(),""));
        user.setName(sharedPreferences.getString(PrefKeys.fullName.name(),""));
        user.setPhone(sharedPreferences.getString(PrefKeys.phoneNum.name(),""));
        user.setMyCode(sharedPreferences.getString(PrefKeys.myCode.name(),""));
        return user;
    }

    public UsersDetails getUserDetails(){
        UsersDetails user=new UsersDetails();
        user.setId(sharedPreferences.getString(PrefKeys.id.name(),""));
        user.setInvitationCode(sharedPreferences.getString(PrefKeys.invitationCode.name(),""));
        user.setMyCode(sharedPreferences.getString(PrefKeys.myCode.name(),""));
        user.setProfits(sharedPreferences.getInt(PrefKeys.profits.name(),0));
        user.setPotentialEarn(sharedPreferences.getInt(PrefKeys.potentialEarn.name(),0));
        return user;
    }

    public void saveUserDetails(UsersDetails details) {
        editor = sharedPreferences.edit();
        editor.putString(PrefKeys.myCode.name(), details.getMyCode());
        editor.putString(PrefKeys.invitationCode.name(), details.getInvitationCode());
        editor.apply();
    }

    public String fullName(){
        return sharedPreferences.getString(PrefKeys.fullName.name(), "");
    }

    public String myCode(){
        return sharedPreferences.getString(PrefKeys.myCode.name(), "");
    }

    public String myId(){
        return sharedPreferences.getString(PrefKeys.id.name(), "");
    }

    public String invitationCode(){
        return sharedPreferences.getString(PrefKeys.invitationCode.name(), "");
    }

    public void setMyCode(String myCode){
        editor = sharedPreferences.edit();
        editor.putString(PrefKeys.myCode.name(),myCode);
        editor.apply();
    }

    public void setId(String id){
        editor = sharedPreferences.edit();
        editor.putString(PrefKeys.id.name(),id);
        editor.apply();
    }

    public String phoneNum(){
        return sharedPreferences.getString(PrefKeys.phoneNum.name(), "");
    }

    public int profits(){
        return sharedPreferences.getInt(PrefKeys.profits.name(), 0);
    }

    public int potentialEarn(){
        return sharedPreferences.getInt(PrefKeys.potentialEarn.name(), 0);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(PrefKeys.loggedIn.name(), false);
    }

    public void setLoggedIn(){
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefKeys.loggedIn.name(), true);
        editor.apply();
    }


    public void logout(){
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefKeys.loggedIn.name(), false);
        editor.apply();
    }


    public void verificationId(String verificationId){
        editor = sharedPreferences.edit();
        editor.putString(PrefKeys.verificationId.name(), verificationId);
        editor.apply();
    }

    public String getVerificationId(){
        return sharedPreferences.getString(PrefKeys.verificationId.name(), "");
    }


    public void clear() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
