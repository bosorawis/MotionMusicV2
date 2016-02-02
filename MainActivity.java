package complexability.motionmusicv2;

import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LeftHandFragment.OnFragmentInteractionListener, RightHandFragment.OnFragmentInteractionListener{
    protected static String testVal = "hello";
    protected static String[] listitems = {"Volume", "Pitch", "Reverb"};
    static List<String> selectedItem = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Button testButton = (Button) findViewById(R.id.testButton);
        final Button checkButton = (Button) findViewById(R.id.checkVal);

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
                int i;
                Log.d("Selected Value",testVal);
                for (i = 0 ; i < selectedItem.size() ; i++){
                    Log.d("Value: ", selectedItem.get(i));
                }
            }
        });
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
}
