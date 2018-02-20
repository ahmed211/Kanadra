package com.bit.apps.kanadra.View.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bit.apps.kanadra.Controler.Adapter.ViewPagerAdapter;
import com.bit.apps.kanadra.R;

import java.util.ArrayList;
import java.util.List;

public class Gallary extends AppCompatActivity {

    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    private ArrayList<String> imagesPath;
    private Bundle bundle;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);

        imagesPath = new ArrayList<>();
        bundle = getIntent().getExtras();

        pos = bundle.getInt("pos");
        imagesPath = bundle.getStringArrayList("images");
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ViewPagerAdapter(this, imagesPath);

        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(pos);

    }
}
