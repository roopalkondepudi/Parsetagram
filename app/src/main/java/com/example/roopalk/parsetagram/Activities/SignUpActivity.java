package com.example.roopalk.parsetagram.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roopalk.parsetagram.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity
{
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etHandle) EditText etHandle;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnSignUp) Button btnSignUp;

    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //binding with butterknife
        ButterKnife.bind(this);

        //set onClickListener for the button
        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //get all the text fields
                String email, username, handle, password;
                email = etEmail.getText().toString();
                username = etUsername.getText().toString();
                handle = "@" + etHandle.getText().toString(); //handle
                password = etPassword.getText().toString();

                //creating a new ParseUser
                final ParseUser newUser = new ParseUser();

                //set properties of the user
                newUser.setEmail(email);
                newUser.put("handle", handle); //setting the handle
                newUser.setPassword(password);
                newUser.setUsername(username);
                //invoke signUpInBackground

                newUser.signUpInBackground(new SignUpCallback()
                {
                    @Override
                    public void done(ParseException e)
                    {
                        if(e == null)
                        {
                            Log.i("SignUpActivity", "Sign up successful");
                            finish();
                        }
                        else
                        {
                            Log.e("SignUpActivity", "Sign up failed");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
