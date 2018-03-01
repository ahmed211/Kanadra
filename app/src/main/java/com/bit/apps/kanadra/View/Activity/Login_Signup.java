package com.bit.apps.kanadra.View.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bit.apps.kanadra.Interface.ClientAPI;
import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.model.SignUp_Model;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login_Signup extends AppCompatActivity {

    private LoginButton facebookLoginButton ;
    private TwitterLoginButton twitterLoginButton;
    private CallbackManager manager;
    private Button login;
    private String email, password, champ_add;
    private EditText email_text,password_text;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private Bundle bundle;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login__signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        intialization();
        facebookLogin();
        twitterLogin();


    }

    // intialize variables

    private void intialization() {
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login);
        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login);
        login = (Button) findViewById(R.id.btn_login_login);
        email_text = (EditText) findViewById(R.id.input_email_login);
        password_text = (EditText) findViewById(R.id.input_password_login);
        intent = new Intent(Login_Signup.this, MainActivity.class);
        editor = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE).edit();
        bundle = getIntent().getExtras();
        champ_add = bundle.getString("champ_add");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("انتظر من فضلك");



    }

    // login with email and password
    public void defaultLogin(View v) {

        email = email_text.getText().toString();
        password = password_text.getText().toString();


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<SignUp_Model> call = api.userLogin(email, password);

        call.enqueue(new retrofit2.Callback<SignUp_Model>() {
            @Override
            public void onResponse(Call<SignUp_Model> call, Response<SignUp_Model> response) {
                // Toast.makeText(MainActivity.this, "succ", Toast.LENGTH_SHORT).show();
                String code = response.body().getCode();
                String message = response.body().getMessage();

                if(code.equals("200")){
                    List<SignUp_Model.data> data = response.body().getData();

//                    String id = data.get(0).getId();
                    Log.v("q1q", code);

                    gotoMain(data.get(0).getId(), data.get(0).getAuth_key());

                }

                Toast.makeText(Login_Signup.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUp_Model> call, Throwable t) {
//                    Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                Log.v("q1q", t.toString());

            }
        });

    }


    // Login with twitter
    private void twitterLogin() {

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                getResultTwitter(session, token);


               // login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                //Displaying Toast message
                Toast.makeText(Login_Signup.this, "تاكد من وجود تطبيق twitter على الهاتف", Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void login(TwitterSession session)
//    {
//        String username = session.getUserName();
//        Intent intent = new Intent(Login_Signup.this, MainActivity.class);
//        Log.v("q1q", username);
//        intent.putExtra("username", username);
//        startActivity(intent);
//    }
    public void getResultTwitter(final TwitterSession session, final String token){
        Call<User> userResult =TwitterCore.getInstance().getApiClient(session).getAccountService().verifyCredentials(true,true,true);
        userResult.enqueue(new Callback<User>() {

            @Override
            public void failure(TwitterException e) {

            }

            @Override
            public void success(Result<User> userResult) {

                User user = userResult.data;

                try {
                    socialMediaAccount(user.email, session.getUserName(), user.profileImageUrl,
                            "twitter", token);
                    //session.getUserName()
//                    Log.v("q1q", " url " + user.profileImageUrl);
//                    Log.v("q1q", "name "  +user.name );
//                    Log.v("q1q","email  "+user.email);
//                    Log.v("q1q", "des" + user.description);
//                    Log.v("q1q ", "fol  " + String.valueOf(user.followersCount));
//                    Log.v("q1q","creat" + user.createdAt);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });

    }


    // Login with facebook
    private void facebookLogin()
    {
        facebookLoginButton.setReadPermissions("email", "public_profile");
        manager = CallbackManager.Factory.create();
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLoginButton.registerCallback(manager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

//                        String userid = loginResult.getAccessToken().getUserId();

                        final String token = loginResult.getAccessToken().getToken();
                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                displayUserInfo(object, token);


                            }
                        });


                        Bundle paramters = new Bundle();
                        paramters.putString("fields", "first_name, last_name, email, id");
                        graphRequest.setParameters(paramters);
                        graphRequest.executeAsync();

                        Intent intent = new Intent(Login_Signup.this, MainActivity.class);
                        if(champ_add.equals("0"))
                            intent.putExtra("add", "0");
                        else
                            intent.putExtra("add", "1");

                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {

                        Log.v("q1q", "cancel");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.v("q1q", error.toString());

                    }
                });

            }
        });

    }

    private void displayUserInfo(JSONObject object, String token) {

        String first_name, last_name, email, id, image;
        first_name ="";
        last_name="";
        email = "";
        id="";


        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        image = "https://graph.facebook.com/" + id + "/picture?type=large";
        socialMediaAccount(email, first_name + last_name, image,
                "facebook", token);

        Log.v("q1q", "first name :  " + first_name +"\n" +
                "last name : " + last_name +"\n"+
                "id     :  " + id + "\n" +
                "email      :" + email
        );

    }



    private void socialMediaAccount(String email, String username, String avatar,
                                    String type, String social) {
        progressDialog.show();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ClientAPI api = retrofit.create(ClientAPI.class);
        Call<SignUp_Model> call = api.creatAccount(email, "",
                getString(R.string.mobile), username, avatar, type, social
        );

        call.enqueue(new retrofit2.Callback<SignUp_Model>() {
            @Override
            public void onResponse(Call<SignUp_Model> call, Response<SignUp_Model> response) {
                // Toast.makeText(MainActivity.this, "succ", Toast.LENGTH_SHORT).show();


                String code = response.body().getCode();
                String message = response.body().getMessage();

                if(code.equals("200")){
                    List<SignUp_Model.data> data = response.body().getData();
                    String id = data.get(0).getId();
                    gotoMain(data.get(0).getId(), data.get(0).getAuth_key());

                }
                Log.v("q1q", code);
                Toast.makeText(Login_Signup.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

                }

            @Override
            public void onFailure(Call<SignUp_Model> call, Throwable t) {
              Toast.makeText(Login_Signup.this, "حاول مرة اخرى", Toast.LENGTH_SHORT).show();
//                Log.v("q1q", t.toString());
                progressDialog.dismiss();
                }
            });
    }

    private void gotoMain(String id, String auth_key) {
        editor.putString("id", id);
        editor.putString("auth_key", auth_key);
        editor.apply();
        intent = new Intent(Login_Signup.this, MainActivity.class);
        if(champ_add.equals("0"))
            intent.putExtra("add", "0");
        else
            intent.putExtra("add", "1");


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void startSignup(View v){
        Intent signup = new Intent(Login_Signup.this, Sign_up.class);
        signup.putExtra("champ_add", champ_add);
        startActivity(signup);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        manager.onActivityResult(requestCode, resultCode, data);

        twitterLoginButton.onActivityResult(requestCode, resultCode, data);

    }
}
