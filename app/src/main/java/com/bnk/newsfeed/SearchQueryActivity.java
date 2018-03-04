package com.bnk.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchQueryActivity extends AppCompatActivity {

    private static String query;
    public static  String REQUEST_URL="https://newsapi.org/v2/everything?q="+query+"&apiKey=4da647159ad049a0b1d45a4a25b5c346";
    ListView mListView;
    NewsAdapter adapter;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_query);

        Toolbar toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView tv_no_internet=findViewById(R.id.no_internet);
        mListView=findViewById(R.id.query_listview);
        adapter=new NewsAdapter(this,new ArrayList<NewsData>());
        pb=findViewById(R.id.query_progress);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsData currentNews=(NewsData) adapter.getItem(i);
                String url=currentNews.getWebUrl();
                Intent intent=new Intent(view.getContext(),WebSiteActivity.class);
                intent.putExtra("url_to_load",url);
                startActivity(intent);
            }
        });

        query=getIntent().getExtras().getString("query").trim();

        String REQUEST_URL_START="https://newsapi.org/v2/everything?";
        String uri=Uri.parse(REQUEST_URL_START).buildUpon()
                .appendQueryParameter("q",query)
                .appendQueryParameter("apiKey","4da647159ad049a0b1d45a4a25b5c346").toString();

        ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details on currently active default data network
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if(networkInfo!=null&&networkInfo.isConnected())
        {
            NewsAyncTask newsAyncTask=new NewsAyncTask();
            newsAyncTask.execute(uri);
        }else tv_no_internet.setVisibility(View.VISIBLE);

    }

    private class NewsAyncTask extends AsyncTask<String,Void,ArrayList<NewsData>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<NewsData> newsDatas) {
            super.onPostExecute(newsDatas);
            pb.setVisibility(View.GONE);
            if(adapter!=null)
                adapter.clear();
            if(newsDatas!=null && !newsDatas.isEmpty())
            {
                adapter.addAll(newsDatas);
            }
        }

        @Override
        protected ArrayList<NewsData> doInBackground(String... strings) {
            if (strings[0]==null)
                return null;
            ArrayList<NewsData> news=QueryUtils.fetchNewsData(strings[0]);
            return news;

        }
    }
}
