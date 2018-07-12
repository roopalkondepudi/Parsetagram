package com.example.roopalk.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.parsetagram.model.Post;

import org.parceler.Parcel;
import org.parceler.Parcels;

import butterknife.BindView;

@Parcel
public class PostDetailsActivity extends AppCompatActivity
{

    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.ivPostImage) ImageView ivPostImage;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
    @BindView(R.id.ivLike) ImageView ivLike;
    @BindView(R.id.ivComment) ImageView ivComment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_post);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        String description = post.getKeyDescription();
        String imagePath = post.getKeyImage().getUrl();
        String username = post.getKeyUser().getUsername();

        tvDescription.setText(description);
        tvUsername.setText(username);

        GlideApp.with(this)
                .load(imagePath)
                .into(ivPostImage);
    }
}
