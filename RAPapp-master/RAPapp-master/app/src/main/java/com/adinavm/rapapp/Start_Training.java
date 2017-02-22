package com.adinavm.rapapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.bluetooth.BluetoothAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.graphics.Point;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.Gravity;
import android.widget.PopupWindow;
import android.graphics.drawable.BitmapDrawable;



public class Start_Training extends AppCompatActivity {

    //The "x" and "y" position of the "Show Button" on screen.
    Point p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__training);

        Button btn_show = (Button) findViewById(R.id.button13);
        btn_show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    showPopup(Start_Training.this, p);
                    // Device does not support Bluetooth - check if supported
                } else {
                    if (mBluetoothAdapter.isEnabled()) {
                        showPopup(Start_Training.this, p);
                        // Bluetooth not enabled
                    }
                }}});
    }

    // when you click the back button, it returns to Home_Screen
    public void onClickBack(View view) {
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }

    // Get the x and y position after the button is draw on screen
    // (It's important to note that we can't get the position in the onCreate(),
    // because at that stage most probably the view isn't drawn yet, so it will return (0, 0))
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        Button button = (Button) findViewById(R.id.button13);

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        button.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    // The method that displays the popup.
    private void showPopup(final AppCompatActivity context, Point p) {
        int popupWidth = 1000;
        int popupHeight = 5000;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 200;
        int OFFSET_Y = -2000;


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }
}