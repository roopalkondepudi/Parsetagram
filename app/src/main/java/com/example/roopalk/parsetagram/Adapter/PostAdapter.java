package com.example.roopalk.parsetagram.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.parsetagram.Fragments.onFragmentInteractionListener;
import com.example.roopalk.parsetagram.GlideApp;
import com.example.roopalk.parsetagram.R;
import com.example.roopalk.parsetagram.model.Post;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
{
    Context context;
    List<Post> posts;

    public onFragmentInteractionListener listener;

    public PostAdapter(List<Post> posts_, onFragmentInteractionListener listener)
    {
        posts = posts_;
        this.listener = listener;
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
        holder.tvTimeStamp.setText(getDate(post.getCreatedAt()));

        if(post.favorited)
        {
            holder.ivLike.setImageResource(R.drawable.ic_favorite_filled_24dp);
        }
        else
        {
            holder.ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        GlideApp.with(context)
                .load(post.getKeyImage().getUrl())
                .into(holder.ivPostImage);
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
            ivLike.setOnClickListener(this);
            ivComment.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();

            Post post = posts.get(position);

            if(position != RecyclerView.NO_POSITION)
            {
                Log.i("id", v.getId()+"");
                if(v.getId() ==  R.id.ivLike)
                {
                    ivLike.setImageResource(R.drawable.ic_favorite_filled_24dp);
                    post.liked(true);
                }
                else if (v.getId() == R.id.ivComment)
                {

                }
                else
                {
                    listener.moveToDetailsPage(post);
                }
            }
        }
    }
    public static String getDate(Date date)
    {
       return date.toString();
    }
}
