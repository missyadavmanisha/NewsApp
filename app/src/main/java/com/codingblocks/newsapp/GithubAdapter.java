package com.codingblocks.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.GithubHolder> {

    private ArrayList<News> items;
    private Context ctx;

    public GithubAdapter(ArrayList<News> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public GithubHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ctx=viewGroup.getContext();

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);

        return new GithubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubHolder githubHolder, int i) {
        News currentItem = items.get(i);
        githubHolder.userName.setText(currentItem.getTitle());
        Picasso.get().load(currentItem.getUrlToImage()).into(githubHolder.userImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class GithubHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName;

        public GithubHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    News news=items.get(getAdapterPosition());
                    Intent intent=new Intent(ctx,OtherActivity.class);
                    intent.putExtra("publishedAt",news.getPublishedAt());
                    intent.putExtra("title",news.getTitle());
                    intent.putExtra("author",news.getAuthor());
                    intent.putExtra("urlToimage",news.getUrlToImage());
                    intent.putExtra("description",news.getDescription());
                    intent.putExtra("url",news.getUrl());
                    ctx.startActivity(intent);

                }
            });

            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
        }
    }

}
