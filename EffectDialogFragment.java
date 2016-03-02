package complexability.motionmusicv2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by Sorawis on 2/20/2016.
 */

public class EffectDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener{
    ListView EffectListView;
    Context context;

    protected static String[] EffectList = {
            "Volume",
            "Frequency",
            "Reverb",
            "Delay",
            "Flanger",
            "Distortion",
            "Rotary"
    };
    protected static String[] EffectDescription = {
            "Change volume",       //Volume
            "Change frequency",    //Frequency
            "Add reverb",       //Reverb
            "Add echo",        //Delay
            "Add spacy sound",      //Flanger
            "Distort the sound",   //Distortion
            "Rotary Effect"        //Rotary
    };
    protected static String[] WhoSelectEffect = {
            null,       //Volume
            null,       //Frequency
            null,       //Reverb
            null,       //Delay
            null,       //Flanger
            null,       //Distortion
            null        //Rotary
    };

    protected static int[] EffectImages = {
            R.mipmap.ic_launcher, //Volume
            R.mipmap.ic_launcher, //Frequency
            R.mipmap.ic_launcher, //Reverb
            R.mipmap.ic_launcher, //Delay
            R.mipmap.ic_launcher, //Flanger
            R.mipmap.ic_launcher, //Distortion
            R.mipmap.ic_launcher  //Rotary
    };


    protected String[] SelectedEffects = new String[5];
    protected String caller;


    public EffectDialogFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_effect_fragment, container, false);
        EffectListView = (ListView) view.findViewById(R.id.EffectList);
        //Log.d("DialogFragment",getArguments().getString("Name"));
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        caller = this.getArguments().getString("caller");
        SelectedEffects[0] = this.getArguments().getString("R_fw_selected");
        SelectedEffects[1] = this.getArguments().getString("R_ud_selected");
        SelectedEffects[2] = this.getArguments().getString("R_lr_selected");
        SelectedEffects[3] = this.getArguments().getString("R_pitch_selected");
        SelectedEffects[4] = this.getArguments().getString("R_roll_selected");


        for(int i = 0 ; i < EffectList.length ; i++){
            WhoSelectEffect[i] = null;
        }
        for(int i = 0 ; i < SelectedEffects.length ; i++){
            for (int j = 0 ; j < EffectList.length ; j++){
                if(Objects.equals(EffectList[j], SelectedEffects[i])){
                    switch (i){
                        case 0:
                            WhoSelectEffect[j] = "R_fw_selected";
                            break;
                        case 1:
                            WhoSelectEffect[j] = "R_ud_selected";
                            break;
                        case 2:
                            WhoSelectEffect[j] = "R_lr_selected";
                            break;
                        case 3:
                            WhoSelectEffect[j] = "R_pitch_selected";
                            break;
                        case 4:
                            WhoSelectEffect[j] = "R_roll_selected";
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
        }



        switch(caller){
            case "R_fw_selected":
                break;
            case "R_ud_selected":
                break;
            case "R_lr_selected":
                break;
            case "R_pitch_selected":
                break;
            case "R_roll_selected":
                break;
            default:
                break;
        }
        getDialog().setTitle("Select Effect");
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        //selectedItem.add(listitems[position]);
        //parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.pressed_color));
        Toast.makeText(getActivity(), EffectList[position], Toast.LENGTH_SHORT)
                .show();
        ((MainActivity)getActivity()).dialogFragmentItemSelected(caller, EffectList[position]);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //Log.d("Popup", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        //        android.R.layout.simple_list_item_1, DisplayedEffectList);
        CustomAdapter adapter = new CustomAdapter(getActivity(),
                EffectList,
                EffectImages,
                EffectDescription,
                WhoSelectEffect);

        EffectListView.setAdapter(adapter);
        EffectListView.setOnItemClickListener(this);

    }
}
