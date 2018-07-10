package com.example.roopalk.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity
{
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnSignUp) Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //binding with butterknife
        ButterKnife.bind(this);

        //get all the text fields
        String email, name, username, password;
        email = etEmail.getText().toString();
        name = etName.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        //creating a new ParseUser

        //set onClickListener for the button
        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               finish();
            }
        });
    }
}
