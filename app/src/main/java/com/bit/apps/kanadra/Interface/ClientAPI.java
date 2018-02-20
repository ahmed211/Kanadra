package com.bit.apps.kanadra.Interface;

import com.bit.apps.kanadra.model.AboutUs;
import com.bit.apps.kanadra.model.Gallery_Model.PojoGallery;
import com.bit.apps.kanadra.model.Home_Model.PojoHome;
import com.bit.apps.kanadra.model.News_Categories_Model.PojoCat;
import com.bit.apps.kanadra.model.News_Model.PojoNews;
import com.bit.apps.kanadra.model.SignUp_Model;
import com.bit.apps.kanadra.model.UploadObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ClientAPI {

    @FormUrlEncoded
    @POST("index.php?r=api/user/add")
    Call<SignUp_Model> creatAccount(@Field("email")String email ,
                                @Field("password") String password,
                                @Field("mobile") String mobile,
                                @Field("username") String username,
                                @Field("avatar") String avatar,
                                @Field("type") String type,
                                @Field("social") String social);


    @FormUrlEncoded
    @POST("index.php?r=api/user/login")
    Call<SignUp_Model> userLogin(@Field("email") String email,
                                 @Field("password") String password);


    @GET("index.php?r=api/get/main")
    Call<PojoHome> getHomeData();

    @GET("index.php?r=api/get/gallery")
    Call<PojoGallery> getGallery();

    @FormUrlEncoded
    @POST("index.php?r=api/do/news")
    Call<ResponseBody> uploadnews(
            @Field("mobile") String mobile,
            @Field("u_id") String u_id,
            @Field("u_auth") String u_auth,
            @Field("title") String title,
            @Field("cat_id") String cat_id,
            @Field("content") String content,
            @Field("file") String file

            );



    @FormUrlEncoded
    @POST("index.php?r=api/do/event")
    Call<ResponseBody> uploadOccasion(
            @Field("mobile") String mobile,
            @Field("u_id") String u_id,
            @Field("u_auth") String u_auth,
            @Field("title") String title,
            @Field("date") String date,
            @Field("content") String content,
            @Field("file") String file
    );


    @FormUrlEncoded
    @POST("index.php?r=api/do/diwan")
    Call<ResponseBody> uploadDiwan(
            @Field("mobile") String mobile,
            @Field("u_id") String u_id,
            @Field("u_auth") String u_auth,
            @Field("title") String title,
            @Field("days") String days,
            @Field("content") String content,
            @Field("file") String file
    );


    @GET("index.php?r=api/get/categories")
    Call<PojoCat> getNewsCategory();


    @FormUrlEncoded
    @POST("index.php?r=api/do/subscribe")
    Call<ResponseBody> subscrib(
            @Field("mobile") String mobile,
            @Field("u_id") String u_id,
            @Field("u_auth") String u_auth,
            @Field("champ_id") String champ_id,
            @Field("phone") String phone
    );


    @GET("index.php?r=api/get/page&name=about")
    Call<AboutUs> getAbout();


    @GET("index.php?r=api/get/news")
    Call<PojoNews> getNews();

    @GET("index.php?r=api/get/news&category=1")
    Call<PojoNews> getFamillyNews();


    @GET("index.php?r=api/get/news&category=3")
    Call<PojoNews> getArticles();

    @GET("index.php?r=api/get/diwans")
    Call<PojoNews> getDiwan();

    @GET("index.php?r=api/get/championships")
    Call<PojoNews> getChampion();

    @GET("index.php?r=api/get/events")
    Call<PojoNews> getEvents();

    @GET("index.php?r=api/get/projects")
    Call<PojoNews> getProjects();



}
