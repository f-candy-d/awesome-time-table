package com.d.candy.f.awesometimetable.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.structure.MyVH;
import com.d.candy.f.awesometimetable.structure.OneDayTimeTable;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

/**
 * Created by daichi on 7/22/17.
 */

public class DaySubjectCardAdapter extends EntityCardAdapter {

    @NonNull private OneDayTimeTable mTimeTable;

    public DaySubjectCardAdapter(
            @NonNull final WeeklyTimeTable timeTable,
            @NonNull final OneDayTimeTable oneDayTimeTable) {
//        this(timeTable, null);
        this(timeTable, oneDayTimeTable, null);
    }

    public DaySubjectCardAdapter(
            @NonNull final WeeklyTimeTable timeTable,
            @NonNull final OneDayTimeTable oneDayTimeTable,
            @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {

//        super(onItemClickListener);
        super(timeTable, onItemClickListener);

        // noinspection ConstantConditions
        if (timeTable == null) {
            throw new NullPointerException();
        }

        mTimeTable = oneDayTimeTable;
        init();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyVH.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyVH.BaseViewHolder holder, int position) {

    }

    private void init() {

    }
}
