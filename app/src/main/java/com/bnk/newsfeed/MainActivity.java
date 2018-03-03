package com.bnk.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    public static Context m1Context;
    private static final String REQUEST_URL="https://newsapi.org/v2/top-headlines?country=in&apiKey=4da647159ad049a0b1d45a4a25b5c346";
    public static final String LOG_TAG = MainActivity.class.getName();


    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        m1Context=this;

        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

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

                Intent intent=new Intent(m1Context,SearchQueryActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch (id)
        {
            case R.id.news_item:
                Toast.makeText(this,"News",Toast.LENGTH_LONG).show();
                break;
            case R.id.category_item:
                Intent intent=new Intent(this,AboutHelpActivity.class);
                intent.putExtra("matter",getString(R.string.category_info));
                startActivity(intent);
                break;

            case R.id.about_app_item:
                Intent intent1=new Intent(this,AboutHelpActivity.class);
                intent1.putExtra("matter",getString(R.string.about_app_info));
                startActivity(intent1);
                break;
            case R.id.help_item:
                Intent intent2=new Intent(this,AboutHelpActivity.class);
                intent2.putExtra("matter",getString(R.string.help_info));
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
