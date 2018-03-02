package com.bnk.newsfeed;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SimplePagerAdapter extends FragmentPagerAdapter{

    private Context mContext;
    public SimplePagerAdapter( Context context,FragmentManager fm) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HeadlinesFragment();
            case 1:
                return new BusinessFragment();
            case 2:
                return new EntertainmentFragment();
            case 3:
                return new HealthFragment();
            case 4:
                return new GeneralFragment();
            case 5:
                return new ScienceFragment();
            case 6:
                return new SportsFragment();
            case 7:
                return new TechnologyFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Headlines";
            case 1:
                return "Business";
            case 2:
                return "Entertainment";
            case 3:
                return "Health";
            case 4:
                return "General";
            case 5:
                return "Science";

            case 6:
                return "Sports";
            case 7:
                return "Tech";
        }
        return null;
    }
}
