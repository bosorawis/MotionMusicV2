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
    protected final String[] InstrumentList = {
            "Space",
            "Guitar",
            "Flute"
    };

    protected static String[] InstrumentDescription = {
            "Spacy sound",       //Space
            "Guitar sound",    //Guitar
            "Flute sound",           //Flute
    };
    protected static int[] InstrumentImages = {
            R.mipmap.ic_launcher, //Space
            R.mipmap.ic_launcher, //Guitar
            R.mipmap.ic_launcher, //Flute
    };
    protected String[] DisplayInstrumentList = InstrumentList;
    protected String previous;
    protected String caller;


    public InstrumentDialogFragment(){
        Log.d("InstrumentFragment", "Constructor");
    }
    /*
    public static InstrumentDialogFragment newInstance(String Name){
        InstrumentDialogFragment myFragment = new InstrumentDialogFragment();
        Bundle args = new Bundle();
        args.putString("Name", Name);
        myFragment.setArguments(args);
        return myFragment;
    }
    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_instrument_fragment, null, false);
        InstrumentListView = (ListView) view.findViewById(R.id.InstrumentList);
        //Log.d("DialogFragment",getArguments().getString("Name"));
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        caller = this.getArguments().getString("caller");
        previous = this.getArguments().getString("previous");

        for(int i = 0 ; i<DisplayInstrumentList.length ; i++){
            if(DisplayInstrumentList[i] == previous){
                DisplayInstrumentList[i] = "Selected - " + InstrumentList[i];
                break;
            }
        }

        getDialog().setTitle("Select Instrument");
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //dismiss();
        //selectedItem.add(listitems[position]);

        view.setSelected(true);
        Toast.makeText(getActivity(), InstrumentList[position], Toast.LENGTH_SHORT)
                .show();
        //DisplayInstrumentList = InstrumentList;
        //DisplayInstrumentList[position] = "Selected - " + InstrumentList[position];
        ((MainActivity)getActivity()).dialogFragmentItemSelected(caller, position);
        dismiss();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //Log.d("Popup", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, DisplayInstrumentList);
        /*CustomAdapter adapter = new CustomAdapter(
                getActivity(),
                InstrumentList,
                InstrumentImages,
                InstrumentDescription
        );*/

        InstrumentListView.setAdapter(adapter);

        InstrumentListView.setOnItemClickListener(this);

    }

}
