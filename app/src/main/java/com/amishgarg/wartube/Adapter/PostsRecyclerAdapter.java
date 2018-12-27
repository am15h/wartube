package com.amishgarg.wartube.Adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amishgarg.wartube.GlideUtil;
import com.amishgarg.wartube.Model.Post;
import com.amishgarg.wartube.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostsRecyclerAdapter.PostViewHolder>{

    private List<Post> postList;
    private Context context;
    private int rowLayout = R.layout.posts_item;


    public PostsRecyclerAdapter() {
    }

    public PostsRecyclerAdapter(List<Post> postList) {
        this.postList = postList;
    }

    public void submitList(List<Post> postList){
        this.postList = postList;
    }
    public static class PostViewHolder extends RecyclerView.ViewHolder
    {

        ImageView authorIcon;
        TextView authorName;
        TextView timestamp;
        ImageView postPhoto;
        TextView postText;
        TextView postNumLikes;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            authorIcon = itemView.findViewById(R.id.post_author_icon);
            authorName = itemView.findViewById(R.id.post_author_name);
            timestamp = itemView.findViewById(R.id.post_timestamp);
            postPhoto = itemView.findViewById(R.id.post_photo);
            postText = itemView.findViewById(R.id.post_text);
            postNumLikes = itemView.findViewById(R.id.post_num_likes);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
//        holder.movieTitle.setText(movies.get(position).getTitle());
//        holder.data.setText(movies.get(position).getReleaseDate());
//        holder.movieDescription.setText(movies.get(position).getOverview());
//        holder.rating.setText(movies.get(position).getVoteAverage().toString());

      GlideUtil.loadImagePicasso(postList.get(position).getFull__url(), holder.postPhoto);
        GlideUtil.loadImagePicasso(postList.get(position).getAuthor().getProfile_pic(), holder.authorIcon);
         holder.authorName.setText(postList.get(position).getAuthor().getDisplay_name());
        holder.postText.setText(postList.get(position).getText());
        holder.timestamp.setText(postList.get(position).getTimestamp().toString());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
