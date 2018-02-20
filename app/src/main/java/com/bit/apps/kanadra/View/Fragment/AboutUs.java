package com.bit.apps.kanadra.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AboutUs extends Fragment {

    View view;
    private TextView about;


    public AboutUs() {
        // Required empty public constructor
    }

    public static AboutUs newInstance() {
        AboutUs fragment = new AboutUs();
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
        view =  inflater.inflate(R.layout.fragment_aboutus, container, false);
        about = (TextView) view.findViewById(R.id.text_about);

        getdata();



        return view;
    }

    private void getdata() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ClientAPI api = retrofit.create(ClientAPI.class);

        Call<com.bit.apps.kanadra.model.AboutUs> aboutUsCall = api.getAbout();

        aboutUsCall.enqueue(new Callback<com.bit.apps.kanadra.model.AboutUs>() {
            @Override
            public void onResponse(Call<com.bit.apps.kanadra.model.AboutUs> call, Response<com.bit.apps.kanadra.model.AboutUs> response) {
                String message = response.body().getContent();
                about.setText(message);
               // Log.v("q1q", response.body().getContent());
            }

            @Override
            public void onFailure(Call<com.bit.apps.kanadra.model.AboutUs> call, Throwable t) {
                Log.v("q1q", t.toString());
            }
        });
    }
}
