package com.d.candy.f.awesometimetable.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by daichi on 7/20/17.
 */

public class RecyclerViewScrollObserver {

    public enum ScrollDirection {
        SCROLL_UP,
        SCROLL_DOWN,
        SCROLL_IDLE
    }

    public interface MessageListener {
        void onScrollDirectionChanged(ScrollDirection newDirection, ScrollDirection oldDirection);
    }

    private static final String TAG = LogHelper.makeLogTag(RecyclerViewScrollObserver.class);
    private final RecyclerView mRecyclerView;
    private ScrollDirection mDirection = ScrollDirection.SCROLL_IDLE;
    private MessageListener mMessageListener = null;
    private int mIgnorableScrollDistance = 0;


    public RecyclerViewScrollObserver(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException(
                    "recyclerView is a null object");
        }

        mRecyclerView = recyclerView;
        initOnScrollListener();
    }

    public void setMessageListener(MessageListener messageListener) {
        mMessageListener = messageListener;
    }

    public void setIgnorableScrollDistance(int distance) {
        if(distance < 0) {
            throw new IllegalArgumentException(
                    "'distance' must be a positive number or 0!");
        }

        mIgnorableScrollDistance = distance;
    }

    private void initOnScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                checkScrollDirectionChange(dy);
            }
        });
    }

    private void checkScrollDirectionChange(final int dy) {
        ScrollDirection direction = mDirection;
        if (dy > 0 && dy > mIgnorableScrollDistance) {
            direction = ScrollDirection.SCROLL_UP;
        } else if (0 > dy && -dy > mIgnorableScrollDistance) {
            direction = ScrollDirection.SCROLL_DOWN;
        }

        if (mDirection != direction) {
            mMessageListener.onScrollDirectionChanged(direction, mDirection);
            mDirection = direction;
        }
    }
}
