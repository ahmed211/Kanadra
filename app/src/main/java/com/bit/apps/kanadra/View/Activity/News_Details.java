package com.bit.apps.kanadra.View.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class News_Details extends AppCompatActivity {

    private String title, content, image, views, date, id;
    private TextView title_show, content_show, views_show, date_show;
    private ImageView image_show;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__details);

        intialization();
        getData();
        showData();
    }



    private void intialization() {
        title_show = (TextView) findViewById(R.id.detail_title);
        content_show = (TextView) findViewById(R.id.detail_content);
        views_show = (TextView) findViewById(R.id.detail_views);
        date_show = (TextView) findViewById(R.id.detail_date);
        image_show = (ImageView) findViewById(R.id.detail_image);
        bundle = getIntent().getExtras();
    }

    private void getData() {
        title = bundle.getString("title");
        content = bundle.getString("content");
        image = bundle.getString("image");
        views = bundle.getString("views");
        date = bundle.getString("date");
        id = bundle.getString("id");

    }

    private void showData() {
        title_show.setText(title);
        content_show.setText(Html.fromHtml(content));
        views_show.setText(views );
        date_show.setText(date);
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(image_show);
        Log.v("q1q pos ", image + "");
        Log.v("q1q pos ", id + "");

    }


    public void share(View view) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "http://alkanadra.online/index.php?r=news%2Fview&id=" + id;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
