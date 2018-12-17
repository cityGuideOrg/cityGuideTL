package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import gr.teicm.cityguidetl.cityguidetl.Entities.OAuthResponse;
import gr.teicm.cityguidetl.cityguidetl.R;
import gr.teicm.cityguidetl.cityguidetl.Services.AuthenticateService;
import gr.teicm.cityguidetl.cityguidetl.Services.CityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button signIn_btn, signUp_btn;
    EditText usernameET, passwordET;
    TextView titleTV, usernameTV, passwordTV;

    public static AuthenticateService authenticateService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        authenticateService = retrofit.create(AuthenticateService.class);

        setContentView(R.layout.activity_login);
        signIn_btn = findViewById(R.id.signIn_btn);
        signUp_btn = findViewById(R.id.signUp_btn);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        titleTV = findViewById(R.id.titleTV);
        usernameTV = findViewById(R.id.usernameTV);
        passwordTV = findViewById(R.id.passwordTV);
        usernameET.requestFocus();
        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authenticateService.login(usernameET.getText().toString(), passwordET.getText().toString(), "password").enqueue(new Callback<OAuthResponse>() {
                    @Override
                    public void onResponse(Call<OAuthResponse> call, Response<OAuthResponse> response) {
                        if(response.code() == 200) {
                            MainActivity.access_token = response.body().getAccess_token();
                            MainActivity.refresh_token = response.body().getRefresh_token();

                            //     response.body().getAccess_token();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OAuthResponse> call, Throwable t) {

                        Toast.makeText(LoginActivity.this, "check your internet connection", Toast.LENGTH_LONG).show();


                    }
                });
            }
        });
    }
    }
