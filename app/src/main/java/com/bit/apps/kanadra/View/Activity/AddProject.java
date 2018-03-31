package com.bit.apps.kanadra.View.Activity;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.ArrayListPhoto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProject extends AppCompatActivity implements OnMapReadyCallback {

    ImageView camera, news_image;
    LinearLayout camera_layout;
    Dialog dialog;
    EditText project_name, project_description, project_phone, project_website, project_facbook, project_instagram, project_twitter;
    ImageView camera_dialog, gallery_dialog, newImage;
    Button publish;
    private final int CAMERA_RESULT = 1;
    private Bitmap photo = null;
    private Uri selectedImage = null;
    private String encodedImage;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    private ArrayList<String> images;
    LayoutInflater layoutInflater;
    View addView;
    private GoogleMap map;
    private final LatLng kanadra = new LatLng(29.078586, 47.790341);
    private LatLng diwanLocation = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        intialization();
    }

    private void intialization() {
        camera = (ImageView) findViewById(R.id.upload_image);
        project_name = (EditText) findViewById(R.id.project_name);
        project_description = (EditText) findViewById(R.id.project_content);
        publish = (Button) findViewById(R.id.publish_project);
        project_phone = (EditText) findViewById(R.id.project_phone);
        project_website = (EditText) findViewById(R.id.project_website);
        project_facbook = (EditText) findViewById(R.id.project_facebook);
        project_instagram = (EditText) findViewById(R.id.project_instagram);
        project_twitter = (EditText) findViewById(R.id.project_twitter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("انتظر من فضلك");
        preferences = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE);
        camera_layout = (LinearLayout) findViewById(R.id.camera_container);
        layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView = layoutInflater.inflate(R.layout.custom_view, null);
        news_image = (ImageView) addView.findViewById(R.id.icon);
        images = new ArrayList<>();
        initMap();
    }


    public void upload_photo(View v){

        dialog = new Dialog(AddProject.this);
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

    public void publish_project(View v){
        if(validate()){

            progressDialog.show();
            String  title, content, userId, user_auth, mobile, imag, phone, website, facbook, instagram, twitter;

            title = project_name.getText().toString();
            content = project_description.getText().toString();
            mobile = getString(R.string.mobile);
            userId = preferences.getString("id", null);
            user_auth = preferences.getString("auth_key", null);
            phone = project_phone.getText().toString();
            website = project_website.getText().toString();
            facbook = project_facbook.getText().toString();
            twitter = project_twitter.getText().toString();
            instagram = project_instagram.getText().toString();
           // imag = encodedImage;

            callapi(title,content, mobile, userId, user_auth , images,
                    phone, website, facbook, instagram, twitter);


        }

    }


    private void callapi(String title, String content, String mobile, String userId,
                         String user_auth,  ArrayList <String> img,String phone,String website,String facbook,String instagram,String twitter) {
       // MultipartBody.Part[] imagesParts = new MultipartBody.Part[img.size()];
        String [] imgarr = new String[img.size()];
        for (int i=0; i<img.size(); i++)
//            imagesParts[i] = MultipartBody.Part.createFormData("image", img.get(i));
            imgarr[i] = img.get(i);




        ArrayListPhoto arrayListAge = new ArrayListPhoto(img);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());


        Log.v("ssss", imgarr.toString());
        Retrofit retrofit = builder.build();

        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<ResponseBody> call = api.uploadProject(mobile, userId, user_auth,
                title, content, phone, website, facbook, twitter, instagram , arrayListAge);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.v("q1q", response.body().toString());
                progressDialog.dismiss();
                Toast.makeText(AddProject.this, "تم التحميل بنجاح", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddProject.this, MainActivity.class);
                startActivity(intent);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddProject.this, "حاول مرة اخرى", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean validate(){
        boolean valid = true;
        String title, content;
        title = project_name.getText().toString();
        content = project_description.getText().toString();

        if(title.isEmpty()){
            project_name.setError(getString(R.string.news_title));
            valid = false;
        }

        else if(content.isEmpty() ){
            project_description.setError(getString(R.string.news_description));
            valid = false;
        }

        else if(selectedImage == null){
            Toast.makeText(this, R.string.news_image, Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if(diwanLocation == null){
            Toast.makeText(this, "من فضلك حدد مكان الديوان", Toast.LENGTH_SHORT).show();
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView = layoutInflater.inflate(R.layout.custom_view, null);
        news_image = (ImageView) addView.findViewById(R.id.icon);

        Bitmap thumb = null;

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){

//                    selectedImage = data.getData();
                    photo = (Bitmap) data.getExtras().get("data");
                    selectedImage = getImageUri(AddProject.this, photo);
                    thumb = Bitmap.createScaledBitmap(photo, 350, 350, false);
                    news_image.setImageBitmap(thumb);
                    camera_layout.addView(addView);

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
                    thumb = Bitmap.createScaledBitmap(photo, 250, 250, false);
                    news_image.setImageBitmap(thumb);
                    camera_layout.addView(addView);

                }
                break;
        }

        camera_layout.setVisibility(View.VISIBLE);
        String filePath = getRealPathFromURIPath(selectedImage, AddProject.this);
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();



        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        images.add(encodedImage);
        Log.v("q2q", images.size() +"");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(kanadra, 7));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //lstLatLngs.add(latLng);
                map.clear();
                map.addMarker(new MarkerOptions().position(latLng));
                diwanLocation = latLng;
            }
        });

    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(AddProject.this);
    }


}
