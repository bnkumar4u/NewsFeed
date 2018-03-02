package com.bnk.newsfeed;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<NewsData>>{

    private String mUrl;
    public NewsLoader(Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<NewsData> loadInBackground() {
        if (mUrl==null)
            return null;
        ArrayList<NewsData> news=QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
