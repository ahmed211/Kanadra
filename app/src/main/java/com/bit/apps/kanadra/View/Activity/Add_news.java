package com.bit.apps.kanadra.View.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Championship_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.Events_Adapter;
import com.bit.apps.kanadra.Controler.Adapter.Home_Adapter.News_Adapter;
import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.Home_Model.PojoHome;
import com.bit.apps.kanadra.model.News_Categories_Model.Categories;
import com.bit.apps.kanadra.model.News_Categories_Model.PojoCat;
import com.bit.apps.kanadra.model.SignUp_Model;
import com.bit.apps.kanadra.model.UploadObject;
import com.bumptech.glide.load.data.FileDescriptorLocalUriFetcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.File.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_news extends AppCompatActivity {

    LinearLayout camera_layout;
    ImageView camera, news_image;
    Dialog dialog;
    EditText news_name, news_description;
    ImageView camera_dialog, gallery_dialog, publish;
    private final int CAMERA_RESULT = 1;
    private Bitmap photo = null;
    private Uri selectedImage = null;
    private String encodedImage;
    private ProgressDialog progressDialog;
    private List<Categories> categories;
    private List<String> id,name;
    private Spinner cats;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initialization();
        getNewsCategories();




    }

    private void initialization() {
        camera = (ImageView) findViewById(R.id.upload_image);
        news_image = (ImageView) findViewById(R.id.news_image);
        news_name = (EditText) findViewById(R.id.news_name);
        news_description = (EditText) findViewById(R.id.news_description);
        publish = (ImageView) findViewById(R.id.publish_news);
        progressDialog = new ProgressDialog(this);
        cats = (Spinner) findViewById(R.id.news_categories);
        progressDialog.setMessage("انتظر من فضلك");
        id = new ArrayList<>();
        name = new ArrayList<>();
        preferences = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE);



    }

    private void getNewsCategories(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<PojoCat> myPojoCall = api.getNewsCategory();

        myPojoCall.enqueue(new Callback<PojoCat>() {
            @Override
            public void onResponse(Call<PojoCat> call, Response<PojoCat> response) {
                Log.v("q1q", response.body().toString());
                categories = response.body().getCategories();
                addCategories();

                Log.v("", "");
            }

            @Override
            public void onFailure(Call<PojoCat> call, Throwable t) {
                Log.v("q1q", t.toString());

            }
        });
    }

    private void addCategories() {
        for(int i=0; i<categories.size(); i++){
            id.add(categories.get(i).getId());
            name.add(categories.get(i).getName());
        }

        ArrayAdapter<String> categories_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, name);
        cats.setAdapter(categories_adapter);

    }


    public void upload_photo(View v){

                dialog = new Dialog(Add_news.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("");

                camera_dialog = dialog.findViewById(R.id.open_camera);
                gallery_dialog = dialog.findViewById(R.id.open_gallery);

                camera_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (ContextCompat.checkSelfPermission(Add_news.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        {
                            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) Add_news.this, Manifest.permission.CAMERA))
                            {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Add_news.this);
                                alertBuilder.setCancelable(true);
                                alertBuilder.setTitle("Permission necessary");
                                alertBuilder.setMessage("CAMERA is necessary");
                                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions((Activity) Add_news.this,
                                                new String[]{Manifest.permission.CAMERA}, 0);
                                    }
                                });
                                AlertDialog alert = alertBuilder.create();
                                alert.show();
                            } else {
                                ActivityCompat.requestPermissions((Activity) Add_news.this, new String[]{Manifest.permission.CAMERA}, 0);
                            }
                            Log.v("q1q", "fffffffffaals");
                        } else {
                            Log.v("q1q", "trueeeeeeeeeeeeeee");

                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                            dialog.dismiss();

                        }

                    }
                });

                gallery_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                        dialog.dismiss();

                    }
                });


                dialog.show();


    }

    public void publish_news(View v){
        if(validate()){

            progressDialog.show();

            String  title, content, userId, category_id, user_auth, mobile, imag, category_name;
            int cat_pos;

            title = news_name.getText().toString();
            content = news_description.getText().toString();
            mobile = getString(R.string.mobile);
            userId = preferences.getString("id", null);
            user_auth = preferences.getString("auth_key", null);
            imag = encodedImage;
            cat_pos = cats.getSelectedItemPosition();
            category_id = id.get(cat_pos);

            callapi(title,content, mobile, userId, category_id, user_auth , imag);

            }

    }

    private void callapi(String title, String content, String mobile, String userId,
                         String category_id, String user_auth, String imag) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<ResponseBody> call = api.uploadnews(mobile, userId, user_auth,
                title, category_id, content, imag);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("q1q", response.body().toString());
                progressDialog.dismiss();
                Toast.makeText(Add_news.this, "تم التحميل بنجاح", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Add_news.this, "حاول مرة اخرى", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean validate(){
        boolean valid = true;
        String title, content;
        title = news_name.getText().toString();
        content = news_description.getText().toString();

        if(title.isEmpty()){
            news_name.setError(getString(R.string.news_title));
            valid = false;
        }

        else if(content.isEmpty() ){
            news_description.setError(getString(R.string.news_description));
            valid = false;
        }

        else if(selectedImage == null){
            Toast.makeText(this, R.string.news_image, Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }



    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){

                    selectedImage = data.getData();
                    photo = (Bitmap) data.getExtras().get("data");
                    news_image.setImageBitmap(photo);

                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    selectedImage = data.getData();
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    news_image.setImageBitmap(photo);
                }
                break;
        }

        String filePath = getRealPathFromURIPath(selectedImage, Add_news.this);
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();



        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    }
}
