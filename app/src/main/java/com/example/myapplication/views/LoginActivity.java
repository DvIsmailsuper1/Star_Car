package com.example.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.RandomString;
import com.example.myapplication.controllers.FirebaseFireStoreController;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.interfaces.MyUser;
import com.example.myapplication.interfaces.ProcessCallback;
import com.example.myapplication.interfaces.UnCodeCallback;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UsersDetails;
import com.example.myapplication.prefs.AppSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        initializeViews();
        super.onStart();
    }

    private void initializeViews() {
        setOnClickListener();
    }

    private void setOnClickListener() {
        binding.loginBtn.setOnClickListener(this);
        binding.signUpBtn.setOnClickListener(this);
        binding.smsCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.login_btn){
          if (checkData()){
              if (!AppSharedPreferences.getInstance().getVerificationId().isEmpty()){
                   String a= AppSharedPreferences.getInstance().getVerificationId();
                   PhoneAuthCredential credential = PhoneAuthProvider.getCredential(a,
                          binding.smsCode.getText().toString().trim());
                  FirebaseAuth.getInstance()
                          .signInWithCredential(credential)
                          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                                  if (task.isSuccessful()){
                                      FirebaseUser user = task.getResult().getUser();
                                      String id=user.getUid();
                                      User userLocal=new User();
                                      userLocal.setId(id);
                                      FirebaseFireStoreController.getInstance().getMyUser(id, new MyUser() {
                                          @Override
                                          public void myUser(User user) {
                                          AppSharedPreferences.getInstance().saveUser(user);
                                              Log.e("TAG", "myUser: "+user.getMyCode());
                                              Toast.makeText(LoginActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                              intent();
                                          }

                                          @Override
                                          public void noUser(String s) {
                                              setMyCode();
                                              userLocal.setMyCode(AppSharedPreferences.getInstance().myCode());
                                              userLocal.setPhone(AppSharedPreferences.getInstance().phoneNum());
                                              userLocal.setName(AppSharedPreferences.getInstance().fullName());
                                              AppSharedPreferences.getInstance().saveUser(userLocal);
                                             FirebaseFireStoreController.getInstance().unCodeCheck(AppSharedPreferences.getInstance().myCode(), new UnCodeCallback() {
                                                 @Override
                                                 public void countIn(long num) {
                                                     if (num == 0){
                                                         FirebaseFireStoreController.getInstance().createUser(userLocal, new ProcessCallback() {
                                                             @Override
                                                             public void onSuccess(String message) {
                                                                 UsersDetails details=new UsersDetails();
                                                                 details.setId(id);
                                                                 details.setPotentialEarn(0);
                                                                 details.setProfits(0);
                                                                 details.setInvitationCode(AppSharedPreferences.getInstance().invitationCode());
                                                                 details.setMyCode(AppSharedPreferences.getInstance().myCode());
                                                                 AppSharedPreferences.getInstance().saveUserDetails(details);
                                                                 FirebaseFireStoreController.getInstance().createUserDetails(details, new ProcessCallback() {
                                                                     @Override
                                                                     public void onSuccess(String message) {
                                                                         Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                                                         intent();
                                                                     }

                                                                     @Override
                                                                     public void onFailure(String message) {
                                                                         Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                                                     }
                                                                 });
                                                             }

                                                             @Override
                                                             public void onFailure(String message) {
                                                                 Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                                             }
                                                         });
                                                     }else {
                                                         Toast.makeText(LoginActivity.this, "حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
                                             });
                                          }
                                      });
                                  }
                              }
                          });
              }

          }else Toast.makeText(this, "تحقق من البريد او كلمة المرور", Toast.LENGTH_SHORT).show();

        } if (view.getId()==R.id.sign_up_btn){
            Intent intent=new Intent(getBaseContext(), RegisterActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkData(){
        if (!binding.smsCode.getText().toString().isEmpty()){
            return true;
        }else return false;
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void setMyCode(){
        AppSharedPreferences.getInstance().setMyCode(RandomString.generate());
    }

    private void intent(){
        Intent intent=new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }



}










