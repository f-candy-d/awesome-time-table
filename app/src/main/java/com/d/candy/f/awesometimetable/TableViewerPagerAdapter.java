package com.d.candy.f.awesometimetable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by daichi on 7/21/17.
 */

public class TableViewerPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;

    public TableViewerPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

}
