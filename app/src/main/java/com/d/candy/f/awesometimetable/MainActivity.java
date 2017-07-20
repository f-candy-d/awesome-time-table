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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;
import com.d.candy.f.awesometimetable.ui.SubjectDetailsActivity;
import com.d.candy.f.awesometimetable.ui.WeeklyTimeTableFragment;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;
import com.d.candy.f.awesometimetable.utils.LogHelper;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        WeeklyTimeTableFragment.InteractionListener {

    private static final String TAG = LogHelper.makeLogTag(MainActivity.class);
    private int mCheckedItemID = -1;
    private AHBottomNavigation mBottomNavigation;

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

        // Start with WeeklyTimeTableFragment
        // The following code make a bug that beyond me on orientation change...
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, new WeeklyTimeTableFragment()).commit();
        WeeklyTimeTableFragment fragment = WeeklyTimeTableFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
        navigationView.setCheckedItem(R.id.nav_table1);

        // Setup BottomNavigationBar
        initBottomNavigationBar();
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
            switchFragments(new WeeklyTimeTableFragment());
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

    private void switchFragments(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        // To turn back navigation on, enable this code
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initBottomNavigationBar() {
        mBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_bar);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(
                R.string.app_name, R.drawable.ic_menu_camera, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(
                R.string.action_settings, R.drawable.ic_menu_gallery, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(
                R.string.day_of_week_friday, R.drawable.ic_menu_slideshow, R.color.md_teal_500);

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
//        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
//        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

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
                // Do something cool here...
                return true;
            }
        });
        mBottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }

    /**
     * Implementation of WeeklyTimeTableFragment.InteractionListener interface
     */
    @Override
    public void onLaunchSubjectDetailsViewer(EnrollingInfo enrollingInfo) {
        Intent intent = new Intent(this, SubjectDetailsActivity.class);
        intent.putExtra(EXTRA_ENROLLING_INFO_ID, enrollingInfo.getID());
        startActivity(intent);
    }

    @Override
    public void onListViewScrolled(RecyclerViewScrollObserver.ScrollDirection direction) {
        if (direction == RecyclerViewScrollObserver.ScrollDirection.SCROLL_DOWN
                && mBottomNavigation.isHidden()) {
            mBottomNavigation.restoreBottomNavigation(true);
        } else if (direction == RecyclerViewScrollObserver.ScrollDirection.SCROLL_UP
                && mBottomNavigation.isShown()) {
            mBottomNavigation.hideBottomNavigation(true);
        }
    }

    @Override
    public WeeklyTimeTable onRequireTimeTableData() {
        return DataStructureFactory.makeTimeTable(0);
    }
}
