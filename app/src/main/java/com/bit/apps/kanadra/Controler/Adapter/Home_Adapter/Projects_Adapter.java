package com.bit.apps.kanadra.Controler.Adapter.Home_Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.Championship_Details;
import com.bit.apps.kanadra.View.Activity.ProjectDetails;
import com.bit.apps.kanadra.model.News_Model.Championships;
import com.bit.apps.kanadra.model.ProjectsModel.Images;
import com.bit.apps.kanadra.model.ProjectsModel.ProjectsData;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Projects_Adapter extends RecyclerView.Adapter<Projects_Adapter.ViewHolder> {
    private List<ProjectsData> projectsData;
    private ArrayList<String> imageList;
    private String title, phone, website, image;
    Context context;

    public Projects_Adapter(List<ProjectsData> projectsData, Context context) {
        this.projectsData = projectsData;
        this.context = context;
    }

    @Override
    public Projects_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_card_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Projects_Adapter.ViewHolder holder, final int position) {

        title = projectsData.get(position).getTitle();
        phone = projectsData.get(position).getPhone();
        image = context.getString(R.string.baseUrl) + projectsData.get(position).getImages().get(0).getPath();
        //views = news_Data.get(position).getViews();
        website = projectsData.get(position).getWebsite();


        holder.title.setText(projectsData.get(position).getTitle());
       // holder.date.setText(projectsData.get(position).getDate());
        Glide.with(context).load(image).override(150,150).into(holder.image);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);

                callIntent.setData(Uri.parse("tel:" + projectsData.get(position).getPhone()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);

            }
        });

        holder.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                context.startActivity(browserIntent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int imageSize =  projectsData.get(position).getImages().size();
                getImageList(imageSize, position);
                Intent intent = new Intent(context, ProjectDetails.class);
                intent.putExtra("title", projectsData.get(position).getTitle());
                intent.putExtra("content", projectsData.get(position).getContent());
                intent.putExtra("image", imageList);
                intent.putExtra("lat", projectsData.get(position).getLat());
                intent.putExtra("lng", projectsData.get(position).getLon());
                intent.putExtra("phone", projectsData.get(position).getPhone());
                intent.putExtra("website", projectsData.get(position).getWebsite());
                intent.putExtra("facebook", projectsData.get(position).getFacebook());
                intent.putExtra("twitter", projectsData.get(position).getTwitter());
                intent.putExtra("instagram", projectsData.get(position).getInstagram());


                context.startActivity(intent);
            }
        });

    }

    private void getImageList(int imageSize, int pos) {
        imageList = new ArrayList<>();
        for (int i=0; i<imageSize; i++)
            imageList.add(context.getString(R.string.baseUrl) +projectsData.get(pos).getImages().get(i).getPath());
    }


    @Override
    public int getItemCount() {
        return projectsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, date;
        ImageView image, call, website;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_title);
            date = (TextView) itemView.findViewById(R.id.card_views);
            image = (ImageView) itemView.findViewById(R.id.card_image);
            call = (ImageView) itemView.findViewById(R.id.call);
            website = (ImageView) itemView.findViewById(R.id.website);
        }
    }
}