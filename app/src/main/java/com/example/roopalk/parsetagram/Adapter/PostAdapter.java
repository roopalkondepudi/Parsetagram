package com.example.roopalk.parsetagram.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.parsetagram.GlideApp;
import com.example.roopalk.parsetagram.PostDetailsActivity;
import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
{
    Context context;
    List<Post> posts;

    public PostAdapter(List<Post> posts_)
    {
        posts = posts_;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position)
    {
        Post post = posts.get(position);

        holder.tvUsername.setText(post.getKeyUser().getUsername());
        holder.tvDescription.setText(post.getKeyDescription());
        GlideApp.with(context)
                .load(post.getKeyImage().getUrl())
                .into(holder.ivPostImage);

       // Log.i("whatever", post.getKeyImage().getUrl());
    }

    @Override
    public int getItemCount()
    {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
        @BindView(R.id.ivPostImage) ImageView ivPostImage;
        @BindView(R.id.ivLike) ImageView ivLike;
        @BindView(R.id.ivComment) ImageView ivComment;
        @BindView(R.id.tvDescription) TextView tvDescription;

        public ViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION)
            {
                if(v.getId() == R.id.ivLike)
                {

                }
                else if (v.getId() == R.id.ivComment)
                {

                }
                else
                {
                    Post post = posts.get(position);
                    Intent detailsIntent = new Intent(context, PostDetailsActivity.class);
                    detailsIntent.putExtra("post", Parcels.wrap(post));
                    context.startActivity(detailsIntent);
                }
            }
        }
    }

}
