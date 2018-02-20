package com.bit.apps.kanadra.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit.apps.kanadra.R;

public class SignUp extends Fragment {

    private View inflate_view;

    public SignUp() {
        // Required empty public constructor
    }

    public static SignUp newInstance(String param1, String param2) {
        SignUp fragment = new SignUp();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate_view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return inflate_view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
