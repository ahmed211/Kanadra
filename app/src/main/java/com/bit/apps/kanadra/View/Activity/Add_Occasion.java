package com.bit.apps.kanadra.View.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.SignUp_Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Occasion extends AppCompatActivity {

    private TextView occasion_date, occasion_name, occasion_content;
    private ImageView occasion_photo,camera, camera_dialog, gallery_dialog;
    private Dialog dialog;
    private Button publish;

    private Bitmap photo = null;
    private Uri selectedImage = null;
    private String encodedImage;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__occasion);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
        initialization();




        occasion_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateDialog();
            }
        });
    }

    private void initialization() {
        occasion_date = (TextView) findViewById(R.id.occasion_date);
        occasion_name = (TextView) findViewById(R.id.occasion_name);
        occasion_content =(TextView) findViewById(R.id.occasion_content);

        camera = (ImageView) findViewById(R.id.upload_image);
        occasion_photo = (ImageView) findViewById(R.id.occasion_image);
        publish = (Button) findViewById(R.id.publish_occasion);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("انتظر من فضلك");
        preferences = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE);



    }

    public void DateDialog(){
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                occasion_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(this, listener, 2018, 1, 1);
        dpDialog.show();
    }

    public void upload_photo(View view) {
        dialog = new Dialog(Add_Occasion.this);
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

    public void publish_occasion(View v){
        if(validate()){

            progressDialog.show();

            String  title, content, userId, category_id, user_auth, mobile, imag,date;

            title = occasion_name.getText().toString();
            content = occasion_content.getText().toString();
            mobile = getString(R.string.mobile);
            userId = preferences.getString("id", null);
            user_auth = preferences.getString("auth_key", null);
            imag = encodedImage;
            date = occasion_date.getText().toString();

            callapi(title,content, mobile, userId, date, user_auth , imag);

        }

    }

    private boolean validate(){
        boolean valid = true;
        String title, content, date;
        title = occasion_name.getText().toString();
        content = occasion_content.getText().toString();
        date = occasion_date.getText().toString();

        if(title.isEmpty()){
            occasion_name.setError(getString(R.string.news_title));
            valid = false;
        }

        else if(content.isEmpty() ){
            occasion_content.setError(getString(R.string.news_description));
            valid = false;
        }

        else if(selectedImage == null){
            Toast.makeText(this, R.string.news_image, Toast.LENGTH_SHORT).show();
            valid = false;
        }

        else if(date.isEmpty()){
            Toast.makeText(this, "ادخل التاريخ", Toast.LENGTH_SHORT).show();
            valid = false;

        }

        return valid;
    }



    private void callapi(String title, String content, String mobile, String userId,
                         String date, String user_auth, String imag) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<SignUp_Model> call = api.uploadOccasion(mobile, userId, user_auth,
                title, date, content, imag);

        call.enqueue(new Callback<SignUp_Model>() {
            @Override
            public void onResponse(Call<SignUp_Model> call, Response<SignUp_Model> response) {
                if(response.body().getCode().equals("200")) {
                    progressDialog.dismiss();
                    Toast.makeText(Add_Occasion.this, "تم التحميل بنجاح", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Add_Occasion.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(Add_Occasion.this, "حاول مرة اخرى", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<SignUp_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Add_Occasion.this, "حاول مرة اخرى", Toast.LENGTH_SHORT).show();

            }
        });

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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap thumb = null;

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){

//                    selectedImage = data.getData();
                    photo = (Bitmap) data.getExtras().get("data");
                    selectedImage = getImageUri(Add_Occasion.this, photo);

//                    thumb = Bitmap.createScaledBitmap(photo, 250, 250, false);
                    occasion_photo.setImageBitmap(photo);

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
                    //thumb = Bitmap.createScaledBitmap(photo, 250, 250, false);
                    occasion_photo.setImageBitmap(photo);
                }
                break;
        }

        occasion_photo.setVisibility(View.VISIBLE);
        String filePath = getRealPathFromURIPath(selectedImage, Add_Occasion.this);
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();



        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        Log.v("q1q", encodedImage);
    }

}
