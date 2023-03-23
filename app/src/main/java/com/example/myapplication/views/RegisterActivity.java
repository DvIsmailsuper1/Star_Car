package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.RandomString;
import com.example.myapplication.controllers.FirebaseAuthController;
import com.example.myapplication.controllers.FirebaseFireStoreController;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.example.myapplication.dialog.LoginMyAccountDialog;
import com.example.myapplication.interfaces.LoginMyAccount;
import com.example.myapplication.interfaces.ProcessCallback;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UsersDetails;
import com.example.myapplication.prefs.AppSharedPreferences;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, LoginMyAccount {
ActivityRegisterBinding binding;
private FirebaseAuth auth;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
binding=ActivityRegisterBinding.inflate(getLayoutInflater());
setContentView(binding.getRoot());

auth=FirebaseAuth.getInstance();
    auth.setLanguageCode("ar");

}

@Override
protected void onStart() {
super.onStart();
initializeView();
}



private void initializeView(){
setOnClickListener();
}

private void setOnClickListener() {
binding.save.setOnClickListener(this);
binding.loginMyAccount.setOnClickListener(this);
}

@Override
public void onClick(View view) {
if (view.getId()== R.id.save){
    if (binding.phoneEt.getText().toString().trim().length() ==9&&!binding.phoneEt.getText().toString().trim().isEmpty()
            &&!binding.firstName.getText().toString().trim().isEmpty()
            &&!binding.lastName.getText().toString().trim().isEmpty()
            &&!binding.invitationCode.getText().toString().trim().isEmpty()
            &&binding.invitationCode.getText().toString().trim().length()==6){
        User user=new User();
        user.setName(binding.firstName.getText().toString()+" "+binding.lastName.getText().toString());
        user.setPhone(binding.phoneEt.getText().toString());
        UsersDetails details=new UsersDetails();
        details.setInvitationCode(binding.invitationCode.getText().toString());
        details.setProfits(AppSharedPreferences.getInstance().profits());
        details.setPotentialEarn(AppSharedPreferences.getInstance().potentialEarn());
        AppSharedPreferences.getInstance().saveUser(user);
        AppSharedPreferences.getInstance().saveUserDetails(details);
        sendOtp(binding.phoneEt.getText().toString().trim());
    }else {
        Toast.makeText(this, "تحقق من صحة البيانات", Toast.LENGTH_SHORT).show();
    }
    }if (view.getId()==R.id.login_my_account){
        LoginMyAccountDialog dialogFragment =new LoginMyAccountDialog();
        dialogFragment.show(getSupportFragmentManager(),"");
    }

}




private void intent(){
Intent intent=new Intent(getBaseContext(), LoginActivity.class);
startActivity(intent);
}

private boolean checkData(){
if (!binding.firstName.getText().toString().isEmpty()&&
        !binding.lastName.getText().toString().isEmpty()&&
        !binding.phoneEt.getText().toString().isEmpty()&&
        !binding.invitationCode.getText().toString().isEmpty()){
    return true;
}else return false;
}



    private User user(){
        return AppSharedPreferences.getInstance().getUser();
    }

    private UsersDetails usersDetails(){
        return AppSharedPreferences.getInstance().getUserDetails();
    }



    private void sendOtp(String phoneNum){
        binding.save.setVisibility(View.INVISIBLE);
        binding.progress.setVisibility(View.VISIBLE);


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                binding.progress.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);
                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onVerificationFailed: "+e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progress.setVisibility(View.GONE);
                binding.save.setVisibility(View.VISIBLE);
                Intent intent=new Intent(getBaseContext(),LoginActivity.class);
                AppSharedPreferences.getInstance().verificationId(verificationId);
                startActivity(intent);

            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+970"+phoneNum)       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    public void onClick(String phoneNum) {
        sendOtp(phoneNum);
    }
}