package com.bit.apps.kanadra.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import com.bit.apps.kanadra.Controler.Adapter.Gallery_Adapter.GalleryAdapter;
import com.bit.apps.kanadra.Controler.Adapter.Gallery_Adapter.VideoAdapter;
import com.bit.apps.kanadra.Controler.Adapter.RecyclerViewAdapter;
import com.bit.apps.kanadra.Controler.EqualSpacingItemDecoration;
import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.Gallery_Model.Gallery;
import com.bit.apps.kanadra.model.Gallery_Model.PojoGallery;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KanadraGallery extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerViewAdapter adapter;
    private View inflate_view;
    private List <Gallery> galleries, videos;
    private ArrayList <String> imagesPath, videoPath;
    private Button videoBtn, imageBtn;
    private int flag = 1;

    private GalleryAdapter galleryAdapter;
    private VideoAdapter videoAdapter;

    public KanadraGallery() {
        // Required empty public constructor
    }

    public static KanadraGallery newInstance() {
        KanadraGallery fragment = new KanadraGallery();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate_view = inflater.inflate(R.layout.fragment_kanadra_gallery, container, false);

        initialization();

        getImages();

        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoAdapter = new VideoAdapter(getActivity(), videos, videoPath);
                recyclerView.setAdapter(videoAdapter);
                imageBtn.setTextColor(getResources().getColor(R.color.text));
                imageBtn.setBackgroundColor(getResources().getColor(R.color.offwit));
                videoBtn.setTextColor(getResources().getColor(R.color.offwit));
                videoBtn.setBackgroundColor(getResources().getColor(R.color.text));

            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryAdapter = new GalleryAdapter(getActivity(), galleries, imagesPath);
                recyclerView.setAdapter(galleryAdapter);

                imageBtn.setTextColor(getResources().getColor(R.color.offwit));
                imageBtn.setBackgroundColor(getResources().getColor(R.color.text));
                videoBtn.setTextColor(getResources().getColor(R.color.text));
                videoBtn.setBackgroundColor(getResources().getColor(R.color.offwit));


            }
        });
//        LoadImages();


        return inflate_view;
    }

    private void getImages() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<PojoGallery> myPojoCall = api.getGallery();

        myPojoCall.enqueue(new Callback<PojoGallery>() {
            @Override
            public void onResponse(Call<PojoGallery> call, Response<PojoGallery> response) {
                galleries = response.body().getImages();
                videos = response.body().getVideos();
                loadImages();
                galleryAdapter = new GalleryAdapter(getActivity(), galleries, imagesPath);
                recyclerView.setAdapter(galleryAdapter);
            }

            @Override
            public void onFailure(Call<PojoGallery> call, Throwable t) {
                Log.v("q1q", t.toString());

            }
        });

    }

    private void initialization() {
        imagesPath = new ArrayList<>();
        videoPath = new ArrayList<>();
        recyclerView = (RecyclerView) inflate_view.findViewById(R.id.recyclerview_family);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(20));
        manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        videoBtn = (Button) inflate_view.findViewById(R.id.videos);
        imageBtn = (Button) inflate_view.findViewById(R.id.images);

        imageBtn.setTextColor(getResources().getColor(R.color.offwit));
        imageBtn.setBackgroundColor(getResources().getColor(R.color.text));
    }

    private void loadImages() {
        for(int i=0; i<galleries.size(); i++){
            imagesPath.add(getString(R.string.baseUrl) + galleries.get(i).getPath());
        }
        for(int i=0; i<videos.size(); i++){
            videoPath.add( videos.get(i).getPath());
        }
    }

}
