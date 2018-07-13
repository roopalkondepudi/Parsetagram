package com.example.roopalk.parsetagram.Fragments;

import android.graphics.Bitmap;

import com.example.roopalk.parsetagram.model.Post;

import java.io.File;

public interface onFragmentInteractionListener
{
    public void moveToDetailsPage(Post post);

    public void moveToPostPage(Bitmap bitmap, File file);

    public void moveToTimeline();
}
