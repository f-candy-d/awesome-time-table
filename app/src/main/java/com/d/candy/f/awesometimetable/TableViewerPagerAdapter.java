package com.d.candy.f.awesometimetable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.ui.EntityCardListViewerFragment;

import java.util.ArrayList;

/**
 * Created by daichi on 7/21/17.
 */

public class TableViewerPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<EntityCardListViewerFragment> mFragments;
    private EntityCardListViewerFragment mCurrentFragment;

    public TableViewerPagerAdapter(FragmentManager fragmentManager, ArrayList<EntityCardListViewerFragment> fragments) {
        super(fragmentManager);

        mFragments = new ArrayList<>(fragments);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = (EntityCardListViewerFragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public EntityCardListViewerFragment getCurrentFragment() {
        return mCurrentFragment;
    }

}
