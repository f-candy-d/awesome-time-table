package com.d.candy.f.awesometimetable.utils;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

/**
 * Created by daichi on 7/23/17.
 */

public class AHBottomNavigationObserver
        implements AHBottomNavigation.OnNavigationPositionListener,
        AHBottomNavigation.OnTabSelectedListener {

    public interface NotificationListener {
        boolean onTabSelected(final int position, final boolean wasSelected);
        void onPositionChange(int y);
        void onNavigationStateChanged(State state);
    }

    public enum State {
        SHOWN,
        HIDDEN,
        APPEARING,
        DISAPPEARING,
        UNDETERMINED
    }

    private AHBottomNavigation mBottomNavigation;
    private int mOldPosY = -1;
    private NotificationListener mListener = null;
    private int mFlags = NULL_FLAG;
    private State mCurrentState = State.UNDETERMINED;

    /**
     * Bit flags
     */
    public static final int NULL_FLAG = 0;
    public static final int TAB_SELECTION = 1;
    public static final int POSITION_Y = 2;
    public static final int STATE = 4;

    public AHBottomNavigationObserver(final int flags) {
        this(flags, null);
    }

    public AHBottomNavigationObserver(final int flags, final AHBottomNavigation bottomNavigation) {
        mBottomNavigation = bottomNavigation;
        mFlags = flags;
        invalidateListeners(flags);
    }

    @Override
    public void onPositionChange(int y) {
        observeState(y);

        if (mListener != null && isContainFlag(mFlags, POSITION_Y)) {
            mListener.onPositionChange(y);
        }
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        return isContainFlag(mFlags, TAB_SELECTION) && mListener != null
                && mListener.onTabSelected(position, wasSelected);
    }

    public void setBottomNavigation(AHBottomNavigation bottomNavigation) {
        mBottomNavigation = bottomNavigation;
        invalidateListeners(mFlags);
    }

    public void setFlags(int flags) {
        mFlags = flags;
        invalidateListeners(flags);
    }

    public void setNotificationListener(NotificationListener notificationListener) {
        mListener = notificationListener;
    }

    private void observeState(final int posY) {
        if (isContainFlag(mFlags, STATE)) {
            boolean changeFlag = false;

            if (mCurrentState == State.APPEARING && posY == mBottomNavigation.getHeight()) {
                mCurrentState = State.SHOWN;
                changeFlag = true;

            }else if (mCurrentState == State.DISAPPEARING && posY == 0) {
                mCurrentState = State.HIDDEN;
                changeFlag = true;

            } else if (mOldPosY < posY) {
                mCurrentState = State.APPEARING;
                changeFlag = true;

            } else if (mOldPosY > posY) {
                mCurrentState = State.DISAPPEARING;
                changeFlag = true;
            }

            if (changeFlag && mListener != null) {
                mListener.onNavigationStateChanged(mCurrentState);
            }
        }

        mOldPosY = posY;
    }

    private boolean isContainFlag(int flags, int flag) {
        return ((flag & flags) != NULL_FLAG);
    }

    private void invalidateListeners(final int flags) {
        if (mBottomNavigation != null) {
            if (isContainFlag(flags, TAB_SELECTION)) {
                mBottomNavigation.setOnTabSelectedListener(this);
            }
            if (isContainFlag(flags, POSITION_Y | STATE)) {
                mBottomNavigation.setOnNavigationPositionListener(this);
            }
        }
    }
}
