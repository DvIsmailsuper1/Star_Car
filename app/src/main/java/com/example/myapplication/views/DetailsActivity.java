package com.example.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.myapplication.dialog.DialogFragment;
import com.example.myapplication.controllers.FirebaseFireStoreController;
import com.example.myapplication.databinding.ActivityDetailsBinding;
import com.example.myapplication.interfaces.DetailsCallback;
import com.example.myapplication.interfaces.ProfitsCallBack;
import com.example.myapplication.interfaces.TeamDetailsCallback;
import com.example.myapplication.prefs.AppSharedPreferences;


public class DetailsActivity extends AppCompatActivity {
   ActivityDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       binding.myCodeLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DialogFragment dialogFragment =new DialogFragment();
               dialogFragment.show(getSupportFragmentManager(),"");
           }
       });


        userNum();
        teamNum();
        binding.myCodeTv.setText(AppSharedPreferences.getInstance().myCode());
    }

    private void userNum(){
        FirebaseFireStoreController.getInstance().count(new DetailsCallback() {
            @Override
            public void count(long count) {
                binding.userNum.setText(String.valueOf(count));
            }
        });
    }


    private void teamNum(){
        Log.e("TAG", "teamNum: "+AppSharedPreferences.getInstance().myCode() );
        FirebaseFireStoreController.getInstance().countIn(AppSharedPreferences.getInstance().myCode(), new TeamDetailsCallback() {
            @Override
            public void countIn(long num) {
                FirebaseFireStoreController.getInstance().setPotentialEarn(AppSharedPreferences.getInstance().myId(),num);
              FirebaseFireStoreController.getInstance().getPotentialEarn(AppSharedPreferences.getInstance().myId(), new ProfitsCallBack() {
                  @Override
                  public void value(double v) {
                      binding.potentialEarn.setText(String.valueOf(v));
                  }
              });
                binding.teamNum.setText(String.valueOf(num));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.myCodeTv.setText(AppSharedPreferences.getInstance().myCode());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.myCodeTv.setText(AppSharedPreferences.getInstance().myCode());
    }
}