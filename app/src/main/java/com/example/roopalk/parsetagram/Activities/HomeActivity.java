package com.example.roopalk.parsetagram.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.roopalk.parsetagram.Fragments.CameraFragment;
import com.example.roopalk.parsetagram.Fragments.PostDetailsFragment;
import com.example.roopalk.parsetagram.Fragments.TimelineFragment;
import com.example.roopalk.parsetagram.Fragments.UserFragment;
import com.example.roopalk.parsetagram.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
{
    boolean works = false;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bnv;

    //Fragments
    private Fragment cameraFragment;
    private Fragment postFragment;
    private Fragment timelineFragment;
    private Fragment userFragment;
    private Fragment postDetailsFragment;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        //setting up the fragments
        cameraFragment = new CameraFragment();
        timelineFragment = new TimelineFragment();
        userFragment = new UserFragment();
        postDetailsFragment = new PostDetailsFragment();

        //On launch, display the home timeline
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, timelineFragment);
        ft.commit();

        //make the bottom navigation menu
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentTransaction.replace(R.id.placeholder, timelineFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.camera:
                        fragmentTransaction.replace(R.id.placeholder, cameraFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.user:
                        // do something here
                        return true;
                }
                return true;
            }
        });
    }
}










