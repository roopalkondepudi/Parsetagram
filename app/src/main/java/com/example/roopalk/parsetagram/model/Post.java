package com.example.roopalk.parsetagram.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Post")
public class Post extends ParseObject
{
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";

    public String getKeyDescription()
    {
        return getString(KEY_DESCRIPTION);
    }

    public void setKeyDescription(String description)
    {
        put(KEY_DESCRIPTION, description);
    }

    public String getKeyImage()
    {
        return getString(KEY_IMAGE);
    }

    public void setKeyImage(String keyImage)
    {
        put(KEY_IMAGE, keyImage);
    }
    public String getKeyUser()
    {
        return KEY_USER;
    }

    public void setKeyUser(String keyUser)
    {
        put(KEY_USER, keyUser);
    }

}
