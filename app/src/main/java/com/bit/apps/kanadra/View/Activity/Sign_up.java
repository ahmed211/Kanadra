package com.bit.apps.kanadra.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_up extends AppCompatActivity {

    EditText email_text, username_text, password_text;
    Button signup_btn;
    String email, username, password;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        username_text = (EditText) findViewById(R.id.user_name_signup);
        email_text = (EditText) findViewById(R.id.email_signup);
        password_text = (EditText) findViewById(R.id.password_signup);

        signup_btn = (Button) findViewById(R.id.btn_signup);
        editor = getSharedPreferences(getString(R.string.login_pref), MODE_PRIVATE).edit();


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });


    }

    private void addUser() {
        if(validate()){

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(getString(R.string.baseUrl))
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            ClientAPI api = retrofit.create(ClientAPI.class);
            Call<SignUp_Model> call = api.creatAccount(email_text.getText().toString(),
                    password_text.getText().toString(), getString(R.string.mobile),
                    username_text.getText().toString(),"", "", ""
            );

            call.enqueue(new Callback<SignUp_Model>() {
                @Override
                public void onResponse(Call<SignUp_Model> call, Response<SignUp_Model> response) {
                    // Toast.makeText(MainActivity.this, "succ", Toast.LENGTH_SHORT).show();
                    String code = response.body().getCode();
                    String message = response.body().getMessage();

                    if(code.equals("200")){
                        List<SignUp_Model.data> data = response.body().getData();
                        String id = data.get(0).getId();
                        String auth_key = data.get(0).getAuth_key();

                        Intent intent = new Intent(Sign_up.this, MainActivity.class);
                        intent.putExtra("id", data.get(0).getId());
                        intent.putExtra("auth_key", data.get(0).getAuth_key());
                        editor.putString("id", id);
                        editor.putString("auth_key", auth_key);
                        editor.apply();
                        Toast.makeText(Sign_up.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }
                    else if(code.equals("300"))
                        Toast.makeText(Sign_up.this, "هذا الايميل موجود بالفعل", Toast.LENGTH_SHORT).show();
                    Log.v("q1q", code);

                }

                @Override
                public void onFailure(Call<SignUp_Model> call, Throwable t) {
                    Toast.makeText(Sign_up.this, "فشل في التسجيل.... حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                    Log.v("q1q", t.toString());

                }
            });
        }
    }

    private boolean validate(){
        boolean valid = true;
        username = username_text.getText().toString();
        email = email_text.getText().toString();
        password = password_text.getText().toString();

        if(username.equals("")){
            username_text.setError(getString(R.string.username_error));
            valid = false;
        }

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_text.setError(getString(R.string.email_error));
            valid = false;
        }

        if(password.equals("")){
            password_text.setError(getString(R.string.password_error));
            valid = false;
        }

        return valid;
    }
}
