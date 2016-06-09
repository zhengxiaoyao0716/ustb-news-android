package com.zheng0716.ustbnews.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Fragment管理.
 * Created by zhengxiaoyao0716 on 2016/4/10.
 */
public class FragmentManager {
    public static void replace(AppCompatActivity activity, @IdRes int id, Fragment fragment) {
        android.support.v4.app.FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // DO: Update argument type and name
        void onFragmentInteraction();

        AppCompatActivity getActivity();
    }
}