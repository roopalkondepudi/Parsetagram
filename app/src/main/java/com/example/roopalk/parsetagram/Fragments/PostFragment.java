package com.example.roopalk.parsetagram.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostFragment extends Fragment {
    private static final String TAG = "PostFragment";
    //Binding views with ButterKnife
    @BindView(R.id.ivPreview) ImageView ivPreview;
    @BindView(R.id.etDescription) TextView etDescription;
    @BindView(R.id.create_btn) Button btnCreate;
    @BindView(R.id.refresh_btn) Button btnRefresh;
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_camera, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        ButterKnife.bind(this, view);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "whatever", Toast.LENGTH_LONG).show();
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                FragmentManager fm = getFragmentManager();
                CameraFragment fragm = (CameraFragment) fm.findFragmentById(R.id.camera_fragment);

                File file = fragm.getPhotoFileUri(fragm.photoFileName);

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
//                    Intent returnIntent = new Intent();
//                    setResult(REQUEST, returnIntent);
//                    Log.d(TAG, "Create post success!");
//                    finish();

                    //make a method call to host activity
                }
                else
                {
//                    Log.e(TAG, "Create post failure");
//                    e.printStackTrace();
                }
            }
        });
    }
}
