package com.bit.apps.kanadra.Controler.Adapter;

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
import com.bit.apps.kanadra.model.Card;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Card> card_list;
    private ArrayList<Integer> image;

    public HomeAdapter(Context context, ArrayList<Card> card_list, ArrayList<Integer> image) {
        this.context = context;
        this.card_list = card_list;
        this.image = image;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainhome_card_icon, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Card card = card_list.get(position);

        holder.title.setText(card.getTitle());
        Glide.with(context).load(card.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Gallary.class);
                intent.putExtra("images", image);
                intent.putExtra("pos", position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.text_card);
            image = (ImageView) itemView.findViewById(R.id.card_image);
        }
    }
}
