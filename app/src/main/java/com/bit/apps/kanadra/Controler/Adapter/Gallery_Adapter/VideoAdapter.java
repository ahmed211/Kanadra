package com.bit.apps.kanadra.Controler.Adapter.Gallery_Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.Gallary;
import com.bit.apps.kanadra.View.Activity.PlayVideo;
import com.bit.apps.kanadra.model.Gallery_Model.Gallery;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ahmed on 2/17/2018.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<Gallery> items;
    private String image;
    private ArrayList<String> imagesPath;
    public VideoAdapter(Context context, List <Gallery> items, ArrayList <String> imagesPath) {
        this.context = context;
        this.items = items;
        this.imagesPath = imagesPath;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.title.setText(items.get(position).getTitle());
        holder.image.setImageResource(R.drawable.logo);
       // Glide.with(context).load(image).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = items.get(position).getPath();
                key = key.substring(32, key.length());
                Intent intent = new Intent(context, PlayVideo.class);
                intent.putExtra("key", key);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.text_card);
            image = (ImageView) itemView.findViewById(R.id.image_card);
        }
    }


}
