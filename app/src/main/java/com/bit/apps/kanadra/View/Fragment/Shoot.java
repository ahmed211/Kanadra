package com.bit.apps.kanadra.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.bit.apps.kanadra.Controler.Adapter.RecyclerViewAdapter;
import com.bit.apps.kanadra.Controler.EqualSpacingItemDecoration;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.Card;

public class Shoot extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerViewAdapter adapter;
    private ArrayList<Card> card_list;
    private ArrayList<Integer> image;
    private View inflate_view;


    public Shoot() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Shoot newInstance() {
        Shoot fragment = new Shoot();
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
        inflate_view = inflater.inflate(R.layout.fragment_shoot, container, false);

        card_list = new ArrayList<>();
        image = new ArrayList<>();
        //adapter =new RecyclerViewAdapter(getActivity(), card_list);

        LoadImages();
        recyclerView = (RecyclerView) inflate_view.findViewById(R.id.recyclerview_shoot);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(20));

        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        //recyclerView.setAdapter(adapter);


        return inflate_view;
    }


    private void LoadImages() {
        for (int i = 0; i < 100; i++) {
            image.add(R.drawable.logo);
            Card card = new Card("الكندري للرماية", image.get(i));
            card_list.add(card);
        }
    }


}
