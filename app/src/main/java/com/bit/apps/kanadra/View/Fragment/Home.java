package com.bit.apps.kanadra.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit.apps.kanadra.Controler.Adapter.HomeAdapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Championship_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Events_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.News_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.SlidingImage_Adapter;
import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.News_Model.Championships;
import com.bit.apps.kanadra.model.Home_Model.Events;
import com.bit.apps.kanadra.model.Home_Model.PojoHome;
import com.bit.apps.kanadra.model.Home_Model.News;
import com.bit.apps.kanadra.model.ImageModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends Fragment {

    private View inflate_view;
    private RecyclerView eventsRecycler;
    private RecyclerView.LayoutManager events_manager;
    private Events_Adapter events_adapter;
    private List <News> news_Data;
    private List <Events> events_Data;


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private ArrayList<String> myImageList, sliderTitles;


    public Home() {
    }


    // TODO: Rename and change types and number of parameters
    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate_view = inflater.inflate(R.layout.fragment_home, container, false);


        initialization();
       // getData();
        getHomData();

        return inflate_view;
    }

    private void initialization() {
        eventsRecycler = (RecyclerView) inflate_view.findViewById(R.id.events_recycle);
        events_manager = new LinearLayoutManager(getActivity());
        eventsRecycler.setLayoutManager(events_manager);

        imageModelArrayList = new ArrayList<>();
        myImageList = new ArrayList<>();
        sliderTitles = new ArrayList<>();


    }

    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < news_Data.size()+events_Data.size(); i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList.get(i));
            imageModel.setTitle(sliderTitles.get(i));
            list.add(imageModel);
        }

        return list;
    }

    private void init() {

        mPager = (ViewPager) inflate_view.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(getActivity(),imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator) inflate_view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void getHomData(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<PojoHome> myPojoCall = api.getHomeData();

        myPojoCall.enqueue(new Callback<PojoHome>() {
            @Override
            public void onResponse(Call<PojoHome> call, Response<PojoHome> response) {
                Log.v("q1q", response.body().toString());
                news_Data = response.body().getNews();
                events_Data = response.body().getEvents();

                loadSliderData();

                events_adapter = new Events_Adapter(events_Data, getActivity(), news_Data);
                eventsRecycler.setAdapter(events_adapter);

                String ss= news_Data.get(0).getTitle();
                Log.v("", "");
            }

            @Override
            public void onFailure(Call<PojoHome> call, Throwable t) {
                Log.v("q1q", t.toString());

            }
        });
    }

    private void loadSliderData() {
        String image;
        try {
            for(int i=0; i<news_Data.size(); i++){
                image = getString(R.string.baseUrl) + news_Data.get(i).getThumbnail();
                myImageList.add(image);
                sliderTitles.add(news_Data.get(i).getTitle());
            }
            for(int i=0; i<events_Data.size(); i++){
                image = getString(R.string.baseUrl) + events_Data.get(i).getThumbnail();
                myImageList.add(image);
                sliderTitles.add(events_Data.get(i).getTitle());
            }

            imageModelArrayList = populateList();

            init();

        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
        }

    }


}
