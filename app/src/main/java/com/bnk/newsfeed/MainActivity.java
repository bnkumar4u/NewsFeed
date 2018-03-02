package com.bnk.newsfeed;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {


    public static Context m1Context;
    private static final String REQUEST_URL="https://newsapi.org/v2/top-headlines?country=in&apiKey=4da647159ad049a0b1d45a4a25b5c346";
    public static final String LOG_TAG = MainActivity.class.getName();
    ListView mListView;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m1Context=this;

        ViewPager viewPager=findViewById(R.id.view_pager);
        SimplePagerAdapter simplePagerAdapter=new SimplePagerAdapter(this,getSupportFragmentManager());

        viewPager.setAdapter(simplePagerAdapter);

        TabLayout tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);



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
