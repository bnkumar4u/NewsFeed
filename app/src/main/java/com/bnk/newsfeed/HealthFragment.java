package com.bnk.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class HealthFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<NewsData>>{


    private ProgressBar pb;
    private Context mContext;
    private static final String REQUEST_URL="https://newsapi.org/v2/top-headlines?country=in&apiKey=4da647159ad049a0b1d45a4a25b5c346&category=health";
    private static  int NEWS_lOADER_ID=75;
    ListView mListView;
    NewsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        mContext=view.getContext();
        pb=view.findViewById(R.id.health_progress);
        TextView tv_no_internet=view.findViewById(R.id.no_internet);

        adapter=new NewsAdapter(view.getContext(),new ArrayList<NewsData>());
        mListView=view.findViewById(R.id.health_listview);
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


        ConnectivityManager connMgr=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details on currently active default data network
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if(networkInfo!=null&&networkInfo.isConnected())
        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_lOADER_ID, null, this);
        }else tv_no_internet.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public Loader<ArrayList<NewsData>> onCreateLoader(int id, Bundle args) {
        pb.setVisibility(View.VISIBLE);
        return new NewsLoader(mContext,REQUEST_URL.trim());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsData>> loader, ArrayList<NewsData> newsDatas) {
        pb.setVisibility(View.GONE);
        if(adapter!=null)
            adapter.clear();
        if(newsDatas!=null && !newsDatas.isEmpty())
        {
            adapter.addAll(newsDatas);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsData>> loader) {
        adapter.clear();
    }





}
