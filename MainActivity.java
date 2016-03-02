package complexability.motionmusicv2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.csounds.CsoundObj;
import com.csounds.CsoundObjListener;
import com.csounds.bindings.CsoundBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import csnd6.CsoundMYFLTArray;
import csnd6.controlChannelType;

/**
 * MainActivity of the app
 * Contains
 * **Bluetooth
 * **Csound
 */
public class MainActivity extends AppCompatActivity implements LeftHandFragment.OnFragmentInteractionListener, RightHandFragment.OnFragmentInteractionListener, CsoundObjListener,CsoundBinding {
    public static Hands rightHand = new Hands();
    /**
     * Variable for Csound
     */

    private static final int NONE       = 0;
    private static final int VOLUME     = 1;
    private static final int FREQUENCY  = 2;
    private static final int REVERB     = 3;
    private static final int DELAY      = 4;
    private static final int FLANGER    = 5;
    private static final int DISTORTION = 6;
    private static final int ROTARY     = 7;

    private static final int PITCH_DATA   = 0;
    private static final int ROLL_DATA    = 1;
    private static final int X_ACCEL_DATA = 2;
    private static final int Y_ACCEL_DATA = 3;
    private static final int Z_ACCEL_DATA = 4;

    private  float r_volume = (float) 0.0;
    private  float r_freq = (float) 0.0;

    String rightHandCurrent[] = rightHand.getAllEffect();

    int r_pitchData;
    int r_rollData;
    int r_x_accelData;
    int r_y_accelData;
    int r_z_accelData;

    static List<String> selectedItem = new ArrayList<String>();
    /************************
    Csound initialization
     ************************/
    CsoundObj csoundObj = new CsoundObj();
    //Structure:
    //l_inst, l_fw, l_ud, l_lr, l_tl, l_tn, r_inst, r_fw, r_ud, r_lr, r_
    CsoundMYFLTArray testArr[] = new CsoundMYFLTArray[2];

    //protected Handler handler = new Handler();
    ToggleButton startStop;
    float csdTest = 0;

    //Bluetooth Initialization
    protected  BluetoothSPP bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rightHand.setInstrument("Space");
        rightHand.setEffects(0, "Delay");
        rightHand.setEffects(1,"Reverb");
        rightHand.setEffects(2,"Flanger");
        rightHand.setEffects(3,"Frequency");
        rightHand.setEffects(4,"Volume");

        //selectedRightHandEffect = rightHand.getAllEffect();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO
        //Default value hard coded for testing

        final Button testButton = (Button) findViewById(R.id.testButton);
        final Button checkButton = (Button) findViewById(R.id.checkVal);
        startStop = (ToggleButton) findViewById(R.id.onOffButton);
        /*
        Here for testing csound
         */
        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //testArr[0].SetValue(0, csdTest);
                    Log.d("MainActivity", "SEND PLAY TO CSD");
                    csoundObj.sendScore(String.format("i1.%d 0 -2 %d", 0, 0));
                }
                else{
                    csoundObj.sendScore(String.format("i-1.%d 0 0 %d", 0, 0));                }
            }
        });
        //**********************************************************************************
        /*
        Handle Bluetooth stuff here
         */
        bt = new BluetoothSPP(this);

        if(!bt.isBluetoothAvailable()){
            Toast.makeText(getApplicationContext() , "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Bluetooth is NOT available");
            finish();
        }
        else{
            Log.d("MainActivity","Bluetooth is available");
        }

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * Data receive method
         */
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                //Toast.makeText(SimpleActivity.this, message, Toast.LENGTH_SHORT).show();
                //Log.d("MainActivity", message);

                //TODO
                //data[] is 5 bytes long
                // data[0] - 90     pitch
                // data[1] - 90     roll
                // data[2] - 100    x_speed
                // data[3] - 100    y_speed
                // data[4] - 100    z_speed

                r_pitchData   =  data[PITCH_DATA];
                r_rollData    =  data[ROLL_DATA];
                //r_x_accelData =  data[2];
                //r_y_accelData =  data[3];
                //r_z_accelData =  data[4];
                for (int i = 0 ; i < rightHandCurrent.length ; i++){
                    switch (rightHandCurrent[i]){
                        //case
                    }
                }

                //csdTest += Float.parseFloat(message);
                //Log.d("MainActivity", "val: " + csdTest);
                //for(int i = 0 ; i < testArr.length ; i++) {
                //    if (testArr[i] != null) {
                        //Log.d("Hello","Fuck you");
                //testArr[0].SetValue(0, csdTest);
            }
        });
        //**********END BLUETOOTH*******************************//
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the dialog
                //rightHand.showSelected();
                int[] byteArrayTest = new int[]{0xFD, 0x2D, 0x64, 0x64, 0x64 };
                int testValue = byteArrayTest[0];
                Log.d("testButtonClick", "Byte:" + Integer.toString(testValue - 128));
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }

            }
        });

        /**
         * CSD initialization
         */
        String csd = getResourceFileAsString(R.raw.test);
        File f = createTempFile(csd);
        csoundObj.addBinding(this);
        //Log.d("MainActivity", "this is " + String.valueOf(this));
        csoundObj.startCsound(f);
        //END CSD
        //Add Bluetooth connect button somewhere
    }

    @Override
    protected void onStart() {
        /*
        More bluetooth handler
         */
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        }
        else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                Log.d("MainActivity", "Bluetooth Connect");
            }
        }
        //*********************************************
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_bluetooth){
            Log.d("MainActivity", "Bluetooth Select");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    Helper function to check if an item is in selectedItem
     */
    protected boolean isSelected(String item){
        int i;
        for (i = 0 ; i < selectedItem.size() ; i++){
            if(Objects.equals(selectedItem.get(i), item)){
                return true;
            }
        }
        return false;
    }

    protected void removeItem(String item) {
        int i;
        for (i = 0; i < selectedItem.size(); i++) {
            if (Objects.equals(selectedItem.get(i), item)) {
                selectedItem.remove(i);
                return;
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Csound handler functions
     * Taken from BaseCsoundActivity because we are working with single activity application
     */

    protected String getResourceFileAsString(int resId) {
        StringBuilder str = new StringBuilder();

        InputStream is = getResources().openRawResource(resId);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        String line;

        try {
            while ((line = r.readLine()) != null) {
                str.append(line).append("\n");
            }
        } catch (IOException ios) {

        }

        return str.toString();
    }

    protected File createTempFile(String csd) {
        File f = null;

        try {
            f = File.createTempFile("temp", ".csd", this.getCacheDir());
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(csd.getBytes());
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return f;
    }
    @Override
    public void setup(CsoundObj csoundObj) {
        Log.d("MainActivity", "Setup");
        //for (int i = 0; i < testArr.length; i++) {

        testArr[0] = csoundObj.getInputChannelPtr(String.format("frequency_mod.%d",0), controlChannelType.CSOUND_CONTROL_CHANNEL);
        testArr[1] = csoundObj.getInputChannelPtr(String.format("volume_mod.%d",0), controlChannelType.CSOUND_CONTROL_CHANNEL);

    }

    @Override
    public void updateValuesToCsound() {
        //Log.d("Well","What the actual fuck");
        //for (int i = 0; i < testArr.length; i++) {
        //    float send = csdTest/100;
        //    //if(send>=1){
        //     //   send = 1;
        //    //}
        //    testArr[i].SetValue(0, send);
        //    //freqArr[0].SetValue(0, send);
        //}


        //Write test value for all effects
        //Use switch statement to determine which effects to be operated on
        //Run a loop before SetValue
        //Be fucking careful with how much delay it introduce
        //Possible issue with race condition
        //Log.d("UpdateValue", Float.toString(csdTest));

        testArr[0].SetValue(0, csdTest/100);
        testArr[1].SetValue(0, 1.0);
    }

    private void dataProc(Byte data, int id){
        String currentOption[] = rightHand.getAllEffect();
        int readData = data;
        float finalData;
        for(int i = 0 ; i < currentOption.length ; i++){
            Log.d("dataProc", currentOption[i]);
            switch(currentOption[i]){
                case "Volume":

                    break;
                case "Frequency":
                    break;
                case "Reverb":
                    break;
                case "Delay":
                    break;
                case "Flanger":
                    break;
                case "Distortion":
                    break;
                case "Rotary":
                    break;
                default:
                    Log.d("MainActivity", "WTF!!!");
                    break;
            }
        }
    }

    @Override
    public void updateValuesFromCsound() {}

    @Override
    public void cleanup() {
        for (int i = 0; i < testArr.length; i++) {
            testArr[i].Clear();
            testArr[i] = null;
        }
    }

    @Override
    public void csoundObjStarted(CsoundObj csoundObj) {
        Log.d("MainActivity", "csoundObjStarted");
    }

    @Override
    public void csoundObjCompleted(CsoundObj csoundObj) {
        Log.d("MainActivity", "csoundObjCompleted");

    }


    /**********************************************************************
                    End of Csound external handler
     * *******************************************************************/


    public void dialogFragmentItemSelected(String param, String selectedItem){
        Log.d("dfItemSelected", param+":" + selectedItem);
        TextView txt;
        switch(param){
            case "R_inst":
                txt = (TextView) findViewById(R.id.instText);
                txt.setText(selectedItem);
                rightHand.setInstrument(selectedItem);
                break;
            case "R_fb":
                txt = (TextView) findViewById(R.id.fwBackText);
                txt.setText(selectedItem);
                rightHand.setEffects(0, selectedItem);
                break;
            case "R_ud":
                txt = (TextView) findViewById(R.id.upDownText);
                txt.setText(selectedItem);
                rightHand.setEffects(1, selectedItem);
                break;
            case "R_lr":
                txt = (TextView) findViewById(R.id.leftRightText);
                txt.setText(selectedItem);
                rightHand.setEffects(2, selectedItem);
                break;
            case "R_pitch":
                txt = (TextView) findViewById(R.id.pitchText);
                txt.setText(selectedItem);
                rightHand.setEffects(3, selectedItem);
                break;
            case "R_roll":
                txt = (TextView) findViewById(R.id.rollText);
                txt.setText(selectedItem);
                rightHand.setEffects(4, selectedItem);
                break;
            default:
                break;
        }
    }
     /*********************************************************************
                    End of MyDialogFragment
     *********************************************************************/
}
/**
 * Design to deal with the data proc
 * Use getEffect Statement
 * Do String compared
 *
 **/