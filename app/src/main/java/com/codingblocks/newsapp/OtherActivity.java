package com.codingblocks.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OtherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        if(getIntent()!=null)
        {
            TextView title=findViewById(R.id.title);
            TextView publishedat=findViewById(R.id.publishedat);
            TextView author=findViewById(R.id.author);
            TextView description=findViewById(R.id.description);
            ImageView imageView=findViewById(R.id.image);
            final TextView url1=findViewById(R.id.url);
            title.setText(getIntent().getStringExtra("title"));
            publishedat.setText(getIntent().getStringExtra("publishedAt"));
            author.setText(getIntent().getStringExtra("author"));
            description.setText(getIntent().getStringExtra("description"));
            url1.setText(getIntent().getStringExtra("url"));
            url1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(getIntent().getStringExtra("url")));
                    startActivity(i);
                }
            });
            Picasso.get().load(getIntent().getStringExtra("urlToimage")).into(imageView);


        }


    }
}
