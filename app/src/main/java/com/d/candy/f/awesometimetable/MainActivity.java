/**
 * AHBottomNavigation library for Android
 Copyright (c) 2017 Aurelien Hubert (http://github.com/aurelhubert).

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.d.candy.f.awesometimetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;
import com.d.candy.f.awesometimetable.ui.EntityCardListViewerFragment;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;
import com.d.candy.f.awesometimetable.utils.LogHelper;
import com.d.candy.f.awesometimetable.utils.RecyclerViewScrollObserver;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        EntityCardListViewerFragment.OnInteractionListener {

    /**
     * Fragment types
     */
    private static final int FRAGMENT_WEEKLY_TIMETABLE = 0;
    private static final int FRAGMENT_ONE_DAY_TIMETABLE = 1;
    private static final int FRAGMENT_ASSIGNMENTS = 2;
    private static final int FRAGMENT_NOTIFICATIONS = 3;

    private static final String TAG = LogHelper.makeLogTag(MainActivity.class);
    private int mCheckedItemID;
    // UI
    private AHBottomNavigation mBottomNavigation;
    private EntityCardListViewerFragment mCurrentFragment;
    private TableViewerPagerAdapter mViewerPagerAdapter;
    private AHBottomNavigationViewPager mViewPager;

    // Data
    private WeeklyTimeTable mTimeTable;

    /**
     * Key strings being used in the Intent
     */
    public static final String EXTRA_ENROLLING_INFO_ID = "enrolling_info_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Setup BottomNavigationBar
        initBottomNavigationBar();

        // Init data
        initTimeTable();

        // ViewPager
        ArrayList<EntityCardListViewerFragment> fragments = new ArrayList<>();
        // Add fragments to the ViewPager
        EntityCardListViewerFragment fragment = EntityCardListViewerFragment.newInstance();
        fragment.setTimeTable(mTimeTable);
        fragment.setID(FRAGMENT_WEEKLY_TIMETABLE);
        fragments.add(fragment);

        EntityCardListViewerFragment fragment1 = EntityCardListViewerFragment.newInstance();
        fragment1.setTimeTable(mTimeTable);
        fragment1.setID(FRAGMENT_ASSIGNMENTS);
        fragments.add(fragment1);

        EntityCardListViewerFragment fragment2 = EntityCardListViewerFragment.newInstance();
        fragment2.setTimeTable(mTimeTable);
        fragment2.setID(FRAGMENT_WEEKLY_TIMETABLE);
        fragments.add(fragment2);

        mViewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager_main);
        mViewPager.setOffscreenPageLimit(3);
        mViewerPagerAdapter = new TableViewerPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mViewerPagerAdapter);
        mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();

        // The following code make a bug that beyond me on orientation change...
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, new WeeklyTimeTableFragment()).commit();
        // TODO: This is Test Code
//        mCurrentFragment = EntityCardListViewerFragment.newInstance();
//        mCurrentFragment.setTimeTable(mTimeTable);
//        mCurrentFragment.setID(0);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, mCurrentFragment).commit();

        // Set the initial position of the NavigationView
        navigationView.setCheckedItem(R.id.nav_table1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_table1 && id != mCheckedItemID) {
            mCurrentFragment.refresh();
        } else if (id == R.id.nav_table2 && id != mCheckedItemID) {

        } else if (id == R.id.nav_subjects) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // update
        mCheckedItemID = id;
        return true;
    }

    private void initBottomNavigationBar() {
        mBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_bar);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(
                "Week", R.drawable.ic_view_week, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(
                "Assignments", R.drawable.ic_assignment, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(
                "Notification", R.drawable.ic_notifications_active, R.color.md_teal_500);

        // Add items
        mBottomNavigation.addItem(item1);
        mBottomNavigation.addItem(item2);
        mBottomNavigation.addItem(item3);

// Set background color
//        mBottomNavigation.setDefaultBackgroundColor(
//                getResources().getColor(R.color.md_green_500));

// Disable the translation inside the CoordinatorLayout
//        mBottomNavigation.setBehaviorTranslationEnabled(false);


// Enable the translation of the FloatingActionButton
//        mBottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

// Change colors
        mBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mBottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBottomNavigationInactive));

// Force to tint the drawable (useful for font with icon for example)
//        mBottomNavigation.setForceTint(true);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
//        mBottomNavigation.setTranslucentNavigationEnabled(false);

// Manage titles
        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
//        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
//        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Use colored navigation with circle reveal effect
//        mBottomNavigation.setColored(true);

// Set current item programmatically
        mBottomNavigation.setCurrentItem(0);

// Customize notification (title, background, typeface)
//        mBottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

// Add or remove notification for each item
//        mBottomNavigation.setNotification("1", 2);
// OR
        AHNotification notification = new AHNotification.Builder()
                .setText("1")
                .setBackgroundColor(ContextCompat.getColor(this, R.color.cardview_light_background))
                .setTextColor(ContextCompat.getColor(this, R.color.cardview_dark_background))
                .build();
        mBottomNavigation.setNotification(notification, 1);

// Set listeners
        mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (mCurrentFragment == null) {
                    mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();
                }

                if (wasSelected) {
                    mCurrentFragment.refresh();
                    return true;
                }
                // Switch fragments
                mViewPager.setCurrentItem(position, false);
                mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();

                return true;
            }
        });
        mBottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }

    private void initTimeTable() {
        // TODO; This is TEST code
        mTimeTable = DataStructureFactory.makeTimeTable(0);
    }

    /**
     * Implementation of EntityCardListViewerFragment.OnInteractionListener interface
     */
    @Override
    public EntityCardAdapter getListAdapter(final EntityCardListViewerFragment fragment) {
        switch (fragment.getID()) {

            case FRAGMENT_WEEKLY_TIMETABLE: {
                // TODO: test code
                // The order of shown items
                DayOfWeek[] order = {
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY,
                        DayOfWeek.FRIDAY
                };

                return new SubjectCardAndHeaderAdapter(mTimeTable, order);
            }

            case FRAGMENT_ASSIGNMENTS: {
                return new AssignmentCardAdapter(
                        DataStructureFactory.makeAllAssignments());
            }
        }

        return null;
    }

    @Override
    public void onListScrolled(RecyclerViewScrollObserver.ScrollDirection direction) {
        if (direction == RecyclerViewScrollObserver.ScrollDirection.SCROLL_DOWN
                && mBottomNavigation.isHidden()) {
            mBottomNavigation.restoreBottomNavigation(true);
        } else if (direction == RecyclerViewScrollObserver.ScrollDirection.SCROLL_UP
                && mBottomNavigation.isShown()) {
            mBottomNavigation.hideBottomNavigation(true);
        }
    }

    @Override
    public void onListItemClicked(int position) {
        if (mCurrentFragment.getID() == FRAGMENT_WEEKLY_TIMETABLE) {
            // TODO; test code
            SubjectCardAndHeaderAdapter sAhAdapter =
                    (SubjectCardAndHeaderAdapter) mCurrentFragment.getListAdapter();
            EnrollingInfo enrollingInfo = sAhAdapter.getEnrollingInfoAtPosition(position);
            if (enrollingInfo != null) {
                Intent intent = new Intent(this, SubjectDetailsActivity.class);
                intent.putExtra(EXTRA_ENROLLING_INFO_ID, enrollingInfo.getID());
                startActivity(intent);
            }
        }
    }

//    private void test(int year, int month, int day, int eE, int hour, int min) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day, hour, min);
//        long milli = calendar.getTimeInMillis();
//        Log.d(TAG, String.valueOf(year) + "/" + String.valueOf(month)+"/"+String.valueOf(day)
//                +"/"+String.valueOf(hour)+":"+String.valueOf(min)+" ("+String.valueOf(eE)+") => " +  String.valueOf(milli));
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTimeInMillis(milli);
//        int y = calendar1.get(Calendar.YEAR);
//        int m = calendar1.get(Calendar.MONTH);
//        int d = calendar1.get(Calendar.DATE);
//        int ee = calendar1.get(Calendar.DAY_OF_WEEK);
//        int h = calendar1.get(Calendar.HOUR);
//        int mim = calendar1.get(Calendar.MINUTE);
//        Log.d(TAG, String.valueOf(milli) + " => " + String.valueOf(y) + "/" + String.valueOf(m)+"/"+String.valueOf(d)
//                +"/"+String.valueOf(h)+":"+String.valueOf(mim)+" ("+String.valueOf(ee)+")");
//    }
}
