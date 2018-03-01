package com.bit.apps.kanadra.View.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.SignUp_Model;
import com.bumptech.glide.Glide;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Championship_Details extends AppCompatActivity {

    private String title, content, image, views, date, id;
    private TextView title_show, content_show, views_show, date_show;
    private ImageView image_show;
    private Bundle bundle;
    private Button subscrib;
    private Dialog dialog;
    private EditText mobile_num;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship__details);

        intialization();
        getData();
        showData();
    }



    private void intialization() {
        title_show = (TextView) findViewById(R.id.detail_title);
        content_show = (TextView) findViewById(R.id.detail_content);
        views_show = (TextView) findViewById(R.id.detail_views);
        date_show = (TextView) findViewById(R.id.detail_date);
        image_show = (ImageView) findViewById(R.id.detail_image);
        bundle = getIntent().getExtras();
        //subscrib = (Button) findViewById(R.id.subscrib);
        dialog = new Dialog(this);
        preferences = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE);


    }

    private void getData() {
        title = bundle.getString("title");
        content = bundle.getString("content");
        image = bundle.getString("image");
        views = bundle.getString("views");
        date = bundle.getString("date");
        id = bundle.getString("id");

    }

    private void showData() {
        title_show.setText(title);
        content_show.setText(content);
        views_show.setText(views );
        date_show.setText(date);
        Glide.with(this).load(image).into(image_show);
        Log.v("q1q pos ", image + "");
        Log.v("q1q pos ", id + "");

    }


    public void championSubscrib(View view) {

        final String user_id, user_auth;
        user_id = preferences.getString("id", null);
        user_auth = preferences.getString("auth_key", null);

        if(user_id == null){
            Intent intent = new Intent(Championship_Details.this, Login_Signup.class);
            intent.putExtra("champ_add", "0");
            startActivity(intent);
        }

        else {
            dialog.setContentView(R.layout.custom_champion_subscrib);
            dialog.setTitle("");
            mobile_num = dialog.findViewById(R.id.mobile);
            subscrib = dialog.findViewById(R.id.champ_subscrip);
            subscrib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(mobile_num.getText().toString().equals(""))
                        Toast.makeText(Championship_Details.this, "ادخل رقم الموبيل من فضلك", Toast.LENGTH_SHORT).show();
                    else
                        callapi(id ,mobile_num.getText().toString(), getString(R.string.mobile),
                                user_id, user_auth);
                }
            });

            dialog.show();

        }


    }


    private void callapi(String champ_id, String phone_num, String mobile,
                         String userId, String user_auth) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<SignUp_Model> call = api.subscrib(mobile, userId, user_auth,
                champ_id, phone_num);

        call.enqueue(new Callback<SignUp_Model>() {
            @Override
            public void onResponse(Call<SignUp_Model> call, Response<SignUp_Model> response) {
                if(response.body().getCode().equals("200"))
                    Toast.makeText(Championship_Details.this, "تم الاشتراك بنجاح", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Championship_Details.this, "فشل ... حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<SignUp_Model> call, Throwable t) {
                Toast.makeText(Championship_Details.this, "فشل ... حاول مرة اخرى", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
