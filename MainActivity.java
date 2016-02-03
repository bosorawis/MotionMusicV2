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
    protected static String testVal = "hello";
    protected static String[] listitems = {"Volume", "Pitch", "Reverb"};
    static List<String> selectedItem = new ArrayList<String>();
    /*
    Csound initialization
     */
    CsoundObj csoundObj = new CsoundObj();
    CsoundMYFLTArray testArr[] = new CsoundMYFLTArray[1];

    //protected Handler handler = new Handler();
    ToggleButton startStop;
    float csdTest = 0;

    //Bluetooth Initialization
    protected  BluetoothSPP bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final Button testButton = (Button) findViewById(R.id.testButton);
        final Button checkButton = (Button) findViewById(R.id.checkVal);
        startStop = (ToggleButton) findViewById(R.id.onOffButton);
        //testArr[0] = csoundObj.getInputChannelPtr(String.format("testValue.%d", 0), controlChannelType.CSOUND_CONTROL_CHANNEL);

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
        //TODO
        /**
         * Data receive method
         */
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                //Toast.makeText(SimpleActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", message);
                csdTest += Float.parseFloat(message);
                Log.d("MainActivity", "val: " + csdTest);
                if(testArr[0] != null) {
                    //Log.d("Hello","Fuck you");
                    testArr[0].SetValue(0, csdTest);
                    //csoundObj.sendScore(String.format("i1.%d 0 -2 %d", 0, 0));
                }
            }
        });
        //**********END BLUETOOTH*******************************//
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the dialog
                Log.d("Main", "Yay");
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                MyDialogFragment fragment = new MyDialogFragment();
                fragment.show(fm, "dialog_test_fragment");
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                int i;
                Log.d("Selected Value",testVal);
                for (i = 0 ; i < selectedItem.size() ; i++){
                    Log.d("Value: ", selectedItem.get(i));
                }
                */
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }

            }
        });
        //TODO

        /**
         * CSD initialization
         */
        String csd = getResourceFileAsString(R.raw.test);
        File f = createTempFile(csd);
        csoundObj.addBinding(this);
        Log.d("MainActivity", "this is " + String.valueOf(this));
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
    //TODO
    //Csound isn't set up correctly
    @Override
    public void setup(CsoundObj csoundObj) {
        Log.d("MainActivity", "Setup");
        testArr[0] = csoundObj.getInputChannelPtr(
                String.format("testValue.%d", 0),
                controlChannelType.CSOUND_CONTROL_CHANNEL);

    }

    @Override
    public void updateValuesToCsound() {
        //Log.d("Well","What the actual fuck");
        for (int i = 0; i < testArr.length; i++) {
            testArr[i].SetValue(0, csdTest);
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


    /**
     * DialogFragment class
     * Created by Sorawis on 1/26/2016.
     */
    public static class MyDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

        ListView myList;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_test_fragment, null, false);
            myList = (ListView) view.findViewById(R.id.listView);
            //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setTitle("Select Effect");
            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dismiss();
            testVal = listitems[position];
            selectedItem.add(listitems[position]);
            Toast.makeText(getActivity(), listitems[position], Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            //Log.d("Popup", "onActivityCreated");
            super.onActivityCreated(savedInstanceState);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, listitems);

            myList.setAdapter(adapter);

            myList.setOnItemClickListener(this);

        }
    }
    /**********************************************************************
                    End of MyDialogFragment
     * *******************************************************************/
}
