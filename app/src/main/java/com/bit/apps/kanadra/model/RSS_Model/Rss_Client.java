package com.bit.apps.kanadra.model.RSS_Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Rss_Client {

    @GET("/rss/rssfeeds")
    Call<Feed> getItems();

}
