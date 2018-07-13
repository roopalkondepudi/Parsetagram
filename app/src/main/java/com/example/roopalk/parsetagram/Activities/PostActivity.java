package com.example.roopalk.parsetagram.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {

    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.create_btn) Button btnCreate;
    @BindView(R.id.refresh_btn) Button btnRefresh;
    @BindView(R.id.ivPreview) ImageView ivPreview;

    public final String TAG = "Camera";
    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int REQUEST = 32;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ivPreview.setImageBitmap(bmp);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "whatever", Toast.LENGTH_LONG).show();
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                File file = (File) getIntent().getExtras().get("file");

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

    private void createPost(String description, ParseFile imageFile, ParseUser user)
    {
        final Post newPost = new Post(); //make a new post
        newPost.setKeyDescription(description); //set all the attributes of the post
        newPost.setKeyUser(user);
        newPost.setKeyImage(imageFile);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) { //save the post to Parse
                if(e == null)
                {
                    Intent returnIntent = new Intent();
                    setResult(REQUEST, returnIntent);
                    Log.d(TAG, "Create post success!");
                    finish();
                }
                else
                {
                    Log.e(TAG, "Create post failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
