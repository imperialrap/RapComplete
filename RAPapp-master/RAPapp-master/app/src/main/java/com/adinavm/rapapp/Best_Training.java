package com.adinavm.rapapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Best_Training extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_training);
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }
}
