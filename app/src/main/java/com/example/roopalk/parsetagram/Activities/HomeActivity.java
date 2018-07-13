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
import com.example.roopalk.parsetagram.Fragments.PostFragment;
import com.example.roopalk.parsetagram.Fragments.TimelineFragment;
import com.example.roopalk.parsetagram.Fragments.UserFragment;
import com.example.roopalk.parsetagram.Fragments.onFragmentInteractionListener;
import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements onFragmentInteractionListener
{

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bnv;

    //Fragments
    private Fragment cameraFragment;
    private Fragment postFragment;
    private Fragment timelineFragment;
    private Fragment userFragment;
    private Fragment postDetailsFragment;


    Bitmap bitmap;
    File file;

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
        moveToTimeline();

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
                        fragmentTransaction.replace(R.id.placeholder, userFragment);
                        fragmentTransaction.commit();
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public void moveToDetailsPage(Post post)
    {
        postDetailsFragment = PostDetailsFragment.newInstance(post);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, postDetailsFragment); //DETAILS ACTIVITY DOES NOT WORK
        ft.commit();
    }

    @Override
    public void moveToPostPage(Bitmap bitmap, File file)
    {
        PostFragment postFragment = PostFragment.newInstance(bitmap, file);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, postFragment);
        ft.commit();
    }

    public void moveToTimeline()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        bnv.setSelectedItemId(R.id.home);
        ft.replace(R.id.placeholder, timelineFragment);
        ft.commit();
    }
}










