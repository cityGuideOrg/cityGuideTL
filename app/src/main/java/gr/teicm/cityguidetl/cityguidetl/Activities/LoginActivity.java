package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gr.teicm.cityguidetl.cityguidetl.R;

public class LoginActivity extends AppCompatActivity {
    Button signIn_btn, signUp_btn;
    EditText usernameET, passwordET;
    TextView titleTV, usernameTV, passwordTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn_btn = findViewById(R.id.signIn_btn);
        signUp_btn = findViewById(R.id.signUp_btn);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        titleTV = findViewById(R.id.titleTV);
        usernameTV = findViewById(R.id.usernameTV);
        passwordTV = findViewById(R.id.passwordTV);
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(usernameET.getText().toString(), passwordET.getText().toString());
            }
        });
    }

    public String validate(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin"))
            return "Login was successful";
        else
            return "Invalid login!";
        }
    }
