package com.bit.apps.kanadra.View.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Diwan extends AppCompatActivity {

    CheckBox saturday, sunday, monday, tuesday, wednesday, thursday, friday;
    ImageView camera, news_image;
    Dialog dialog;
    EditText diwan_name, diwan_description;
    ImageView camera_dialog, gallery_dialog, publish;
    private final int CAMERA_RESULT = 1;
    private Bitmap photo = null;
    private Uri selectedImage = null;
    private String encodedImage;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diwan);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        intialization();
    }

    private void intialization() {

        saturday = (CheckBox) findViewById(R.id.saturday);
        sunday = (CheckBox) findViewById(R.id.sunday);
        monday = (CheckBox) findViewById(R.id.monday);
        tuesday = (CheckBox) findViewById(R.id.tuesday);
        wednesday = (CheckBox) findViewById(R.id.wednesday);
        thursday = (CheckBox) findViewById(R.id.thursday);
        friday = (CheckBox) findViewById(R.id.friday);

        camera = (ImageView) findViewById(R.id.upload_image);
        news_image = (ImageView) findViewById(R.id.diwan_image);
        diwan_name = (EditText) findViewById(R.id.diwan_name);
        diwan_description = (EditText) findViewById(R.id.diwan_content);
        publish = (ImageView) findViewById(R.id.publish_diwan);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("انتظر من فضلك");
        preferences = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE);


    }


    public void upload_photo(View v){

                dialog = new Dialog(Diwan.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("");

                camera_dialog = dialog.findViewById(R.id.open_camera);
                gallery_dialog = dialog.findViewById(R.id.open_gallery);

                camera_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                        dialog.dismiss();
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

    public void publish_diwan(View v){
        if(validate()){

            StringBuffer buffer = new StringBuffer();

            if (saturday.isChecked())
                buffer.append(",السبت");
            if (sunday.isChecked())
                buffer.append(",الاحد");
            if (monday.isChecked())
                buffer.append(",الاثنين");
            if (tuesday.isChecked())
                buffer.append(",الثلاثاء");
            if (wednesday.isChecked())
                buffer.append(",الاربعاء");
            if (thursday.isChecked())
                buffer.append(",الخميس");
            if (friday.isChecked())
                buffer.append(",الجمعة");


            progressDialog.show();

            String  title, content, userId, days, user_auth, mobile, imag;

            title = diwan_name.getText().toString();
            content = diwan_description.getText().toString();
            mobile = getString(R.string.mobile);
            userId = preferences.getString("id", null);
            days = buffer.toString();
            user_auth = preferences.getString("auth_key", null);
            imag = encodedImage;

            callapi(title,content, mobile, userId, days, user_auth , imag);


        }

    }


    private void callapi(String title, String content, String mobile, String userId,
                         String days, String user_auth, String imag) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<ResponseBody> call = api.uploadDiwan(mobile, userId, user_auth,
                title, days, content, imag);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("q1q", response.body().toString());
                progressDialog.dismiss();
                Toast.makeText(Diwan.this, "تم التحميل بنجاح", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Diwan.this, "حاول مرة اخرى", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean validate(){
        boolean valid = true;
        String title, content;
        title = diwan_name.getText().toString();
        content = diwan_description.getText().toString();

        if(title.isEmpty()){
            diwan_name.setError(getString(R.string.news_title));
            valid = false;
        }

        else if(content.isEmpty() ){
            diwan_description.setError(getString(R.string.news_description));
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

        String filePath = getRealPathFromURIPath(selectedImage, Diwan.this);
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();



        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    }



}
