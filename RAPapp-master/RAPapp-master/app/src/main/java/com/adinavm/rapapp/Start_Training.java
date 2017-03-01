package com.adinavm.rapapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.bluetooth.BluetoothAdapter;
import android.widget.Button;
import android.content.Context;



public class Start_Training extends AppCompatActivity {

    private final String TAG = "Start_Training";

    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__training);
        //initialise the button
        Button button13 = (Button) findViewById(R.id.button13);

        Button button25 = (Button) findViewById(R.id.button25);

        mBluetoothAdapter = mBluetoothAdapter.getDefaultAdapter();

      //  button13.setOnClickListener(new View.OnClickListener(){
      //      @Override
      //      public void onClickStartRecording(View view) {
      //          enableDisableBT1();
      //      }
      //  });

      //  button25.setOnClickListener(new View.OnClickListener(){
      //      public void onClickStopRecording(View view) {
      //          enableDisableBT2();
      //      }
      //  });

    }

    public void enableDisableBT1(){
        if(mBluetoothAdapter == null){
            Log.d(TAG, "enableDisableBT: Does not have bluetooth capabilities");
        }
        else if(!mBluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableIntent);

    //        IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    //        registerReceiver(mBroadastReceiver1, BTIntent);
        }
        else if(mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: Bluetooth already ON");
        }

    }

    public void enableDisableBT2(){
        if(mBluetoothAdapter == null){
            Log.d(TAG, "enableDisableBT: Does not have bluetooth capabilities");
        }
        else if(!mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: Bluetooth is not needed");
        }
        else if(mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
        }

    }

    // private final BroadcastReceiver mBroadastReceiver1 = new BroadcastReceiver() {
     //   public void onReceive(Context context, Intent intent) {
     //       String action = Intent.getAction();
     //       if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)){
     //           final int state = Intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

     //           switch(state){
     //               case BluetoothAdapter.STATE_OFF:
     //                   Log.d(TAG, "Bluetooth: STATE OFF");
     //                   break;
     //               case BluetoothAdapter.STATE_TURNING_OFF:
     //                   Log.d(TAG, "Bluetooth: STATE TURNING OFF");
     //                   break;
     //               case BluetoothAdapter.STATE_TURNING_ON:
     //                   Log.d(TAG, "Bluetooth: STATE TURNING ON");
     //                   break;
     //               case BluetoothAdapter.STATE_ON:
     //                   Log.d(TAG, "Bluetooth: STATE ON");
     //                   break;
     //           }
     //       }

    //    }
    // };

    //@Override
    //protected void onDestroy(){
    //    super.onDestroy();
    //    unregisterReceiver(mBroadastReceiver1);
    //}



    // when you click the back button, it returns to Home_Screen
    public void onClickBack(View view) {
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }
}