package com.bit.apps.kanadra.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.AddProject;
import com.bit.apps.kanadra.View.Activity.Add_Occasion;
import com.bit.apps.kanadra.View.Activity.Add_news;
import com.bit.apps.kanadra.View.Activity.Diwan;
import com.bit.apps.kanadra.model.Home_Model.News;


/**
 * A simple {@link Fragment} subclass.
 */
public class ADD extends Fragment {

    View inflate_view;
    Button news, occasion, diwan, project;
    private Intent intent;


    public ADD() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate_view =  inflater.inflate(R.layout.fragment_add, container, false);

        intialization();

        click();



        return inflate_view;
    }


    private void intialization() {
        news = (Button) inflate_view.findViewById(R.id.news);
        occasion = (Button) inflate_view.findViewById(R.id.occasion);
        diwan = (Button) inflate_view.findViewById(R.id.diwan);
        project = (Button) inflate_view.findViewById(R.id.project);
    }

    private void click() {
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Add_news.class);
                startActivity(intent);

            }
        });

        occasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Add_Occasion.class);
                startActivity(intent);
            }
        });

        diwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Diwan.class);
                startActivity(intent);

            }
        });

        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), AddProject.class);
                startActivity(intent);
            }
        });
    }
}
