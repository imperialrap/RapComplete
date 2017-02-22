package com.adinavm.rapapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class Home_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

        final EditText editText8 = (EditText) findViewById(R.id.editText8);
        final EditText editText9 = (EditText) findViewById(R.id.editText9);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", -1);

        TextView welcomeMessage = (TextView) findViewById(R.id.textView4);
        String message = name + "welcome to your user account";
        welcomeMessage.setText(message);
        editText9.setText(editText9 + "");
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu){
    //    MenuInflater inflater = getMenuInflater();
    //   inflater.inflate(R.menu.home_screen, menu);
    //    return true;
    //}


    //public boolean onOptionsItemsSelected(MenuItem Button6){
    //    switch (item.getItemId()){
    //        case R.id.button6:
    //            onClickHistory();
    //            return true;
    //        default:
    //            return super.onOptionsItemSelected(button6)
    //    }
    //}


    /*
    public void onClickLogOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickPersonalInfo(View view){
        Intent intent = new Intent(this, Personal_Information.class);
        startActivity(intent);
    }

    public void onClickHistory(View view){

    }

    public void onClickGraphs(View view){
        Intent intent = new Intent(this, Graphs.class);
        startActivity(intent);
    }

    public void onClickLastTraining(View view){
        Intent intent = new Intent(this, Last_Training.class);
        startActivity(intent);
    }

    public void onClickBestTraining(View view){
        Intent intent = new Intent(this, Best_Training.class);
        startActivity(intent);
    }

    public void onClickStartTraining(View view){
        Intent intent = new Intent(this, Start_Training.class);
        startActivity(intent);
    }*/

}
