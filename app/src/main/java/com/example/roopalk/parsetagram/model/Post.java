package com.example.roopalk.parsetagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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

    public ParseFile getKeyImage()
    {
        return getParseFile(KEY_IMAGE);
    }

    public void setKeyImage(ParseFile keyImage)
    {
        put(KEY_IMAGE, keyImage);
    }
    public ParseUser getKeyUser()
    {
        return getParseUser(KEY_USER);
    }

    public void setKeyUser(ParseUser keyUser)
    {
        put(KEY_USER, keyUser);
    }

    public static class Query extends ParseQuery<Post>
    {
        public Query()
        {
            super(Post.class);
        }

        public Query getTop()
        {
            setLimit(20);
            return this;
        }

        public Query withUser()
        {
            include("user");
            return this;
        }
    }

}
