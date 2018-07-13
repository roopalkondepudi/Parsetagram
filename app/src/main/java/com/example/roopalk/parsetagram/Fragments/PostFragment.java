package com.example.roopalk.parsetagram.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostFragment extends Fragment
{
    onFragmentInteractionListener listener;
    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.create_btn) Button btnCreate;
    @BindView(R.id.refresh_btn) Button btnRefresh;
    @BindView(R.id.ivPreview) ImageView ivPreview;

    public final String TAG = "Camera";
    static final int REQUEST = 32;

    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance(Bitmap bitmap, File f) {
        PostFragment fragment = new PostFragment();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Bundle b = new Bundle();
        b.putByteArray("image",byteArray);
        b.putSerializable("file", f);

        // your fragment code
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        byte[] byteArray = getArguments().getByteArray("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ivPreview.setImageBitmap(bmp);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "whatever", Toast.LENGTH_LONG).show();
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                File file = (File) getArguments().getSerializable("file");

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
                    listener.moveToTimeline();
                    Log.i(TAG, "Create post success");
                }
                else
                {
                    Log.e(TAG, "Create post failure");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener)
        {
            listener = (onFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + "must implement onFragmentInteractionListener");
        }
    }
}
