package com.example.roopalk.parsetagram.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.create_btn) Button btnCreate;
    @BindView(R.id.refresh_btn) Button btnRefresh;
    @BindView(R.id.camera_btn) ImageView ivCamera;

    public final String TAG = "Camera";
    static final int REQUEST_IMAGE_CAPTURE = 3;
    static final int REQUEST = 32;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "whatever", Toast.LENGTH_LONG).show();
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                File file = getPhotoFileUri(photoFileName);
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

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera(v);
            }
        });
    }//when Camera is launched

    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(CameraActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    //after the photo has been taken, do this
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
                //put this image in the imageView in the xml file
                ivPreview.setImageBitmap(takenImage);

            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
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
