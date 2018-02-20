package com.bit.apps.kanadra.Controler.Adapter.Home_Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.News_Details;
import com.bit.apps.kanadra.model.Home_Model.Events;
import com.bit.apps.kanadra.model.Home_Model.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class Events_Adapter extends RecyclerView.Adapter<Events_Adapter.ViewHolder> {
    private List<Events> events_Data;
    private List<News> news_Data;
    Context context;
    private String title, content, image, views, date, id;


    public Events_Adapter(List<Events> events_Data, Context context, List<News> news_Data) {
        this.events_Data = events_Data;
        this.context = context;
        this.news_Data = news_Data;
    }

    @Override
    public Events_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainhome_card_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Events_Adapter.ViewHolder holder, final int position) {


        if(position < news_Data.size())
            getNews(position, holder);
        else
            getEvents(position-news_Data.size(), holder);

//        holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.calendar) );


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, News_Details.class);
                if(position < news_Data.size()){
                    intent.putExtra("title", news_Data.get(position).getTitle());
                    intent.putExtra("content", news_Data.get(position).getContent());
                    intent.putExtra("image", context.getString(R.string.baseUrl) + news_Data.get(position).getThumbnail());
                    intent.putExtra("views", news_Data.get(position).getViews() + " views");
                    intent.putExtra("date", news_Data.get(position).getTimestamp());
                    intent.putExtra("id", news_Data.get(position).getId());

                }

                else {

                    intent.putExtra("title", events_Data.get(position-news_Data.size()).getTitle());
                    intent.putExtra("content", events_Data.get(position-news_Data.size()).getContent());
                    intent.putExtra("image", context.getString(R.string.baseUrl) + events_Data.get(position-news_Data.size()).getThumbnail());
                    intent.putExtra("views", "");
                    intent.putExtra("date", events_Data.get(position-news_Data.size()).getDate());
                    intent.putExtra("id", events_Data.get(position-news_Data.size()).getId());

                }
                context.startActivity(intent);
            }
        });

    }

    private void getEvents(int position, Events_Adapter.ViewHolder holder) {
        title = events_Data.get(position).getTitle();
        content = events_Data.get(position).getContent();
        content = content.substring(0, 80) + "........";
        image = context.getString(R.string.baseUrl) + events_Data.get(position).getThumbnail();
        //views = news_Data.get(position).;
        date = events_Data.get(position).getDate();
        id = events_Data.get(position).getId();


        holder.title.setText(events_Data.get(position).getTitle());
        holder.content.setText(Html.fromHtml(content));
        Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);

    }

    private void getNews(int position, Events_Adapter.ViewHolder holder) {
        title = news_Data.get(position).getTitle();
        content = news_Data.get(position).getContent();
        content = content.substring(0, 80) + "  ........";
        image = context.getString(R.string.baseUrl) + news_Data.get(position).getThumbnail();
        views = news_Data.get(position).getViews();
        date = news_Data.get(position).getTimestamp();
        date = date.substring(0, 10);
        id = news_Data.get(position).getId();

        holder.title.setText(title);
        holder.content.setText(Html.fromHtml(content));
        Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);

    }


    @Override
    public int getItemCount() {
        int size = events_Data.size() + news_Data.size();
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, content;
        ImageView image, icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_title);
            content = (TextView) itemView.findViewById(R.id.content);
            image = (ImageView) itemView.findViewById(R.id.card_image);
//            icon = (ImageView) itemView.findViewById(R.id.card_icon);

        }
    }
}