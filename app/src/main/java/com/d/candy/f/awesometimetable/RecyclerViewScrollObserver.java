package com.d.candy.f.awesometimetable;

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

    private final RecyclerView mRecyclerView;
    private ScrollDirection mDirection = ScrollDirection.SCROLL_IDLE;
    private MessageListener mMessageListener = null;
    private int mOldDy = 0;


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

    private void initOnScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                observeScroll(dx, dy);
            }
        });
    }

    private void observeScroll(final int dx, final int dy) {
        ScrollDirection direction = ScrollDirection.SCROLL_IDLE;
        if (dy > 0) {
            direction = ScrollDirection.SCROLL_DOWN;
        } else if (0 > dy) {
            direction = ScrollDirection.SCROLL_UP;
        }

        if (mDirection != direction) {
            mMessageListener.onScrollDirectionChanged(direction, mDirection);
            mDirection = direction;
        }
    }
}
