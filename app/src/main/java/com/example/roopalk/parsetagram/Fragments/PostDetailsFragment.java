package com.example.roopalk.parsetagram.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.parsetagram.Adapter.PostAdapter;
import com.example.roopalk.parsetagram.GlideApp;
import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailsFragment extends Fragment {
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivPostImage)
    ImageView ivPostImage;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvTimeStamp)
    TextView tvTimeStamp;
    @BindView(R.id.ivLike)
    ImageView ivLike;
    @BindView(R.id.ivComment)
    ImageView ivComment;

    public PostDetailsFragment() {
        // Required empty public constructor
    }

    public static PostDetailsFragment newInstance(Post post) {
        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
        Bundle currPost = new Bundle();
        currPost.putParcelable("post", post);
        postDetailsFragment.setArguments(currPost);
        return postDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Post post = getArguments().getParcelable("post");

        if(post.favorited)
        {
            ivLike.setImageResource(R.drawable.ic_favorite_filled_24dp);
        }

        tvDescription.setText(post.getKeyDescription());
        tvUsername.setText(post.getKeyUser().getUsername());
        tvTimeStamp.setText(PostAdapter.getDate(post.getCreatedAt()));

        GlideApp.with(this)
                .load(post.getKeyImage().getUrl())
                .into(ivPostImage);
    }
}
