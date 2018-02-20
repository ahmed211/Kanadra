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
import com.bit.apps.kanadra.View.Activity.DiwanDetails;
import com.bit.apps.kanadra.model.Diwan_Model.DiwanItem;
import com.bit.apps.kanadra.model.News_Model.Championships;
import com.bumptech.glide.Glide;

import java.util.List;

public class Diwan_Adapter extends RecyclerView.Adapter<Diwan_Adapter.ViewHolder> {
    private List<DiwanItem> diwanItems;
    private String title, content, image, days, id, lat, lon;
    Context context;

    public Diwan_Adapter(List<DiwanItem> diwanItems, Context context) {
        this.diwanItems = diwanItems;
        this.context = context;
    }

    @Override
    public Diwan_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Diwan_Adapter.ViewHolder holder, final int position) {

        title = diwanItems.get(position).getTitle();
        content = diwanItems.get(position).getContent();
        image = context.getString(R.string.baseUrl) + diwanItems.get(position).getThumbnail();
        holder.title.setText(diwanItems.get(position).getTitle());
        holder.date.setText(diwanItems.get(position).getDays());
        Glide.with(context).load(image).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiwanDetails.class);
                intent.putExtra("title", diwanItems.get(position).getTitle());
                intent.putExtra("content", diwanItems.get(position).getContent());
                intent.putExtra("image", context.getString(R.string.baseUrl) + diwanItems.get(position).getThumbnail());
                intent.putExtra("days","الايام : " + diwanItems.get(position).getDays() );
                intent.putExtra("lat", diwanItems.get(position).getLat());
                intent.putExtra("lng", diwanItems.get(position).getLon());


                context.startActivity(intent);
            }
        });
//
    }


    @Override
    public int getItemCount() {
        return diwanItems.size();
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