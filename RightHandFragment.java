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
 * {@link RightHandFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RightHandFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightHandFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentActivity myContext;

    private OnFragmentInteractionListener mListener;

    /**
     * Csound parameter handlers
     */

    TextView instr_txt;
    TextView r_fwBack_txt;
    TextView r_leftRight_txt;
    TextView r_upDown_txt;
    TextView r_pitch_txt;
    TextView r_roll_txt;

    //All the buttons
    /*
    protected Button fw_back_btn
    protected Button tilt_btn
    protected Button turn_btn
    protected Button left_right_btn
    protected Button up_down_btn
    protected Button instrument_btn
    protected Button asd_btn
    */
    public RightHandFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RightHandFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RightHandFragment newInstance(String param1, String param2) {
        RightHandFragment fragment = new RightHandFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_right_hand, container, false);
        /**
         * Button handler
         */

        Button fw_back_btn      = (Button) view.findViewById(R.id.rh_fw_back_btn);
        Button pitch_btn         = (Button) view.findViewById(R.id.rh_pitch_btn);
        Button roll_btn         = (Button) view.findViewById(R.id.rh_roll_btn);
        Button left_right_btn   = (Button) view.findViewById(R.id.rh_left_right_btn);
        Button up_down_btn      = (Button) view.findViewById(R.id.rh_up_down_btn);
        Button instrument_btn   = (Button) view.findViewById(R.id.rh_instrument_btn);
        /**
         * Text Views
         */
        TextView instr_txt      = (TextView) view.findViewById(R.id.instText);
        TextView fwBack_txt     = (TextView) view.findViewById(R.id.r_fwBackText);
        TextView leftRight_txt  = (TextView) view.findViewById(R.id.r_leftRightText);
        TextView upDown_txt     = (TextView) view.findViewById(R.id.r_upDownText);
        TextView pitch_txt      = (TextView) view.findViewById(R.id.r_pitchText);
        TextView roll_txt       = (TextView) view.findViewById(R.id.r_rollText);

        instr_txt.setText(getDefinedString(MainActivity.rightHand.getInstrument()));
        pitch_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(0)));
        roll_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(1)));
        fwBack_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(2)));
        upDown_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(3)));
        leftRight_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(4)));

        //fw_back_btn.setOnClickListener(this);


        fw_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("RightHandFragment", "fw_back_btn");
                //Bundle to pass arguments to the dialogFragment
                Bundle bundle = setBundle("R_fb");
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
                //Log.d("RightHandFragment", "left_right_btn");
                Bundle bundle = setBundle("R_lr");
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
                //Log.d("RightHandFragment", "up_down_btn");
                Bundle bundle = setBundle("R_ud");
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
                //Log.d("RightHandFragment", "tilt_btn");
                Bundle bundle = setBundle("R_pitch");
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
                //Log.d("RightHandFragment", "turn_btn");
                Bundle bundle = setBundle("R_roll");
                //String caller = "R_roll";
                //bundle.putString("caller",caller);
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                //MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Pitch");
                EffectDialogFragment  fragment= new EffectDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(fm, "dialog_effect_fragment");
            }
        });
        instrument_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("RightHandFragment", "instrument_btn");
                Bundle bundle = new Bundle();
                String caller = "R_inst";
                bundle.putString("caller",caller);
                bundle.putString("previous",getDefinedString(MainActivity.rightHand.getInstrument()));
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                InstrumentDialogFragment fragment = new InstrumentDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(fm, "dialog_instrument_fragment");
            }
        });
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

        bundle.putInt("R_pitch_selected", MainActivity.rightHand.getEffect(0));
        bundle.putInt("R_roll_selected", MainActivity.rightHand.getEffect(1));
        bundle.putInt("R_fw_selected", MainActivity.rightHand.getEffect(2));
        bundle.putInt("R_ud_selected", MainActivity.rightHand.getEffect(3));
        bundle.putInt("R_lr_selected", MainActivity.rightHand.getEffect(4));

        return bundle;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext=(FragmentActivity) context;

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
    public void setAllText(){
        instr_txt.setText(getDefinedString(MainActivity.rightHand.getInstrument()));


        r_pitch_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(0)));
        r_roll_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(1)));
        r_fwBack_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(2)));
        r_upDown_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(3)));
        r_leftRight_txt.setText(getDefinedString(MainActivity.rightHand.getEffect(4)));
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
