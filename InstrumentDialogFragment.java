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

import java.util.Objects;

/**
 * Created by Sorawis on 2/4/2016.
 */
public class InstrumentDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener{

    private static final int NONE       = 0;
    private static final int VOLUME     = 1;
    private static final int FREQUENCY  = 2;
    private static final int REVERB     = 3;
    private static final int DELAY      = 4;
    private static final int FLANGER    = 5;
    private static final int DISTORTION = 6;
    private static final int ROTARY     = 7;
    private static final int VIBRATO    = 8;

    private static final int SPACY      = 1000;
    private static final int GUITAR     = 1001;
    private static final int FLUTE      = 1002;

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
    protected static String[] WhoSelectedInstrument = {
            null, // Spacy
            null, // Guitar
            null  // Flute
    };
    protected String[] DisplayInstrumentList = InstrumentList;
    protected int selected;
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
        selected = MainActivity.rightHand.getInstrument();
                //this.getArguments().getInt("instrument_selected");
        if(selected >= 1000){
            selected -= 1000;
        }
        for(int i = 0 ; i < WhoSelectedInstrument.length ; i++){
            WhoSelectedInstrument[i] = null;
        }
        WhoSelectedInstrument[selected] = "instrument_selected";

        Log.d("selected", Integer.toString(selected));
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
        ((MainActivity)getActivity()).dialogFragmentItemSelected(caller, 1000+position);
        dismiss();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //Log.d("Popup", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        //        android.R.layout.simple_list_item_1, DisplayInstrumentList);
        CustomAdapter adapter = new CustomAdapter(
                getActivity(),
                InstrumentList,
                InstrumentImages,
                InstrumentDescription,
                WhoSelectedInstrument
                );

        InstrumentListView.setAdapter(adapter);

        InstrumentListView.setOnItemClickListener(this);

    }

    public String getDefinedString(int data){
        switch (data){
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
            default:
                return null;
        }
    }
}
