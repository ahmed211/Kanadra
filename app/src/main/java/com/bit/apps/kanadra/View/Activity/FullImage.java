package com.bit.apps.kanadra.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bit.apps.kanadra.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class FullImage extends AppCompatActivity {

    private ImageView fullImage;
    private Bundle bundle;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_full_image);



        bundle = getIntent().getExtras();
        fullImage = findViewById(R.id.full_image);

        image = bundle.getString("image");
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(fullImage);

    }
}
