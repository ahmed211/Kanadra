package com.bit.apps.kanadra.Controler.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.FullImage;
import com.bit.apps.kanadra.model.ImageModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 23/04/2016.
 */
public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        TextView title = (TextView) imageLayout.findViewById(R.id.slider_title);

        Glide.with(context).load(imageModelArrayList.get(position).getImage_drawable())
                .override(250, 250).into(imageView);

        title.setText(imageModelArrayList.get(position).getTitle());

        view.addView(imageLayout, 0);

        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context.getClass().getName().equals("com.bit.apps.kanadra.View.Activity.ProjectDetails")){
                    Intent intent = new Intent(context, FullImage.class);
                    intent.putExtra("image", imageModelArrayList.get(position).getImage_drawable());
                    context.startActivity(intent);
                }

            }
        });


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}