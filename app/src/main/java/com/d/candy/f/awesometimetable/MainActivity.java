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

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.d.candy.f.awesometimetable.Adapters.AssignmentCardAdapter;
import com.d.candy.f.awesometimetable.Adapters.EntityCardAdapter;
import com.d.candy.f.awesometimetable.Adapters.NotificationCardAdapter;
import com.d.candy.f.awesometimetable.Adapters.WeeklySubjectCardAdapter;
import com.d.candy.f.awesometimetable.Adapters.TableViewerPagerAdapter;
import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Notification;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;
import com.d.candy.f.awesometimetable.ui.EntityCardListViewerFragment;
import com.d.candy.f.awesometimetable.utils.AHBottomNavigationObserver;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;
import com.d.candy.f.awesometimetable.utils.EntityCache;
import com.d.candy.f.awesometimetable.utils.LogHelper;
import com.d.candy.f.awesometimetable.utils.RecyclerViewScrollObserver;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        EntityCardListViewerFragment.OnInteractionListener,
        AHBottomNavigationObserver.NotificationListener {

    /**
     * Fragment types.
     * These values are used as position of AHBottomNavigation.
     */
    private static final int FRAGMENT_ONE_DAY_TIMETABLE = 0;
    private static final int FRAGMENT_WEEKLY_TIMETABLE = 1;
    private static final int FRAGMENT_ASSIGNMENTS = 2;
    private static final int FRAGMENT_NOTIFICATIONS = 3;

    /**
     * Request codes. Use in {@link MainActivity#startActivityForResult(Intent, int)}
     * and {@link MainActivity#onActivityResult(int, int, Intent)}.
     */
    private static final int REQUEST_CODE_ADD_ASSIGNMENT = 1;
    private static final int REQUEST_CODE_ADD_NOTIFICATION = 2;

    /**
     * Misc
     */
    private static final int VIEWPAGER_OFFSCREEN_LIMIT = 3;
    private static final long FAB_ANIMATION_DURATION = 270L;
    private static final String TAG = LogHelper.makeLogTag(MainActivity.class);

    /**
     * Key strings being used in the Intent
     */
    public static final String EXTRA_ENROLLING_INFO_ID = "enrolling_info_id";

    /**
     * An ID of a checked item of the NavigationDrawer
     */
    private int mCheckedNavigationItemID;

    // TODO; This is used for 3Tabs bottom navigation
//    private int mBottomNaviPos0FragmentID;

    // UI
    private AHBottomNavigation mBottomNavigation;
    private EntityCardListViewerFragment mCurrentFragment;
    private AHBottomNavigationViewPager mViewPager;
    private FloatingActionButton mFab;
    private Toolbar mToolbar;

    // Utils
    private AHBottomNavigationObserver mAHBottomNavigationObserver;

    // Data
    private TableViewerPagerAdapter mViewerPagerAdapter;
    private WeeklyTimeTable mTimeTable;
    private EntityCache mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Initialization
        initNavDrawer();
        initFAB();
        initBottomNavigationBar();
        initTimeTable();
        initViewPager();
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

        if (id == R.id.nav_table1 && id != mCheckedNavigationItemID) {
            mCurrentFragment.refresh();
        } else if (id == R.id.nav_table2 && id != mCheckedNavigationItemID) {

        } else if (id == R.id.nav_subjects) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // update
        mCheckedNavigationItemID = id;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADD_ASSIGNMENT:
                if (resultCode == RESULT_OK) {

                }
        }
    }

    private void initNavDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the initial position of the NavigationView
        navigationView.setCheckedItem(R.id.nav_table1);
    }

    private void initBottomNavigationBar() {
        mBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation_bar);

        // Create items
        AHBottomNavigationItem item0 = new AHBottomNavigationItem(
                "Today", R.drawable.ic_view_day, R.color.colorPrimary);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(
                "Week", R.drawable.ic_view_week, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(
                "Assignments", R.drawable.ic_assignment, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(
                "Notifications", R.drawable.ic_notifications_active, R.color.md_teal_500);

        // Add items
        mBottomNavigation.addItem(item0);
        mBottomNavigation.addItem(item1);
        mBottomNavigation.addItem(item2);
        mBottomNavigation.addItem(item3);

        // Enable the translation of the FloatingActionButton
        mBottomNavigation.manageFloatingActionButtonBehavior(mFab);

        // Change colors
        mBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mBottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBottomNavigationInactive));

        // Show item title always
        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Set current item programmatically
        mBottomNavigation.setCurrentItem(FRAGMENT_ONE_DAY_TIMETABLE);

        // Notification
        AHNotification notification = new AHNotification.Builder()
                .setText("1")
                .setBackgroundColor(ContextCompat.getColor(this, R.color.cardview_light_background))
                .setTextColor(ContextCompat.getColor(this, R.color.cardview_dark_background))
                .build();
        mBottomNavigation.setNotification(notification, 3);

        // Setup observer
        int flags = AHBottomNavigationObserver.STATE | AHBottomNavigationObserver.TAB_SELECTION;
        mAHBottomNavigationObserver = new AHBottomNavigationObserver(flags, mBottomNavigation);
        mAHBottomNavigationObserver.setNotificationListener(this);
    }

    private void initTimeTable() {
        // TODO; This is TEST code
        mDataSet = new EntityCache();

        Subject math = DataStructureFactory.makeSubject(2);
        Subject japanese = DataStructureFactory.makeSubject(3);
        Subject english = DataStructureFactory.makeSubject(4);
        Subject physics = DataStructureFactory.makeSubject(5);
        Subject chemistry = DataStructureFactory.makeSubject(6);

        Location locA = DataStructureFactory.makeLocation(0);
        Location locB = DataStructureFactory.makeLocation(1);
        Location locC = DataStructureFactory.makeLocation(2);
        Location locD = DataStructureFactory.makeLocation(3);

        EnrollingInfo infoMon1 = DataStructureFactory.makeEnrollingInfo(1);
        EnrollingInfo infoMon3 = DataStructureFactory.makeEnrollingInfo(2);
        EnrollingInfo infoMon4 = DataStructureFactory.makeEnrollingInfo(3);
        EnrollingInfo infoMon5 = DataStructureFactory.makeEnrollingInfo(4);
        EnrollingInfo infoMon7 = DataStructureFactory.makeEnrollingInfo(5);

        EnrollingInfo infoTue1 = DataStructureFactory.makeEnrollingInfo(6);
        EnrollingInfo infoTue2 = DataStructureFactory.makeEnrollingInfo(7);
        EnrollingInfo infoTue6 = DataStructureFactory.makeEnrollingInfo(8);

        EnrollingInfo infoWed1 = DataStructureFactory.makeEnrollingInfo(9);
        EnrollingInfo infoWed2 = DataStructureFactory.makeEnrollingInfo(10);
        EnrollingInfo infoWed3 = DataStructureFactory.makeEnrollingInfo(11);
        EnrollingInfo infoWed4 = DataStructureFactory.makeEnrollingInfo(12);

        EnrollingInfo infoThu1 = DataStructureFactory.makeEnrollingInfo(13);
        EnrollingInfo infoThu2 = DataStructureFactory.makeEnrollingInfo(14);
        EnrollingInfo infoThu4 = DataStructureFactory.makeEnrollingInfo(15);
        EnrollingInfo infoThu6 = DataStructureFactory.makeEnrollingInfo(16);

        EnrollingInfo infoFri1 = DataStructureFactory.makeEnrollingInfo(17);

        ArrayList<Assignment> assignments = DataStructureFactory.makeAllAssignments();
        for (Assignment assignment : assignments) {
            mDataSet.cache(assignment, true);
        }

        ArrayList<Notification> notifications = DataStructureFactory.makeAllNotifications();
        for (Notification notification : notifications) {
            mDataSet.cache(notification, true);
        }

        // Register entities
        mDataSet.cache(math, true);
        mDataSet.cache(japanese, true);
        mDataSet.cache(english, true);
        mDataSet.cache(physics, true);
        mDataSet.cache(chemistry, true);

        mDataSet.cache(locA, true);
        mDataSet.cache(locB, true);
        mDataSet.cache(locC, true);
        mDataSet.cache(locD, true);

        mTimeTable = DataStructureFactory.makeTimeTable(0);
        mTimeTable.setDataSet(mDataSet);

        // Enroll subjects
        mTimeTable.enrollSubjectTo(infoMon1);
        mTimeTable.enrollSubjectTo(infoMon3);
        mTimeTable.enrollSubjectTo(infoMon4);
        mTimeTable.enrollSubjectTo(infoMon5);
        mTimeTable.enrollSubjectTo(infoMon7);

        mTimeTable.enrollSubjectTo(infoTue1);
        mTimeTable.enrollSubjectTo(infoTue2);
        mTimeTable.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        mTimeTable.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        mTimeTable.enrollSubjectTo(infoTue6);

        mTimeTable.enrollSubjectTo(infoWed1);
        mTimeTable.enrollSubjectTo(infoWed2);
        mTimeTable.enrollSubjectTo(infoWed3);
        mTimeTable.enrollSubjectTo(infoWed4);
        mTimeTable.enrollBlankSubjectTo(DayOfWeek.WEDNESDAY, 1);

        mTimeTable.enrollSubjectTo(infoThu1);
        mTimeTable.enrollSubjectTo(infoThu2);
        mTimeTable.enrollSubjectTo(infoThu4);
        mTimeTable.enrollSubjectTo(infoThu6);
        mTimeTable.enrollBlankSubjectTo(DayOfWeek.THURSDAY, 1);

        mTimeTable.enrollSubjectTo(infoFri1);
        mTimeTable.enrollBlankSubjectTo(DayOfWeek.FRIDAY, 2);
    }

    private void initViewPager() {
        // ViewPager
        ArrayList<EntityCardListViewerFragment> fragments = new ArrayList<>(4);

        // Add fragments to the ViewPager
        EntityCardListViewerFragment oneDayTTF = EntityCardListViewerFragment.newInstance();
        oneDayTTF.setID(FRAGMENT_ONE_DAY_TIMETABLE);
        fragments.add(FRAGMENT_ONE_DAY_TIMETABLE, oneDayTTF);
        // TODO; This is for 3Tabs bottom navigation
//        mBottomNaviPos0FragmentID = FRAGMENT_ONE_DAY_TIMETABLE;


        EntityCardListViewerFragment weeklyTTF = EntityCardListViewerFragment.newInstance();
        weeklyTTF.setID(FRAGMENT_WEEKLY_TIMETABLE);
        fragments.add(FRAGMENT_WEEKLY_TIMETABLE, weeklyTTF);

        EntityCardListViewerFragment assignmentF = EntityCardListViewerFragment.newInstance();
        assignmentF.setID(FRAGMENT_ASSIGNMENTS);
        fragments.add(FRAGMENT_ASSIGNMENTS, assignmentF);

        EntityCardListViewerFragment notificationF = EntityCardListViewerFragment.newInstance();
        notificationF.setID(FRAGMENT_NOTIFICATIONS);
        fragments.add(FRAGMENT_NOTIFICATIONS, notificationF);

        mViewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager_main);
        mViewPager.setOffscreenPageLimit(VIEWPAGER_OFFSCREEN_LIMIT);
        mViewerPagerAdapter = new TableViewerPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mViewerPagerAdapter);

        // Default fragment
        mViewPager.setCurrentItem(FRAGMENT_ONE_DAY_TIMETABLE, false);
        mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();
    }

    private void initFAB() {
        mFab = (FloatingActionButton) findViewById(R.id.fab_main);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Integer) v.getTag()) == FRAGMENT_ASSIGNMENTS) {
                    // Show add-Assignment screen
                    Intent intent = new Intent(MainActivity.this, EntityEditorActivity.class);
                    intent.putExtra(EntityEditorActivity.EXTRA_EDIT_ENTITY_TYPE, EntityType.ASSIGNMENT);
                    startActivityForResult(intent, REQUEST_CODE_ADD_ASSIGNMENT);

                } else if (((Integer) v.getTag()) == FRAGMENT_NOTIFICATIONS) {
                }
            }
        });
    }

    private void hideFAB(final boolean showAfterAnim) {
        // Hide FAB with animation
        mFab.animate().alpha(0).scaleX(0).scaleY(0).setDuration(FAB_ANIMATION_DURATION)
                .setInterpolator(new LinearOutSlowInInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFab.setVisibility(View.INVISIBLE);
                        if (showAfterAnim) {
                            // Show FAB again!
                            showFAB(false);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        mFab.setVisibility(View.INVISIBLE);
                        if (showAfterAnim) {
                            showFAB(false);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();

    }

    private void showFAB(final boolean hideAfterAnim) {
        // Show FAB with animation
        mFab.setVisibility(View.VISIBLE);
        mFab.setAlpha(0f);
        mFab.setScaleX(0f);
        mFab.setScaleY(0f);
        mFab.animate().alpha(1).scaleX(1).scaleY(1).setDuration(FAB_ANIMATION_DURATION)
                .setInterpolator(new OvershootInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (hideAfterAnim) {
                            // Hide FAB again!
                            hideFAB(false);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    private boolean onSwitchFragments(final int position, final boolean wasSelected) {
        // Avoid null pointer error
        if (mCurrentFragment == null) {
            mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();
        }

        // TODO; Consider using 3Tabs or 4Tabs. The following codes is for 3Tabs.
//        if (wasSelected) {
//            if (mCurrentFragment.getID() == FRAGMENT_WEEKLY_TIMETABLE) {
//                // Update
//                mBottomNaviPos0FragmentID = FRAGMENT_ONE_DAY_TIMETABLE;
//                switchFragments(FRAGMENT_ONE_DAY_TIMETABLE);
//
//                // Change AHBottomNavigationItem's drawable and title
//                AHBottomNavigationItem item = mBottomNavigation.getItem(0);
//                item.setDrawable(R.drawable.ic_view_day);
//                item.setTitle("Today");
//                mBottomNavigation.refresh();
//
//                return true;
//
//            } else if (mCurrentFragment.getID() == FRAGMENT_ONE_DAY_TIMETABLE) {
//                // Update
//                mBottomNaviPos0FragmentID = FRAGMENT_WEEKLY_TIMETABLE;
//                switchFragments(FRAGMENT_WEEKLY_TIMETABLE);
//
//                AHBottomNavigationItem item = mBottomNavigation.getItem(0);
//                item.setDrawable(R.drawable.ic_view_week);
//                item.setTitle("Week");
//                mBottomNavigation.refreshDrawableState();
//                mBottomNavigation.refresh();
//
//                return true;
//            }
//
//            mCurrentFragment.refresh();
//            return true;
//        }
//
//        switch (position) {
//            case 0: switchFragments(mBottomNaviPos0FragmentID); break;
//            case 1: switchFragments(FRAGMENT_ASSIGNMENTS); break;
//            case 2: switchFragments(FRAGMENT_NOTIFICATIONS); break;
//        }

        // TODO; The following code is for 4Tabs
        if (wasSelected) {
            mCurrentFragment.refresh();
        } else {
            switchFragments(position);
        }

        return true;
    }

    private void switchFragments(final int fragmentID) {
        mViewPager.setCurrentItem(fragmentID, false);
        mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();
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

                return new WeeklySubjectCardAdapter(mTimeTable, order);
            }

            case FRAGMENT_ONE_DAY_TIMETABLE: {
                // TODO; test code
                DayOfWeek[] order = { DayOfWeek.FRIDAY };
                return new WeeklySubjectCardAdapter(mTimeTable, order);
            }

            case FRAGMENT_ASSIGNMENTS: {
//                return new AssignmentCardAdapter(
//                        mTimeTable,
//                        DataStructureFactory.makeAllAssignments());
                return new AssignmentCardAdapter(mTimeTable);
            }

            case FRAGMENT_NOTIFICATIONS: {
                // TODO; test code
                return new NotificationCardAdapter(
                        mTimeTable,
                        DataStructureFactory.makeAllNotifications());
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
        if (mCurrentFragment == null) {
            mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();
        }

        if (mCurrentFragment.getID() == FRAGMENT_WEEKLY_TIMETABLE) {
            // TODO; test code
            WeeklySubjectCardAdapter sAhAdapter =
                    (WeeklySubjectCardAdapter) mCurrentFragment.getListAdapter();
            EnrollingInfo enrollingInfo = sAhAdapter.getEnrollingInfoAtPosition(position);
            if (enrollingInfo != null) {
                Intent intent = new Intent(this, SubjectDetailsActivity.class);
                intent.putExtra(EXTRA_ENROLLING_INFO_ID, enrollingInfo.getID());
                startActivity(intent);
            }
        }
    }

    /**
     * The implementation of AHBottomNavigationObserver.NotificationListener interface
     */

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        // Hide or show FAB
        if (!wasSelected) {
            if (position == FRAGMENT_ASSIGNMENTS || position == FRAGMENT_NOTIFICATIONS) {
                // If FAB's visibility is VISIBLE, the previous position is 2 or 3.
                // So the first thing we do is to hide FAB with animation,
                // and then show it again.
                // If FAB's visibility is GONE, the previous position is 0 or 1.
                // In this case, simply show FAB with animation.
                if (mFab.getVisibility() == View.VISIBLE) {
                    hideFAB(true);
                } else if (mFab.getVisibility() == View.INVISIBLE) {
                    showFAB(false);
                }
                mFab.setTag(position);

            } else {
                hideFAB(false);
            }
        }

        return onSwitchFragments(position, wasSelected);
    }

    @Override
    public void onPositionChange(int y) {
        // Nothing to do for now...
    }

    @Override
    public void onNavigationStateChanged(AHBottomNavigationObserver.State state) {
        if (mCurrentFragment == null) {
            mCurrentFragment = mViewerPagerAdapter.getCurrentFragment();
        }

        int id = mCurrentFragment.getID();
        if (id == FRAGMENT_ASSIGNMENTS || id == FRAGMENT_NOTIFICATIONS) {
            if (state == AHBottomNavigationObserver.State.SHOWN
                    && mFab.getVisibility() == View.INVISIBLE) {
                // Show FAB after BottomNavigation appeared
                showFAB(false);

            } else if (state == AHBottomNavigationObserver.State.HIDDEN
                    && mFab.getVisibility() == View.VISIBLE) {
                // Hide FAB after BottomNavigation disappeared
                hideFAB(false);
            }
        }
    }
}
