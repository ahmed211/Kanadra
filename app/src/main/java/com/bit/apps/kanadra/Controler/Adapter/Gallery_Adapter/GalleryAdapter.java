package com.bit.apps.kanadra.Controler.Adapter.Gallery_Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.Gallary;
import com.bit.apps.kanadra.model.Gallery_Model.Gallery;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 2/3/2018.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private List <Gallery> items;
    private String image;
    private ArrayList <String> imagesPath;
    public GalleryAdapter(Context context, List <Gallery> items, ArrayList <String> imagesPath) {
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

        image = context.getString(R.string.baseUrl) + items.get(position).getPath();

        holder.title.setText(items.get(position).getTitle());
        Glide.with(context).load(image).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Gallary.class);
                intent.putExtra("images", imagesPath);
                intent.putExtra("pos", position);
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

    private void getPath(){
        for(int i=0; i<items.size(); i++)
        imagesPath.add(items.get(i).getPath());
    }

}
