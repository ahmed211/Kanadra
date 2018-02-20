package com.bit.apps.kanadra.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Championship_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Diwan_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Events_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.News_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Projects_Adapter;
import com.bit.apps.kanadra.Controler.EqualSpacingItemDecoration;
import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.Diwan_Model.DiwanItem;
import com.bit.apps.kanadra.model.Home_Model.News;
import com.bit.apps.kanadra.model.Home_Model.PojoHome;
import com.bit.apps.kanadra.model.News_Model.Championships;
import com.bit.apps.kanadra.model.News_Model.Item;
import com.bit.apps.kanadra.model.News_Model.PojoNews;
import com.bit.apps.kanadra.model.ProjectsModel.ProjectsData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsArticles extends Fragment {

    private RecyclerView newsRecycler;
    private RecyclerView.LayoutManager newsManager;
    private News_Adapter newsAdapter;
    private Championship_Adapter championshipAdapter;
    private Projects_Adapter projectsAdapter;
    private Diwan_Adapter diwanAdapter;
    private List<Item> news;
    private List<Championships> champion;
    private List<DiwanItem> diwan;
    private List<ProjectsData> projects;
    private View view;
    private String code;


    public NewsArticles() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_news, container, false);

        initialization();

        getData();

        return view;
    }


    private void initialization() {
        newsRecycler = (RecyclerView) view.findViewById(R.id.news_article);
        newsRecycler.addItemDecoration(new EqualSpacingItemDecoration(10));

        newsManager = new GridLayoutManager(getActivity(), 2);
        newsRecycler.setLayoutManager(newsManager);

        news = new ArrayList<>();
        champion = new ArrayList<>();
        projects = new ArrayList<>();

        if (getArguments() != null)
            code = getArguments().getString("code");
    }
    private void getData() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit= builder.build();
        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<PojoNews> newsCall = null;
        Log.v("q1q", code);
        if (code.equals("1"))
            newsCall = api.getNews();
        else if(code.equals("2"))
            newsCall = api.getFamillyNews();
        else if(code.equals("3"))
            newsCall = api.getArticles();
        else if(code.equals("4"))
            newsCall = api.getChampion();
        else if (code.equals("5"))
            newsCall = api.getDiwan();
        else if (code.equals("6"))
            newsCall = api.getEvents();
        else if (code.equals("7"))
            newsCall = api.getProjects();

        newsCall.enqueue(new Callback<PojoNews>() {
            @Override
            public void onResponse(Call<PojoNews> call, Response<PojoNews> response) {

                if (code.equals("4")){
                    champion = response.body().getData();
                    championshipAdapter = new Championship_Adapter(champion, getActivity());
                    newsRecycler.setAdapter(championshipAdapter);
                }
                else if(code.equals("5")){
                    diwan = response.body().getDiwan();
                    diwanAdapter = new Diwan_Adapter(diwan, getActivity());
                    newsRecycler.setAdapter(diwanAdapter);
                }
                else if(code.equals("6")){
                    news = response.body().getEvents();
                    newsAdapter = new News_Adapter(news, getActivity());
                    newsRecycler.setAdapter(newsAdapter);
                }
                else if(code.equals("7")){
                    projects = response.body().getProjects();
                    projectsAdapter = new Projects_Adapter(projects, getActivity());
                    newsRecycler.setAdapter(projectsAdapter);
                }
                else {
                    news = response.body().getNews();
                    newsAdapter = new News_Adapter(news, getActivity());
                    newsRecycler.setAdapter(newsAdapter);
                }

                ////                news_adapter = new News_Adapter(news_Data, getActivity());
// ////                newsRecycler.setAdapter(news_adapter);

            }

            @Override
            public void onFailure(Call<PojoNews> call, Throwable t) {
                Log.v("q1q", t.toString());

            }
        });

    }



}
