package com.example.myapplication.controllers;

import androidx.annotation.NonNull;


import com.example.myapplication.interfaces.ProcessCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.TimeUnit;


public class FirebaseAuthController {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseAuthController instance;

    private FirebaseAuthController() {
    }

    public static synchronized FirebaseAuthController getInstance() {
        if(instance == null) {
            instance = new FirebaseAuthController();
        }
        return instance;
    }

    public void signOut() {
        auth.signOut();
    }


}
