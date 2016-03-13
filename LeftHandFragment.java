package complexability.motionmusicv2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeftHandFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeftHandFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //***********************************************************************
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //***********************************************************************

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

    private FragmentActivity myContext;


    TextView l_fwBack_txt;
    TextView l_leftRight_txt;
    TextView l_upDown_txt;
    TextView l_pitch_txt;
    TextView l_roll_txt;
    public LeftHandFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeftHandFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeftHandFragment newInstance(String param1, String param2) {
        LeftHandFragment fragment = new LeftHandFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Blah",1);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            Log.d("LeftHand", "savedinstantstate");
            Bundle bundle = new Bundle();
            bundle.putString("caller","saved");

            bundle.putInt("L_pitch_selected", MainActivity.leftHand.getEffect(0));
            bundle.putInt("L_roll_selected", MainActivity.leftHand.getEffect(1));
            bundle.putInt("L_fw_selected", MainActivity.leftHand.getEffect(2));
            bundle.putInt("L_ud_selected", MainActivity.leftHand.getEffect(3));
            bundle.putInt("L_lr_selected", MainActivity.leftHand.getEffect(4));
            bundle.putInt("L_instrument", MainActivity.leftHand.getInstrument());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_hand, container, false);
        /**
         * Button handler
         */

        Button fw_back_btn      = (Button) view.findViewById(R.id.lh_fw_back_btn);
        Button pitch_btn         = (Button) view.findViewById(R.id.lh_pitch_btn);
        Button roll_btn         = (Button) view.findViewById(R.id.lh_roll_btn);
        Button left_right_btn   = (Button) view.findViewById(R.id.lh_left_right_btn);
        Button up_down_btn      = (Button) view.findViewById(R.id.lh_up_down_btn);
        //Button instr_button     = (Button) view.findViewById(R.id.left_instrument);
        /**
         * Text Views
         */
        TextView fwBack_txt     = (TextView) view.findViewById(R.id.l_fwBackText);
        TextView leftRight_txt  = (TextView) view.findViewById(R.id.l_leftRightText);
        TextView upDown_txt     = (TextView) view.findViewById(R.id.l_upDownText);
        TextView pitch_txt      = (TextView) view.findViewById(R.id.l_pitchText);
        TextView roll_txt       = (TextView) view.findViewById(R.id.l_rollText);
        //TextView inst_txt       = (TextView) view.findViewById(R.id.leftInstrumentTxt);

        pitch_txt.setText(getDefinedString(MainActivity.leftHand.getEffect(0)));
        roll_txt.setText(getDefinedString(MainActivity.leftHand.getEffect(1)));
        fwBack_txt.setText(getDefinedString(MainActivity.leftHand.getEffect(2)));
        upDown_txt.setText(getDefinedString(MainActivity.leftHand.getEffect(3)));
        leftRight_txt.setText(getDefinedString(MainActivity.leftHand.getEffect(4)));
        //inst_txt.setText(getDefinedString(MainActivity.leftHand.getInstrument()));
        //fw_back_btn.setOnClickListener(this);


        fw_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle to pass arguments to the dialogFragment
                Bundle bundle = setBundle("L_fb");
                //String caller = "R_fb";
                //bundle.putString("caller",caller);
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                //MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Forward - Backward");
                EffectDialogFragment  fragment= new EffectDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(fm, "dialog_effect_fragment");
            }
        });

        left_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = setBundle("L_lr");
                //String caller = "R_lr";
                //bundle.putString("caller",caller);
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                //MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Left - Right");
                EffectDialogFragment  fragment= new EffectDialogFragment();
                fragment.setArguments(bundle);

                fragment.show(fm, "dialog_effect_fragment");
            }
        });
        up_down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = setBundle("L_ud");
                //String caller = "R_ud";
                //bundle.putString("caller",caller);
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                //MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Up - Down");
                EffectDialogFragment  fragment= new EffectDialogFragment();
                fragment.setArguments(bundle);

                fragment.show(fm, "dialog_effect_fragment");
            }
        });
        pitch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = setBundle("L_pitch");
                //String caller = "R_pitch";
                //bundle.putString("caller",caller);
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                //MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Tilt");
                EffectDialogFragment  fragment= new EffectDialogFragment();
                fragment.setArguments(bundle);

                fragment.show(fm, "dialog_effect_fragment");
            }
        });
        roll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = setBundle("L_roll");
                //String caller = "R_roll";
                //bundle.putString("caller",caller);
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                //MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Pitch");
                EffectDialogFragment  fragment= new EffectDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(fm, "dialog_effect_fragment");
            }
        });
        /*
        instr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("RightHandFragment", "instrument_btn");
                Bundle bundle = new Bundle();
                //bundle.putString("previous",getDefinedString(MainActivity.rightHand.getInstrument()));
                bundle.putString("caller", "l_instrument");
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                InstrumentDialogFragment fragment = new InstrumentDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(fm, "dialog_instrument_fragment");
            }
        });
        */
        /**********************************************************
         **********************************************************/


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public Bundle setBundle(String caller){
        Bundle bundle = new Bundle();

        bundle.putString("caller",caller);

        bundle.putInt("L_pitch_selected", MainActivity.leftHand.getEffect(0));
        bundle.putInt("L_roll_selected", MainActivity.leftHand.getEffect(1));
        bundle.putInt("L_fw_selected", MainActivity.leftHand.getEffect(2));
        bundle.putInt("L_ud_selected", MainActivity.leftHand.getEffect(3));
        bundle.putInt("L_lr_selected", MainActivity.leftHand.getEffect(4));
        bundle.putInt("L_instrument", MainActivity.leftHand.getInstrument());
        return bundle;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
    public void testSetText(int val, int id){
        switch (id){
            case 0:

                break;
            case 1:
                break;
            default:
                break;
        }
    }
}
