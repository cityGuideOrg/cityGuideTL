package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gr.teicm.cityguidetl.cityguidetl.R;

public class LoginActivity extends AppCompatActivity {
    Button bSignIn;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bSignIn = findViewById(R.id.bSignIn);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    public LoginActivity(Context context) {

    }
    public String validate(String userName, String password) {
        if (userName.equals("user") && password.equals("user"))
            return "Login was successful";
        else
            return "Invalid login!";
        }
    }
