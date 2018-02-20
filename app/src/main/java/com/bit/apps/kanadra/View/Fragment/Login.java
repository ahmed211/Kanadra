package com.bit.apps.kanadra.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.apps.kanadra.R;
import com.bit.apps.kanadra.View.Activity.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Fragment {

    private View inflate_view;
    private Fragment fragment;
    private FragmentManager manager;
    private android.support.v4.app.FragmentTransaction transaction;
    private TextView link_signup;
    private LoginButton loginButton;
    private CallbackManager facebook_manager;


//    Fragment fragment = new YourFragment();
//
//    FragmentManager fm = getSupportFragmentManager();
//    FragmentTransaction transaction = fm.beginTransaction();
//transaction.replace(R.id.contentFragment, fragment);
//transaction.commit();

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance() {
        Login fragment = new Login();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        inflate_view = inflater.inflate(R.layout.fragment_login, container, false);

        initialization();
        //facebookLogin();
        facebook_manager = CallbackManager.Factory.create();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("q1q", "clic");


                loginButton.registerCallback(facebook_manager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.v("q1q", "suc");

                        String userid = loginResult.getAccessToken().getUserId();

                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                displayUserInfo(object);

                            }
                        });

                        Bundle paramters = new Bundle();
                        paramters.putString("fields", "first_name, last_name, email, id");
                        graphRequest.setParameters(paramters);
                        graphRequest.executeAsync();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
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


        return inflate_view;
    }

    private void initialization() {
        fragment = new SignUp();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        loginButton = (LoginButton) inflate_view.findViewById(R.id.facebook_login);
        loginButton.setReadPermissions("email", "public_profile");






//        link_signup = (TextView) inflate_view.findViewById(R.id.);
//
//        link_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               // transaction.replace(R.id.frame_layout, fragment);
//                transaction.commit();
//
//            }
//        });


    }


//    private void facebookLogin() {
//        loginButton.registerCallback(facebook_manager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                String userid = loginResult.getAccessToken().getUserId();
//
//                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        displayUserInfo(object);
//
//                    }
//                });
//
//                Bundle paramters = new Bundle();
//                paramters.putString("fields", "first_name, last_name, email, id");
//                graphRequest.setParameters(paramters);
//                graphRequest.executeAsync();
//
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//
//
//            }
//
//            @Override
//            public void onCancel() {
//                Log.v("q1q", "cancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.v("q1q", error.toString());
//
//            }
//        });
//    }

    private void displayUserInfo(JSONObject object) {

        String first_name, last_name, email, id;
        first_name = "";
        last_name = "";
        email = "";
        id = "";


        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("q1q", "first name :  " + first_name + "\n" +
                "last name : " + last_name + "\n" +
                "id     :  " + id + "\n" +
                "email      :" + email
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebook_manager.onActivityResult(requestCode, resultCode, data);
    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        facebook_manager.onActivityResult(requestCode, resultCode, data);
//    }

}
