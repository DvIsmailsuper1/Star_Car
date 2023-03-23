package com.example.myapplication.controllers;


import android.util.Log;
import androidx.annotation.NonNull;

import com.example.myapplication.interfaces.DetailsCallback;
import com.example.myapplication.interfaces.MyUser;
import com.example.myapplication.interfaces.ProcessCallback;
import com.example.myapplication.interfaces.ProfitsCallBack;
import com.example.myapplication.interfaces.TeamDetailsCallback;
import com.example.myapplication.interfaces.UnCodeCallback;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UsersDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirebaseFireStoreController {

    private FirebaseFireStoreController() {
    }

    private static FirebaseFireStoreController instance;

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();


    public static synchronized FirebaseFireStoreController getInstance() {
        if (instance == null) {
            instance = new FirebaseFireStoreController();
        }
        return instance;
    }



    public void countIn(String myCode, TeamDetailsCallback callback){
        Query query = fireStore.collection("UsersDetails").whereEqualTo("invitationCode", myCode);
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    callback.countIn(snapshot.getCount());
                } else {
                    Log.d("TAG", "Count failed: ", task.getException());
                }
            }
        });
    }




    public void count(DetailsCallback callback){
        Query query = fireStore.collection("Users");
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    callback.count(snapshot.getCount());
                } else {
                    Log.d("TAG", "Count failed: ", task.getException());
                }
            }
        });
    }


    //CRUD
    public void createUser(User user, ProcessCallback callback) {
        fireStore.collection("Users").document(user.getId()).set(user.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccess("created successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure("Restart the app");
            }
        });
    }

    public void createUserDetails(UsersDetails details, ProcessCallback callback) {
        fireStore.collection("UsersDetails").document(details.getId()).set(details.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccess("created successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure("Restart the app");
                    }
                });
    }




    public void setPotentialEarn(String id,long teamNum){
        // Update one field, creating the document if it does not already exist.
        Map<String, Object> data = new HashMap<>();
        if (teamNum<500){
            data.put("potentialEarn",teamNum*0.75);
        } else if (teamNum>=500&&teamNum<1000) {
            data.put("potentialEarn",teamNum);
        }else if (teamNum>=1000){
            data.put("potentialEarn",teamNum*1.25);
        }


        fireStore.collection("UsersDetails").document(id)
                .set(data, SetOptions.merge());
    }


    public void getPotentialEarn(String id, ProfitsCallBack callBack){
        DocumentReference docRef = fireStore.collection("UsersDetails").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UsersDetails user=document.toObject(UsersDetails.class);
                        callBack.value(user.getPotentialEarn());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }


    public void getMyUser(String id, MyUser myUser){
        DocumentReference docRef = fireStore.collection("Users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user=document.toObject(User.class);
                        myUser.myUser(user);
                    } else {
                        Log.d("TAG", "No such document");
                        myUser.noUser("No User");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }


    public void unCodeCheck(String  myCode, UnCodeCallback callback) {
        Query query = fireStore.collection("Users").whereEqualTo("myCode", myCode);
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    callback.countIn(snapshot.getCount());
                } else {
                    Log.d("TAG", "Count failed: ", task.getException());
                }
            }
        });
    }

//    public void setId(String id,String myCode){
//        // Update one field, creating the document if it does not already exist.
//        Map<String, Object> data = new HashMap<>();
//            data.put("id",id);
//        fireStore.collection("Users").document(myCode)
//                .set(data, SetOptions.merge());
//    }

}
