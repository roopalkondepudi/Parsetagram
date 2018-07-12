package com.example.roopalk.parsetagram.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.roopalk.parsetagram.R;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogoutActivity extends AppCompatActivity
{
    @BindView(R.id.btnLogout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}