package com.bit.apps.kanadra.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bit.apps.kanadra.R;

public class Competition_Registeration extends AppCompatActivity {

    Spinner gender, competitions;

    String[] gender_types = new String[]{"النوع", "ذكر", "انثى"};
    String[] competitions_name = new String[]{"اسم المسابقة", "122", "3252"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition__registeration);

        gender = (Spinner) findViewById(R.id.competitor_gender);
        competitions = (Spinner) findViewById(R.id.competitions);


        ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gender_types);
        gender.setAdapter(gender_adapter);



        ArrayAdapter<String> competitions_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, competitions_name);
        competitions.setAdapter(competitions_adapter);
    }
}
