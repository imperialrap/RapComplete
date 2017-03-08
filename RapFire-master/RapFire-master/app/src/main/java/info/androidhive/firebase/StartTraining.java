package info.androidhive.firebase;

    import java.io.IOException;
    import java.io.InputStream;
    import java.lang.reflect.Method;
    import java.util.UUID;
    import android.support.v7.app.AppCompatActivity;
    import android.bluetooth.BluetoothAdapter;
    import android.bluetooth.BluetoothDevice;
    import android.bluetooth.BluetoothSocket;
    import android.content.Intent;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Handler;
    import android.util.Log;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.os.Message;


public class StartTraining extends AppCompatActivity {

    private static final String TAG = "Start_Training";

    Button startRec, stopRec;
    TextView txtArduino;
    Handler h;

    final int RECIEVE_MESSAGE = 1; // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "98:D3:31:FB:2C:16";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_training);

        startRec = (Button) findViewById(R.id.button13); // button LED ON
        stopRec = (Button) findViewById(R.id.button25); // button LED OFF
        txtArduino = (TextView) findViewById(R.id.textView14);// for display the received data from the Arduino

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE: // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1); // create string from bytes array
                        sb.append(strIncom); // append string
                        int endOfLineIndex = sb.indexOf("\r\n"); // determine the end-of-line
                        if (endOfLineIndex > 0) { // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex); // extract string
                            sb.delete(0, sb.length()); // and clear
                            txtArduino.setText("Data from Arduino: " + sbprint); // update TextView
                            stopRec.setEnabled(true);
                            startRec.setEnabled(true);
                        }
                        //Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        break;
                }
            };
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();// get Bluetooth adapter
        checkBTState();

        startRec.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startRec.setEnabled(false);
                //Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
            }
        });


        stopRec.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                stopRec.setEnabled(false);
                //Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            }
            catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        }
        catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }
        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        }
        catch (IOException e) {
            try {
                btSocket.close();
            }
            catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");

            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "...In onPause()...");
        try {
            btSocket.close();
        }
        catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        }
        else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            }
            else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
        }

        public void run() {
            byte[] buffer = new byte[1024]; // buffer store for the stream

            int bytes = 0; // bytes returned from read()
            int begin = 0;

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer, bytes, buffer.length - bytes);
                    // Get number of bytes and message in "buffer"
                    for (int i = begin; i < bytes; i++) {
                        if (buffer[i] == "#".getBytes()[0]) {
                            mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            begin = i + 1;
                            if (i == bytes - 1)
                                bytes = 0;
                            begin = 0;
                        }
                    }
                } catch (IOException e) {
                    break;
                    //h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();
                    // Send to message queue Handler
                }

            }
        }
    }


    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            byte[] writeBuf = (byte[])msg.obj;
            int begin = (int)msg.arg1;
            int end = (int)msg.arg2;

            switch(msg.what){
                case 1:
                    String writeMessage  = new String(writeBuf);
                    writeMessage = writeMessage.substring(begin, end);
                    break;
            }
        }
    };

    // when you click the back button, it returns to Home_Screen
    public void onClickBack(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}