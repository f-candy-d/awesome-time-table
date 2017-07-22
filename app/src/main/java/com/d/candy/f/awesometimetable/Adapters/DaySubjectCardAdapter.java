package com.d.candy.f.awesometimetable.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.structure.MyVH;
import com.d.candy.f.awesometimetable.structure.OneDayTimeTable;

/**
 * Created by daichi on 7/22/17.
 */

public class DaySubjectCardAdapter extends EntityCardAdapter {

    @NonNull private OneDayTimeTable mTimeTable;

    public DaySubjectCardAdapter(@NonNull final OneDayTimeTable timeTable) {
        this(timeTable, null);
    }

    public DaySubjectCardAdapter(
            @NonNull final OneDayTimeTable timeTable,
            @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {

        super(onItemClickListener);

        // noinspection ConstantConditions
        if (timeTable == null) {
            throw new NullPointerException();
        }

        mTimeTable = timeTable;
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
