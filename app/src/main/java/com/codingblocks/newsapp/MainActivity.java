package com.codingblocks.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<News> responseArrayList;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       url=" https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=9d58259ad387423fb35e7530352b4587";


//        ArrayList<News> news = new ArrayList<>();
//        //Assume this is onBind headlines
//        News currentNews = news.get(5);
//
//        String id = currentNews.getSource().getId();


                makeNetworkCall(url);


    }

    private void makeNetworkCall(String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

//                call.enqueue(this);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                "Sorry, the request failed! Please retry.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();

                responseArrayList = parseJson(result);

                //This will result in an error since response has already been consumed!
//                Log.e("TAG", response.body().string());

                //If I try to modify the TextView here, I won't be able to do so since
                //this is a different thread than my Main/UI thread

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        RecyclerView recyclerView = findViewById(R.id.rvUsers);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        GithubAdapter githubAdapter = new GithubAdapter(responseArrayList);

                        recyclerView.setAdapter(githubAdapter);
                        //This code runs in the Main Thread
                    }
                });

            }
        });
        //....
    }

    ArrayList<News> parseJson(String json) {

        ArrayList<News> news = new ArrayList<>();

        //Do parsing
        try {
            JSONObject root = new JSONObject(json);

            JSONArray jsonArray = root.getJSONArray("articles");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject currentObject = jsonArray.getJSONObject(i);

                //Prefer optX() instead of getX()
                String author = currentObject.optString("author");
                String title = currentObject.optString("title");
                String description = currentObject.optString("description");
                String url = currentObject.optString("url");
                String urlToImage = currentObject.optString("urlToImage");
                String published = currentObject.optString("publishedAt");

                JSONObject sourceJson = currentObject.getJSONObject("source");

                String id = sourceJson.optString("id");
                String name = sourceJson.optString("name");

                Source source = new Source(id, name);

                News currentNews = new News(author, title, description, url, urlToImage, published, source);

                news.add(currentNews);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("TAG", "parseJson: " + news.size());

        return news;
    }

}
