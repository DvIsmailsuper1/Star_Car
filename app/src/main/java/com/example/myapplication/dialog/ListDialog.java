package com.example.myapplication.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.controllers.FirebaseAuthController;
import com.example.myapplication.databinding.DialogBinding;
import com.example.myapplication.databinding.ListDialogBinding;
import com.example.myapplication.prefs.AppSharedPreferences;
import com.example.myapplication.views.DetailsActivity;
import com.example.myapplication.views.LoginActivity;
import com.example.myapplication.views.RegisterActivity;

public class ListDialog extends androidx.fragment.app.DialogFragment {




    public ListDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        ListDialogBinding binding=ListDialogBinding.inflate(getLayoutInflater());

        binding.userName.setText(AppSharedPreferences.getInstance().fullName());
        binding.userPhone.setText("+962"+AppSharedPreferences.getInstance().phoneNum());
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSharedPreferences.getInstance().logout();
                FirebaseAuthController.getInstance().signOut();
                AppSharedPreferences.getInstance().clear();
                Intent intent=new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.profitsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });

        builder.setView(binding.getRoot());
        return builder.create();
    }
}

