package complexability.motionmusicv2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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

    protected static String[] EffectList = {
            "None",
            "Volume",
            "Frequency",
            "Reverb",
            "Delay",
            "Flanger",
            "Distortion",
            "Rotary",
            "Vibrato"
    };
    protected static String[] EffectDescription = {
            "Nothing is selected:",
            "Change volume",       //Volume
            "Change frequency",    //Frequency
            "Add reverb",       //Reverb
            "Add echo",        //Delay
            "Add spacy sound",      //Flanger
            "Distort the sound",   //Distortion
            "Rotary Effect",        //Rotary
            "Vibrato shows "
    };
    protected static String[] WhoSelectEffect = {
            null,       //None
            null,       //Volume
            null,       //Frequency
            null,       //Reverb
            null,       //Delay
            null,       //Flanger
            null,       //Distortion
            null,       //Rotary
            null        //Vibrato
    };

    protected static int[] EffectImages = {
            R.mipmap.ic_launcher, //None
            R.mipmap.ic_launcher, //Volume
            R.mipmap.ic_launcher, //Frequency
            R.mipmap.ic_launcher, //Reverb
            R.mipmap.ic_launcher, //Delay
            R.mipmap.ic_launcher, //Flanger
            R.mipmap.ic_launcher, //Distortion
            R.mipmap.ic_launcher, //Rotary
            R.mipmap.ic_launcher  //Vibration

    };


    protected int[] SelectedEffects = new int[5];
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
        //Log.d("Look Here", String.valueOf(caller.charAt(0)));

        if(Objects.equals(String.valueOf(caller.charAt(0)), "R")) {
            //Log.d("Look Here", caller);
            SelectedEffects[0] = this.getArguments().getInt("R_fw_selected");
            SelectedEffects[1] = this.getArguments().getInt("R_ud_selected");
            SelectedEffects[2] = this.getArguments().getInt("R_lr_selected");
            SelectedEffects[3] = this.getArguments().getInt("R_pitch_selected");
            SelectedEffects[4] = this.getArguments().getInt("R_roll_selected");
            for(int i = 0 ; i < EffectList.length ; i++){
                WhoSelectEffect[i] = null;
            }
            for(int i = 0 ; i < SelectedEffects.length ; i++){
                for (int j = 0 ; j < EffectList.length ; j++){
                    if(Objects.equals(EffectList[j], getDefinedString(SelectedEffects[i]))){
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
        }
        else if (Objects.equals(String.valueOf(caller.charAt(0)), "L")) {
            SelectedEffects[0] = this.getArguments().getInt("L_fw_selected");
            SelectedEffects[1] = this.getArguments().getInt("L_ud_selected");
            SelectedEffects[2] = this.getArguments().getInt("L_lr_selected");
            SelectedEffects[3] = this.getArguments().getInt("L_pitch_selected");
            SelectedEffects[4] = this.getArguments().getInt("L_roll_selected");

            for(int i = 0 ; i < EffectList.length ; i++){
                WhoSelectEffect[i] = null;
            }
            for(int i = 0 ; i < SelectedEffects.length ; i++){
                for (int j = 0 ; j < EffectList.length ; j++){
                    if(Objects.equals(EffectList[j], getDefinedString(SelectedEffects[i]))){
                        switch (i){
                            case 0:
                                WhoSelectEffect[j] = "L_fw_selected";
                                break;
                            case 1:
                                WhoSelectEffect[j] = "L_ud_selected";
                                break;
                            case 2:
                                WhoSelectEffect[j] = "L_lr_selected";
                                break;
                            case 3:
                                WhoSelectEffect[j] = "L_pitch_selected";
                                break;
                            case 4:
                                WhoSelectEffect[j] = "L_roll_selected";
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                }
            }


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
        ((MainActivity)getActivity()).dialogFragmentItemSelected(caller, position);
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
