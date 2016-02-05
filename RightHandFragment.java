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

        Button fw_back_btn   = (Button) view.findViewById(R.id.rh_fw_back_btn);
        Button tilt_btn   = (Button) view.findViewById(R.id.rh_tilt_btn);
        Button turn_btn   = (Button) view.findViewById(R.id.rh_turn_btn);
        Button left_right_btn   = (Button) view.findViewById(R.id.rh_left_right_btn);
        Button up_down_btn   = (Button) view.findViewById(R.id.rh_up_down_btn);
        Button instrument_btn   = (Button) view.findViewById(R.id.rh_instrument_btn);
        //fw_back_btn.setOnClickListener(this);

        fw_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RightHandFragment", "fw_back_btn");
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Forward - Backward");
                fragment.show(fm, "dialog_test_fragment");
            }
        });

        left_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RightHandFragment", "left_right_btn");
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Left - Right");
                fragment.show(fm, "dialog_test_fragment");
            }
        });
        up_down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RightHandFragment", "up_down_btn");
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Up - Down");
                fragment.show(fm, "dialog_test_fragment");
            }
        });
        tilt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RightHandFragment", "tilt_btn");
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Tilt");
                fragment.show(fm, "dialog_test_fragment");
            }
        });
        turn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RightHandFragment", "turn_btn");
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                MainActivity.MyDialogFragment fragment = MainActivity.MyDialogFragment.newInstance("Pitch");
                fragment.show(fm, "dialog_test_fragment");
            }
        });
        instrument_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RightHandFragment", "instrument_btn");
                android.support.v4.app.FragmentManager fm = myContext.getSupportFragmentManager();
                InstrumentDialogFragment fragment = new InstrumentDialogFragment();
                fragment.show(fm, "dialog_instrument_fragment");
            }
        });
        /**********************************************************
         **********************************************************/


        //return inflater.inflate(R.layout.fragment_right_hand, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
