package com.bit.apps.kanadra.Controler.Adapter.Home_Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.Championship_Details;
import com.bit.apps.kanadra.model.News_Model.Championships;
import com.bumptech.glide.Glide;

import java.util.List;

public class Championship_Adapter extends RecyclerView.Adapter<Championship_Adapter.ViewHolder> {
    private List<Championships> champion_Data;
    private String title, content, image, views, date, id;
    Context context;

    public Championship_Adapter(List<Championships> champion_Data, Context context) {
        this.champion_Data = champion_Data;
        this.context = context;
    }

    @Override
    public Championship_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Championship_Adapter.ViewHolder holder, final int position) {

        title = champion_Data.get(position).getName();
        content = champion_Data.get(position).getNote();
        image = context.getString(R.string.baseUrl) + champion_Data.get(position).getMedia();
        //views = news_Data.get(position).getViews();
        date = champion_Data.get(position).getDate();
        id = champion_Data.get(position).getId();


        holder.title.setText(champion_Data.get(position).getName());
        holder.date.setText(champion_Data.get(position).getDate());
        Glide.with(context).load(image).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Championship_Details.class);
                intent.putExtra("title", champion_Data.get(position).getName());
                intent.putExtra("content", champion_Data.get(position).getNote());
                intent.putExtra("image", context.getString(R.string.baseUrl) + champion_Data.get(position).getMedia());
                intent.putExtra("views", "");
                intent.putExtra("date", champion_Data.get(position).getDate());
                intent.putExtra("id", champion_Data.get(position).getId());


                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return champion_Data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, date;
        ImageView image, icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_title);
            date = (TextView) itemView.findViewById(R.id.card_views);
            image = (ImageView) itemView.findViewById(R.id.card_image);
        }
    }
}