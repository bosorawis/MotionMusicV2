package complexability.motionmusicv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by Sorawis on 1/26/2016.
 */
public class MyDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {


    String[] listitems = {"item1", "item2", "item3"};
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

        Toast.makeText(getActivity(), listitems[position], Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listitems);

        myList.setAdapter(adapter);

        myList.setOnItemClickListener(this);

    }



}
