package complexability.motionmusicv2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.csounds.CsoundObj;
import com.csounds.CsoundObjListener;
import com.csounds.bindings.CsoundBinding;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
public class MainActivity extends AppCompatActivity implements LeftHandFragment.OnFragmentInteractionListener, RightHandFragment.OnFragmentInteractionListener, CsoundObjListener, CsoundBinding {
    public static Hands rightHand = new Hands();
    public static Hands leftHand = new Hands();

    private static final String INSTRUMENT_STATE = "instrument";
    private static final String RIGHT_ACCEL_X_STATE = "right_x";
    private static final String RIGHT_ACCEL_Y_STATE = "right_y";
    private static final String RIGHT_ACCEL_Z_STATE = "right_z";
    private static final String RIGHT_ROLL_STATE = "right_roll";
    private static final String RIGHT_PITCH_STATE = "right_pitch";

    private static final String LEFT_ACCEL_X_STATE = "left_x";
    private static final String LEFT_ACCEL_Y_STATE = "left_y";
    private static final String LEFT_ACCEL_Z_STATE = "left_z";
    private static final String LEFT_ROLL_STATE = "left_roll";
    private static final String LEFT_PITCH_STATE = "left_pitch";


    /**
     * Variable for Csound
     */

    private static final int NONE = 0;
    private static final int VOLUME = 1;
    private static final int FREQUENCY = 2;
    private static final int REVERB = 3;
    private static final int DELAY = 4;
    private static final int FLANGER = 5;
    private static final int DISTORTION = 6;
    private static final int ROTARY = 7;
    private static final int VIBRATO = 8;


    private static final int SPACY = 1000;
    private static final int GUITAR = 1001;
    private static final int FLUTE = 1002;

    private static final int PITCH_DAT  = 2000;
    private static final int ROLL_DAT   = 2001;
    private static final int X_AXIS     = 2002;
    private static final int Y_AXIS     = 2003;
    private static final int Z_AXIS     = 2004;


    private static final int MAX_ACCELEROMETER = 190;
    private static final int MAX_GYROSCOPE = 190;
    private static final int DATA_LENGTH = 5;

    private static final int STEP_THRESHOLD = 5;


    private static final int PITCH = 0;
    private static final int ROLL = 1;
    private static final int X_ACCEL = 2;
    private static final int Y_ACCEL = 3;
    private static final int Z_ACCEL = 4;

    private float volume = (float) 0.5;
    private float freq = (float) 0.5;
    private float flanger = (float) 0.0;
    private float reverb = (float) 0.72;
    private float vibrato = (float) 0.5;
    TextView _pitchTextTest;
    TextView _rollTextTest ;
    int rightHandCurrent[] = rightHand.getAllEffect();
    int leftHandCurrent[] = leftHand.getAllEffect();
    File f;
    boolean _bt_logdata = false;
    boolean _log_step = false;

    static List<String> selectedItem = new ArrayList<String>();
    /************************
     * Csound initialization
     ************************/
    CsoundObj csoundObj = new CsoundObj();
    //Structure:
    //l_inst, l_fw, l_ud, l_lr, l_tl, l_tn, r_inst, r_fw, r_ud, r_lr, r_
    CsoundMYFLTArray testArr[] = new CsoundMYFLTArray[5];

    //protected Handler handler = new Handler();
    ToggleButton startStop;
    float csdTest = 0;
    boolean btActivityStart = false;
    //Bluetooth Initialization
    protected BluetoothSPP bt;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate");

        if (savedInstanceState != null) {
            Log.d("MainActivity", "savedInstanceState != null");
            rightHand.setInstrument(savedInstanceState.getInt(INSTRUMENT_STATE));
            rightHand.setEffects(PITCH, savedInstanceState.getInt(RIGHT_PITCH_STATE));
            rightHand.setEffects(ROLL, savedInstanceState.getInt(RIGHT_ROLL_STATE));
            rightHand.setEffects(Y_ACCEL, savedInstanceState.getInt(RIGHT_ACCEL_Y_STATE));
            rightHand.setEffects(Z_ACCEL, savedInstanceState.getInt(RIGHT_ACCEL_Z_STATE));
            rightHand.setEffects(X_ACCEL, savedInstanceState.getInt(RIGHT_ACCEL_X_STATE));

            //leftHand.setInstrument(SPACY);
            leftHand.setEffects(PITCH, savedInstanceState.getInt(LEFT_PITCH_STATE));
            leftHand.setEffects(ROLL, savedInstanceState.getInt(LEFT_ROLL_STATE));
            leftHand.setEffects(Y_ACCEL, savedInstanceState.getInt(LEFT_ACCEL_Y_STATE));
            leftHand.setEffects(Z_ACCEL, savedInstanceState.getInt(LEFT_ACCEL_Z_STATE));
            leftHand.setEffects(X_ACCEL, savedInstanceState.getInt(LEFT_ACCEL_X_STATE));

        } else {
            rightHand.setInstrument(SPACY);
            rightHand.setEffects(PITCH, NONE);
            rightHand.setEffects(ROLL, NONE);
            rightHand.setEffects(X_ACCEL, NONE);
            rightHand.setEffects(Y_ACCEL, NONE);
            rightHand.setEffects(Z_ACCEL, NONE);

            leftHand.setInstrument(SPACY);
            leftHand.setEffects(PITCH, NONE);
            leftHand.setEffects(ROLL, NONE);
            leftHand.setEffects(X_ACCEL, NONE);
            leftHand.setEffects(Y_ACCEL, NONE);
            leftHand.setEffects(Z_ACCEL, NONE);
        }
        //selectedRightHandEffect = rightHand.getAllEffect();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO
        //Default value hard coded for testing


        _pitchTextTest = (TextView)findViewById(R.id.PitchValtextView);
        _rollTextTest  = (TextView)findViewById(R.id.RollValTextView) ;

        final Button testButton = (Button) findViewById(R.id.testButton);
        final Switch testSwitch = (Switch) findViewById(R.id.logSwitch);
        final Switch stepSwitch = (Switch) findViewById(R.id.stepSwitch);
        startStop = (ToggleButton) findViewById(R.id.onOffButton);
        startStop.setChecked(false);
        /*
        Here for testing csound
         */
        testSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    _bt_logdata = true;
                }
                else{
                    _bt_logdata =  false;
                }
            }
        });
        stepSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    _log_step = true;
                }
                else{
                    _log_step = false;
                }
            }
        });
        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //testArr[0].SetValue(0, csdTest);
                    Log.d("MainActivity", "SEND PLAY TO CSD");
                    switch (rightHand.getInstrument()) {
                        case SPACY:
                            csoundObj.sendScore(String.format("i1.%d 0 -0.01 %d", 0, 0));
                            break;
                        case GUITAR:
                            csoundObj.sendScore(String.format("i2.%d 0 -0.01 %d", 0, 0));
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (rightHand.getInstrument()) {
                        case SPACY:
                            csoundObj.sendScore(String.format("i-1.%d 0 0 %d", 0, 0));
                            break;
                        case GUITAR:
                            csoundObj.sendScore(String.format("i-2.%d 0 0 %d", 0, 0));
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        //**********************************************************************************
        /*
        Handle Bluetooth stuff here
         */
        bt = new BluetoothSPP(this);

        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Bluetooth is NOT available");
            finish();
        } else {
            Log.d("MainActivity", "Bluetooth is available");
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
                if (data!=null) {
                    //Log.d("BT","Hello");
                    dataProc(data);
                }
            }
        });
        //**********END BLUETOOTH*******************************//
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the dialog
                //rightHand.showSelected();
                Log.d("TEST", "BEGIN RIGHT HAND");
                Log.d("TEST","RIGHT INSTRUMENT: " + getDefinedString(rightHand.getInstrument()));
                rightHand.showSelected();
                Log.d("TEST", "BEGIN LEFT HAND");
                //Log.d("TEST","LEFT INSTRUMENT: " + getDefinedString(leftHand.getInstrument()));
                leftHand.showSelected();
            }
        });

        /**
         * CSD initialization
         */
        String csd = getResourceFileAsString(R.raw.inputtest);
        f = createTempFile(csd);
        csoundObj.addBinding(this);
        csoundObj.startCsound(f);
        //END CSD
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onStart() {
        /*
        More bluetooth handler
         */
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        btActivityStart = false;
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                Log.d("MainActivity", "Bluetooth Connect");
            }
        }
        //*********************************************
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://complexability.motionmusicv2/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://complexability.motionmusicv2/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);

        Log.d("MainActivity", "onStop");
        csoundObj.stop();
        csoundObjCompleted(csoundObj);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "onRestart");
        String csd = getResourceFileAsString(R.raw.oscil);
        f = createTempFile(csd);
        csoundObj.addBinding(this);
        csoundObj.startCsound(f);

    }

    @Override
    protected void onPause() {

        super.onPause();
        //cleanup();
        //if(!csoundObj.isPaused()) {
        //if(!btActivityStart) {
        //csoundObj.stop();
        //csoundObjCompleted(csoundObj);
        //}//}
        //csoundObj.stop();

    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy");
        //if(!btActivityStart) {
        super.onDestroy();
        f.delete();
        //}
        //csoundObj.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSaveInstanceState");
        outState.putInt(RIGHT_ROLL_STATE, rightHand.getEffect(ROLL));
        outState.putInt(RIGHT_PITCH_STATE, rightHand.getEffect(PITCH));
        outState.putInt(RIGHT_ACCEL_Y_STATE, rightHand.getEffect(Y_ACCEL));
        outState.putInt(RIGHT_ACCEL_Z_STATE, rightHand.getEffect(Z_ACCEL));
        outState.putInt(RIGHT_ACCEL_X_STATE, rightHand.getEffect(X_ACCEL));

        outState.putInt(LEFT_ROLL_STATE, leftHand.getEffect(ROLL));
        outState.putInt(LEFT_PITCH_STATE, leftHand.getEffect(PITCH));
        outState.putInt(LEFT_ACCEL_Y_STATE, leftHand.getEffect(Y_ACCEL));
        outState.putInt(LEFT_ACCEL_Z_STATE, leftHand.getEffect(Z_ACCEL));
        outState.putInt(LEFT_ACCEL_X_STATE, leftHand.getEffect(X_ACCEL));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MainActivity", "onRestoreInstanceState");
        rightHand.setInstrument(savedInstanceState.getInt(INSTRUMENT_STATE));
        rightHand.setEffects(PITCH, savedInstanceState.getInt(RIGHT_PITCH_STATE));
        rightHand.setEffects(ROLL, savedInstanceState.getInt(RIGHT_ROLL_STATE));
        rightHand.setEffects(Y_ACCEL, savedInstanceState.getInt(RIGHT_ACCEL_Y_STATE));
        rightHand.setEffects(Z_ACCEL, savedInstanceState.getInt(RIGHT_ACCEL_Z_STATE));
        rightHand.setEffects(X_ACCEL, savedInstanceState.getInt(RIGHT_ACCEL_X_STATE));

        //leftHand.setInstrument(SPACY);
        leftHand.setEffects(PITCH, savedInstanceState.getInt(LEFT_PITCH_STATE));
        leftHand.setEffects(ROLL, savedInstanceState.getInt(LEFT_ROLL_STATE));
        leftHand.setEffects(Y_ACCEL, savedInstanceState.getInt(LEFT_ACCEL_Y_STATE));
        leftHand.setEffects(Z_ACCEL, savedInstanceState.getInt(LEFT_ACCEL_Z_STATE));
        leftHand.setEffects(X_ACCEL, savedInstanceState.getInt(LEFT_ACCEL_X_STATE));

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        btActivityStart = false;
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
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
        } else if (id == R.id.action_bluetooth) {
            btActivityStart = true;
            Log.d("MainActivity", "Bluetooth Select");
            if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                bt.disconnect();
            } else {
                Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
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

        testArr[0] = csoundObj.getInputChannelPtr(String.format("frequency_mod.%d", 0), controlChannelType.CSOUND_CONTROL_CHANNEL);
        testArr[1] = csoundObj.getInputChannelPtr(String.format("volume_mod.%d", 0), controlChannelType.CSOUND_CONTROL_CHANNEL);
        testArr[2] = csoundObj.getInputChannelPtr(String.format("flanger_mod.%d", 0), controlChannelType.CSOUND_CONTROL_CHANNEL);
        testArr[3] = csoundObj.getInputChannelPtr(String.format("reverb_mod.%d", 0), controlChannelType.CSOUND_CONTROL_CHANNEL);
        testArr[4] = csoundObj.getInputChannelPtr(String.format("vibrato_mod.%d", 0), controlChannelType.CSOUND_CONTROL_CHANNEL);
    }

    @Override
    public void updateValuesToCsound() {
        //Log.d("Well","What the actual fuck");


        //Write test value for all effects
        //Use switch statement to determine which effects to be operated on
        //Run a loop before SetValue
        //Be fucking careful with how much delay it introduce
        //Possible issue with race condition
        //Log.d("UpdateValue", Float.toString(csdTest));

        testArr[0].SetValue(0, freq);
        testArr[1].SetValue(0, volume);
        testArr[2].SetValue(0, flanger);
        testArr[3].SetValue(0, reverb);
        testArr[4].SetValue(0, vibrato);
    }

    //TODO
    //Fix the processing correctly
    private int getBit(byte data,int bitNum){
        return data & (1<<bitNum);
    }
    private void logData(int data, int id){
        if(_log_step){
            if(data >= 10 && id >= 2) {
                Log.d("dataProc", "data raw[" + getDefinedString(2000+id) + "]:" + Integer.toString(data));
            }
        }
        else {
            Log.d("dataProc", "data raw[" + getDefinedString(2000+id) + "]:" + Integer.toString(data));
        }

    }
    private void dataProc(byte[] data) {
        
        if (data.length < 6) {
            return;
        }

        int rightHandEffects[] = rightHand.getAllEffect();
        int LeftHandEffects[] = leftHand.getAllEffect();
        float finalData = 0;
        int readData;
        reInitiate();
        //**********************For testing*******************************
        _pitchTextTest.setText(String.valueOf((data[0] & 0x000000FF)-90));
        _rollTextTest.setText(String.valueOf((data[1] & 0x000000FF)-90));
        //***************************************************************
        for (int i = 0; i < rightHandEffects.length; i++) {

            if((data[5] & (1<<(7-i))) != 0) {
                readData = (data[i] & 0x000000FF) * (-1);
            }
            else{
                readData = (data[i] & 0x000000FF);
            }

            //Log.d("dataProc", "data raw[" + Integer.toString(i) + ":]" + Integer.toString(readData));
            //readData = data[i];

            if (i <= 1) {
                finalData = (float) ((readData)) / (MAX_GYROSCOPE);
                //Log.d("dataProc", "In if:" + Float.toString(finalData));
            } else {
                finalData = (float) ((readData)) / (MAX_ACCELEROMETER);
            }
            if(_bt_logdata) {
                logData(readData, i);
            }
            switch (rightHandEffects[i]) {
                case NONE:
                    break;
                case VOLUME:
                    //Log.d("dataProc","volume:" + Float.toString(finalData));
                    volume = (finalData);
                    break;
                case FREQUENCY:
                    //Log.d("dataFreq", "data: " + Integer.toHexString(readData));
                    //Log.d("dataProc","freq:" + Float.toString(finalData));
                    if(_bt_logdata) {
                        //Log.d("dataProc", "data raw[" + Integer.toString(i) + "]:" + Integer.toString(readData));
                        //Log.d("dataProc", "data float[" + Integer.toString(i) + "]:" + Float.toString(finalData));
                    }
                    freq = (finalData);
                    break;
                case REVERB:
                    reverb = (finalData);
                    break;
                case DELAY:
                    break;
                case FLANGER:
                    flanger = (finalData);
                    break;
                case DISTORTION:
                    break;
                case ROTARY:
                    break;
                case VIBRATO:
                    //Log.d("dataProc","vibrato:" + Float.toString(finalData));
                    vibrato = (finalData);
                    break;
                default:
                    Log.d("MainActivity", "WTF!!!");
                    break;
            }
        }
        //LeftHand Handler
        for (int i = 0; i < LeftHandEffects.length; i++) {
            finalData = 0;
            break;
            //if (i+5 <= data.length){
            //return;
            //}
            //TODO
            /*
            readData = data[i+5];
            //Log.d("DataReceived","Num: "+ Integer.toString(i) + " \tdata: " + Integer.toString(readData));
            if(i <= 1){
                finalData = (float)(readData)/(MAX_GYROSCOPE);
                //Log.d("dataProc", "In if:" + Float.toString(finalData));
            }
            else{
                finalData = (float)(readData)/(MAX_ACCELEROMETER);
            }
            switch(LeftHandEffects[i]){
                case NONE:
                    break;
                case VOLUME:
                    //Log.d("dataProc","volume:" + Float.toString(finalData));
                    volume += finalData;
                    break;
                case FREQUENCY:
                    //Log.d("dataFreq", "data: " + Integer.toHexString(readData));
                    //Log.d("dataProc","freq:" + Float.toString(finalData));
                    freq  += finalData;
                    break;
                case REVERB:
                    reverb += finalData;
                    break;
                case DELAY:
                    break;
                case FLANGER:
                    flanger += finalData;
                    break;
                case DISTORTION:
                    break;
                case ROTARY:
                    break;
                case VIBRATO:
                    Log.d("dataProc","vibrato:" + Float.toString(finalData));
                    vibrato += finalData;
                    break;

                default:
                    Log.d("MainActivity", "WTF!!!");
                    break;
            }
            */
        }


    }

    @Override
    public void updateValuesFromCsound() {
    }

    @Override
    public void cleanup() {
        if (!btActivityStart) {
            for (int i = 0; i < testArr.length; i++) {
                testArr[i].Clear();
                testArr[i] = null;
            }
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
     * End of Csound external handler
     *******************************************************************/


    public void dialogFragmentItemSelected(String param, int selectedItem) {
        Log.d("dfItemSelected", param + ":" + selectedItem);
        TextView txt;
        switch (param) {
            case "R_pitch":
                txt = (TextView) findViewById(R.id.r_pitchText);
                txt.setText(getDefinedString(selectedItem));
                rightHand.setEffects(PITCH, selectedItem);
                break;
            case "R_roll":
                txt = (TextView) findViewById(R.id.r_rollText);
                txt.setText(getDefinedString(selectedItem));
                rightHand.setEffects(ROLL, selectedItem);
                break;
            case "instrument":
                txt = (TextView) findViewById(R.id.instText);
                txt.setText(getDefinedString(selectedItem));
                rightHand.setInstrument(selectedItem);
                break;
            case "R_fb":
                txt = (TextView) findViewById(R.id.r_fwBackText);
                txt.setText(getDefinedString(selectedItem));
                rightHand.setEffects(2, selectedItem);
                break;
            case "R_ud":
                txt = (TextView) findViewById(R.id.r_upDownText);
                txt.setText(getDefinedString(selectedItem));
                rightHand.setEffects(3, selectedItem);
                break;
            case "R_lr":
                txt = (TextView) findViewById(R.id.r_leftRightText);
                txt.setText(getDefinedString(selectedItem));
                rightHand.setEffects(4, selectedItem);
                break;

            case "L_pitch":
                txt = (TextView) findViewById(R.id.l_pitchText);
                txt.setText(getDefinedString(selectedItem));
                leftHand.setEffects(PITCH, selectedItem);
                break;
            case "L_roll":
                txt = (TextView) findViewById(R.id.l_rollText);
                txt.setText(getDefinedString(selectedItem));
                leftHand.setEffects(ROLL, selectedItem);
                break;
            case "L_fb":
                txt = (TextView) findViewById(R.id.l_fwBackText);
                txt.setText(getDefinedString(selectedItem));
                leftHand.setEffects(2, selectedItem);
                break;
            case "L_ud":
                txt = (TextView) findViewById(R.id.l_upDownText);
                txt.setText(getDefinedString(selectedItem));
                leftHand.setEffects(3, selectedItem);
                break;
            case "L_lr":
                txt = (TextView) findViewById(R.id.l_leftRightText);
                txt.setText(getDefinedString(selectedItem));
                leftHand.setEffects(4, selectedItem);
                break;
            //case "l_instrument":
            //    txt = (TextView) findViewById(R.id.leftInstrumentTxt);
            //    txt.setText(getDefinedString(selectedItem));
            //    leftHand.setInstrument(selectedItem);
            //    break;

            default:
                break;
        }
    }

    public void reInitiate() {
        reverb = 0;
        volume = (float) 0.5;
        freq = (float) 0.5;
        vibrato = 0;
        flanger = 0;
    }

    /*********************************************************************
     * End of MyDialogFragment
     *********************************************************************/
    public String getDefinedString(int data) {
        switch (data) {
            case NONE:
                return "None";
            case VOLUME:
                return "Volume";
            case FREQUENCY:
                return "Frequency";
            case REVERB:
                return "Reverb";
            case DELAY:
                return "Delay";
            case FLANGER:
                return "Flanger";
            case DISTORTION:
                return "Distortion";
            case ROTARY:
                return "Rotary";
            case SPACY:
                return "Spacy";
            case GUITAR:
                return "Guitar";
            case FLUTE:
                return "Flute";
            case VIBRATO:
                return "Vibrato";
            case PITCH_DAT:
                return "PITCH";
            case ROLL_DAT:
                return "ROLL";
            case X_AXIS:
                return "X-AXIS";
            case Y_AXIS:
                return "Y-AXIS";
            case Z_AXIS:
                return "Z-AXIS";
            default:
                return null;
        }
    }
}
