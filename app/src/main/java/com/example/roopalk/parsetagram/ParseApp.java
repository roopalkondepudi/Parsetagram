package com.example.roopalk.parsetagram;

import android.app.Application;

import com.example.roopalk.parsetagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        //setting up the parse server

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("fbu-parsetagram-app-id")
                .clientKey("F8U-P4R5374GR4M")
                .server("http://roopalk-fbu-parsetagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
