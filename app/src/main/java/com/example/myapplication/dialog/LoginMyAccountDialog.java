package com.example.myapplication.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.LoginMyAccountDialogBinding;
import com.example.myapplication.interfaces.LoginMyAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class LoginMyAccountDialog extends androidx.fragment.app.DialogFragment {

    private LoginMyAccount listener;


    public LoginMyAccountDialog() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginMyAccount){
            listener= (LoginMyAccount) context;
        }else throw new RuntimeException("kkk");
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LoginMyAccountDialogBinding binding=LoginMyAccountDialogBinding.inflate(getLayoutInflater());

        binding.sava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.phoneEt.getText().toString().trim().isEmpty()&&binding.phoneEt.getText().toString().trim().length()==9){
                    listener.onClick(binding.phoneEt.getText().toString().trim());
                    dismiss();
                }else {
                    Toast.makeText(getActivity(), "تاكد من رقم الهاتف", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(binding.getRoot());
        return builder.create();
    }



}

