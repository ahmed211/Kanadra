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
import com.bit.apps.kanadra.View.Activity.News_Details;
import com.bit.apps.kanadra.model.Home_Model.News;
import com.bit.apps.kanadra.model.News_Model.Item;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ahmed on 1/16/2018.
 */

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.ViewHolder> {
    private List <Item> news_Data;
    Context context;
    private String title, content, image, views, date, id;

    public News_Adapter(List<Item> news_Data, Context context) {
        this.news_Data = news_Data;
        this.context = context;
    }

    @Override
    public News_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(News_Adapter.ViewHolder holder, final int position) {

        title = news_Data.get(position).getTitle();
        content = news_Data.get(position).getContent();
        image = context.getString(R.string.baseUrl) + news_Data.get(position).getThumbnail();
        views = news_Data.get(position).getViews();
        date = news_Data.get(position).getTimestamp();
        date = date.substring(0, 10);
        id = news_Data.get(position).getId();

        holder.title_show.setText(title);
        holder.views_show.setText(date);
        Glide.with(context).load(image).into(holder.image_show);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, News_Details.class);
                intent.putExtra("title", news_Data.get(position).getTitle());
                intent.putExtra("content", news_Data.get(position).getContent());
                intent.putExtra("image", context.getString(R.string.baseUrl) + news_Data.get(position).getThumbnail());
                if(news_Data.get(position).getViews() != null)
                    intent.putExtra("views", news_Data.get(position).getViews() + " views");
                intent.putExtra("date", news_Data.get(position).getTimestamp());
                intent.putExtra("id", news_Data.get(position).getId());

                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return news_Data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title_show, views_show;
        ImageView image_show;

        public ViewHolder(View itemView) {
            super(itemView);
            title_show = (TextView) itemView.findViewById(R.id.card_title);
            views_show = (TextView) itemView.findViewById(R.id.card_views);
            image_show = (ImageView) itemView.findViewById(R.id.card_image);
        }
    }
}