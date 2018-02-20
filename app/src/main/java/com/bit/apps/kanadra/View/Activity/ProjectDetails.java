package com.bit.apps.kanadra.View.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.apps.kanadra.Controler.Adapter.SlidingImage_Adapter;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.ImageModel;
import com.bit.apps.kanadra.model.ProjectsModel.Images;
import com.bit.apps.kanadra.model.ProjectsModel.ProjectsData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProjectDetails extends AppCompatActivity {

    private ProjectsData data;
    private String title, content, phone, website, facebook, twitter, instagram;
    private TextView title_view, content_view;
    private ImageView phone_view, website_view;
    private Bundle bundle;
    private double lat, lng;
    private ArrayList<String> image;
    private MapView mapView;
    private GoogleMap map;
    private LatLng location;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        initialization();

        initmap(savedInstanceState);

        slider();
    }


    private void initmap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.addMarker(new MarkerOptions().position(location).title(title));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

            }
        });

    }


    private void initialization() {
        title_view = (TextView) findViewById(R.id.project_title);
        content_view = (TextView) findViewById(R.id.project_content);
        phone_view = (ImageView) findViewById(R.id.call);
        website_view = (ImageView) findViewById(R.id.website);
        bundle = getIntent().getExtras();

        imageModelArrayList = new ArrayList<>();

        title = bundle.getString("title");
        content = bundle.getString("content");
        phone = bundle.getString("phone");
        website = bundle.getString("website");
        facebook = bundle.getString("facebook");
        twitter = bundle.getString("twitter");
        instagram = bundle.getString("instagram");
        lat = Double.parseDouble(bundle.getString("lat"));
        lng = Double.parseDouble(bundle.getString("lng"));
        image = bundle.getStringArrayList("image");


        title_view.setText(title);
        content_view.setText(Html.fromHtml(content));
        location = new LatLng(lat, lng);
        mapView = (MapView) findViewById(R.id.map);


    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(this, imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(3 * density);

        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < image.size(); i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(image.get(i));
            list.add(imageModel);
        }

        return list;
    }

    private void slider() {
        imageModelArrayList = populateList();

        init();
    }


    public void openWebsite(View view) {
        openURL(website);
    }
    public void openInstagram(View view) {
        if(instagram == null)
            Toast.makeText(this, "لا يوجد انستجرام", Toast.LENGTH_SHORT).show();
        else
            openURL(instagram);
    }

    public void openTwitter(View view) {
        if(instagram == null)
            Toast.makeText(this, "لا يوجد تويتر", Toast.LENGTH_SHORT).show();
        else
            openURL(twitter);
    }

    public void openFacebook(View view) {
        if(instagram == null)
            Toast.makeText(this, "لا يوجد فيسبوك", Toast.LENGTH_SHORT).show();
        else
            openURL(facebook);
    }


    public void openURL(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void makeCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);

        callIntent.setData(Uri.parse("tel:" + bundle.getString(phone)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

}
