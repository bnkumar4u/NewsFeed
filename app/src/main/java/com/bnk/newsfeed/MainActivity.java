package com.bnk.newsfeed;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsData>>{


    private static final int EARTHQUAKE_lOADER_ID=0;
    private static final String REQUEST_URL="https://newsapi.org/v2/top-headlines?country=in&apiKey=4da647159ad049a0b1d45a4a25b5c346";
    public static final String LOG_TAG = MainActivity.class.getName();
    ListView mListView;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter= new NewsAdapter(this, new ArrayList<NewsData>());

        mListView=(ListView)findViewById(R.id.list_view);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                NewsData currentData=(NewsData) mListView.getItemAtPosition(i);
                String url=currentData.getWebUrl();

                Intent intent=new Intent(MainActivity.this,WebSiteActivity.class);
                intent.putExtra("url_to_load",url);
                startActivity(intent);
                //Toast.makeText(getBaseContext(),"hello"+i,Toast.LENGTH_SHORT).show();
            }
        });



        ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
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
            loaderManager.initLoader(EARTHQUAKE_lOADER_ID, null, this);
        }
    }


    @Override
    public Loader<ArrayList<NewsData>> onCreateLoader(int i, Bundle bundle) {

        return new NewsLoader(this,REQUEST_URL.trim());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsData>> loader, ArrayList<NewsData> newsDatas) {
        if(adapter!=null)
            adapter.clear();
        if(newsDatas!=null && !newsDatas.isEmpty())
        {
            adapter.addAll(newsDatas);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsData>> loader) {
        adapter.clear();;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_news,menu);
        MenuItem item=menu.findItem(R.id.search_menu);

        SearchView searchView=(SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //TODO
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
