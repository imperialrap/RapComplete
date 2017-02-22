package com.adinavm.rapapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "adina's message: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        final EditText editText5 = (EditText) findViewById(R.id.editText5);
        final EditText editText4 = (EditText) findViewById(R.id.editText4);

        final Button button = (Button) findViewById(R.id.button);
        final TextView registerLink = (TextView) findViewById(R.id.registerLink);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editText5.getText().toString();
                final String password = editText4.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");
                                int weight = jsonResponse.getInt("weight");
                                int height = jsonResponse.getInt("height");
                                String email = jsonResponse.getString("email");
                                String gender = jsonResponse.getString("gender");

                                Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                                intent.putExtra("name", name);
                                intent.putExtra("age", age);
                                intent.putExtra("username", username);
                                intent.putExtra("weight", weight);
                                intent.putExtra("height", height);
                                intent.putExtra("gender", gender);
                                intent.putExtra("email", email);

                                MainActivity.this.startActivity(intent);

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Login failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

    }
}

    /*@Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }


    }

    public void onClickLogIn(View view){
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }
*/


