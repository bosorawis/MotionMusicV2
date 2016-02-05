package complexability.motionmusicv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Sorawis on 2/4/2016.
 */
public class InstrumentDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener{


    ListView InstrumentListView;
    protected static String[] InstrumentList = {"Space", "Guitar", "Flute"};

    public InstrumentDialogFragment(){
        Log.d("InstrumentFragment", "Constructor");
    }

    public static InstrumentDialogFragment newInstance(String Name){
        InstrumentDialogFragment myFragment = new InstrumentDialogFragment();
        Bundle args = new Bundle();
        args.putString("Name", Name);
        myFragment.setArguments(args);
        return myFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_instrument_fragment, null, false);
        InstrumentListView = (ListView) view.findViewById(R.id.InstrumentList);
        //Log.d("DialogFragment",getArguments().getString("Name"));
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setTitle("Select Instrument");
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        //selectedItem.add(listitems[position]);
        Toast.makeText(getActivity(), InstrumentList[position], Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //Log.d("Popup", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, InstrumentList);

        InstrumentListView.setAdapter(adapter);

        InstrumentListView.setOnItemClickListener(this);

    }

}
