package com.adinavm.rapapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText editText9 = (EditText) findViewById(R.id.editText9);
        final EditText editText5 = (EditText) findViewById(R.id.editText5);
        final EditText editText8 = (EditText) findViewById(R.id.editText8);
        final EditText editText4 = (EditText) findViewById(R.id.editText4);
        final EditText editText7 = (EditText) findViewById(R.id.editText7);
        final EditText editText6 = (EditText) findViewById(R.id.editText6);
        final EditText editText10 = (EditText) findViewById(R.id.editText10);
        final EditText editText3 = (EditText) findViewById(R.id.editText3);

        final Button button7 = (Button) findViewById(R.id.button7);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editText8.getText().toString();
                final String username = editText5.getText().toString();
                final int age = Integer.parseInt(editText9.getText().toString());
                final String password = editText4.getText().toString();
                final float weight = Float.parseFloat(editText7.getText().toString());
                final int height = Integer.parseInt(editText6.getText().toString());
                final String gender = editText10.getText().toString();
                final String email = editText3.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                Register.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username, email, password, weight, height, age, gender, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);
            }
        });
    }

    // function that sends the info/copies data to personal information screen
/*
    public void onClickConfirm(View view){
        //save data and then go to home screen
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }

    public void onClickCancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/
}
