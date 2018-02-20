package com.bit.apps.kanadra.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DiwanDetails extends AppCompatActivity {

    private LatLng location;
    private GoogleMap map;
    private Bundle bundle;
    private String content, title, lat, lng, days, image;
    private TextView content_view, title_view, days_view;
    private ImageView image_view;

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diwan_details);

        initialization();

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
        mapView = (MapView) findViewById(R.id.map);
        bundle = getIntent().getExtras();
        image_view = (ImageView) findViewById(R.id.dwan_image);
        content_view = (TextView) findViewById(R.id.dwan_content);
        title_view = (TextView) findViewById(R.id.dwan_title);
        days_view = (TextView) findViewById(R.id.days);

        title = bundle.getString("title");
        content = bundle.getString("content");
        lat = bundle.getString("lat");
        lng = bundle.getString("lng");
        image = bundle.getString("image");
        days = bundle.getString("days");

        title_view.setText(title);
        content_view.setText(content);
        days_view.setText(days);
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(image_view);

        location = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
    }


}
