package com.example.myapplication.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.DialogBinding;
import com.example.myapplication.prefs.AppSharedPreferences;

public class DialogFragment extends androidx.fragment.app.DialogFragment {




    public DialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        DialogBinding binding=DialogBinding.inflate(getLayoutInflater());
         binding.codeEt.setText(AppSharedPreferences.getInstance().myCode());

        builder.setView(binding.getRoot());
        return builder.create();
    }
}

