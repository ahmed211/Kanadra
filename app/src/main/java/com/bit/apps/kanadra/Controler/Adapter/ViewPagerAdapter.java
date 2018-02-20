package com.bit.apps.kanadra.Controler.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bit.apps.kanadra.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 2/3/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList <String> images;
    private FloatingActionButton share;
    private View inflateView;

    public  ViewPagerAdapter(Context context, ArrayList <String> images)
    {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflateView = inflater.inflate(R.layout.custom_image, null);
        final ImageView imageView = (ImageView) inflateView.findViewById(R.id.image_pager);
        share = (FloatingActionButton) inflateView.findViewById(R.id.share);

//        Glide.with(context).load(images.get(position)).into(imageView);
        Picasso.with(context).load(images.get(position)).into(imageView);

        ViewPager pager = (ViewPager) container;
        pager.addView(inflateView, 0);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareItem(inflateView, imageView);
            }
        });


        return  inflateView;
    }
    public void share(int position) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = Integer.toString(position);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    // Can be triggered by a view event such as a button press
    public void onShareItem(View v, ImageView imageView) {
        // Get access to bitmap image from view
        //ImageView ivImage = (ImageView) v.findViewById(R.id.image_pager);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(imageView);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(context, "please make permission for storage", Toast.LENGTH_SHORT).show();
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager pager = (ViewPager) container;
        View view = (View) object;
        pager.removeView(view);
    }
}



