package com.zheng0716.ustbnews.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.zheng0716.ustbnews.R;


public class NewFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onButtonPressed();
                return false;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private FragmentManager.OnFragmentInteractionListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentManager.OnFragmentInteractionListener) {
            mListener = (FragmentManager.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // DO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
