package com.example.roopalk.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roopalk.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
{

    //request image capture
    static final int REQUEST_IMAGE_CAPTURE = 3;

    //intent to take a picture
    private void takePicture()
    {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(photoIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    public static final String TAG = "HomeActivity";
    private static final String imagePath = "/DCIM/Camera/IMG_20180710_114531.jpg";
    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.create_btn) Button btnCreate;
    @BindView(R.id.refresh_btn) Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "whatever", Toast.LENGTH_LONG).show();
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                String path = Environment.getExternalStorageDirectory().getPath();

                final File file = new File(path+imagePath);
                final ParseFile parseFile = new ParseFile(file);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null)
                            createPost(description, parseFile, user);
                        else
                            e.printStackTrace();
                    }
                });
            }
        });
    }

    private void loadTopPosts()
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

    private void createPost(String description, ParseFile imageFile, ParseUser user)
    {
        final Post newPost = new Post();
        newPost.setKeyDescription(description);
        newPost.setKeyUser(user);
        newPost.setKeyImage(imageFile);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                {
                    Log.d(TAG, "Create post success!");
                }
                else
                {
                    Log.e(TAG, "Create post failure");
                    e.printStackTrace();
                }
            }
        });
    }

    //make the menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logout:
                Intent intent = new Intent(HomeActivity.this, LogoutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}