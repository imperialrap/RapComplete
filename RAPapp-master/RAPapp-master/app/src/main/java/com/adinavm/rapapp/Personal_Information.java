package com.adinavm.rapapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Personal_Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__information);

        final EditText editText9 = (EditText) findViewById(R.id.editText9);
        final EditText editText5 = (EditText) findViewById(R.id.editText5);
        final EditText editText8 = (EditText) findViewById(R.id.editText8);
        final EditText editText4 = (EditText) findViewById(R.id.editText4);
        final EditText editText7 = (EditText) findViewById(R.id.editText7);
        final EditText editText6 = (EditText) findViewById(R.id.editText6);
        final EditText editText10 = (EditText) findViewById(R.id.editText10);
        final EditText editText3 = (EditText) findViewById(R.id.editText3);

    }

    /*
    public void onClickCancel2(View view){
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }

    public void onClickSave(View view){
        // save data and then return to main screen
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }*/
}
