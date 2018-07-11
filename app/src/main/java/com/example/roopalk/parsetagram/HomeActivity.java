package com.example.roopalk.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.roopalk.parsetagram.Adapter.PostAdapter;
import com.example.roopalk.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
{

    public static final String TAG = "HomeActivity";
    //private static final String imagePath = "/DCIM/Camera/IMG_20180710_114531.jpg";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rvPost) RecyclerView rvPosts;
    ArrayList<Post> posts;
    PostAdapter postAdapter;

    private SwipeRefreshLayout swipeContainer;

    private final int REQUEST = 32;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        posts = new ArrayList<>();

        loadTopPosts();

        postAdapter = new PostAdapter(posts);

        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        rvPosts.setAdapter(postAdapter);

        setSupportActionBar(toolbar);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadTopPosts();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

    }

    public void loadTopPosts()
    {
        final Post.Query postsQuery = new Post.Query();
        postsQuery
                .getTop()
                .withUser();
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null)
                {
                    for(int i = 0; i < objects.size(); i++)
                    {
                        posts.add(objects.get(i));
                        Log.d(TAG, "Post[" + i + "] = " + objects.get(i).getKeyDescription() + "\n username = " + objects.get(i).getKeyUser().getUsername());
                    }
                }
                else
                {
                    Log.e(TAG, "Failed");
                }
            }
        });
    }

    //make the menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //inflates the menu onto the toolbar
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //depending on the item selected, go to different activities
        switch(item.getItemId())
        {
            case(R.id.logout):
                Intent intent = new Intent(HomeActivity.this, LogoutActivity.class);
                startActivity(intent);
                return true;
            case(R.id.camera):
                Intent cameraIntent = new Intent(HomeActivity.this, CameraActivity.class);
                startActivityForResult(cameraIntent, REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST)
        {
            if(resultCode == RESULT_OK)
            {
                finish();
            }
        }
    }
}