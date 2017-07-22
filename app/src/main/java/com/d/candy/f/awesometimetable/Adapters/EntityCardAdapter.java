package com.d.candy.f.awesometimetable.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.d.candy.f.awesometimetable.structure.MyVH;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

/**
 * Created by daichi on 7/21/17.
 */

abstract public class EntityCardAdapter extends RecyclerView.Adapter<MyVH.BaseViewHolder> {

//    @NonNull private WeeklyTimeTable mTimeTable;
    @Nullable private MyVH.BaseViewHolder.OnItemClickListener mOnItemClickListener = null;

//    public EntityCardAdapter(@NonNull final WeeklyTimeTable timeTable) {
//        this(timeTable, null);
//    }

    public EntityCardAdapter() {
        this(null);
    }

//    public EntityCardAdapter(@NonNull final WeeklyTimeTable timeTable,
//                             @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {
//        // noinspection ConstantConditions
//        if(timeTable == null) {
//            throw new NullPointerException();
//        }
//
//        mTimeTable = timeTable;
//        mOnItemClickListener = onItemClickListener;
//    }

    public EntityCardAdapter(
            @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public void setOnItemClickListener(
            @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public boolean isOnItemClickListenerNull() {
        return (mOnItemClickListener == null);
    }

//    @NonNull
//    public WeeklyTimeTable getTimeTable() {
//        return mTimeTable;
//    }

    @Nullable
    protected MyVH.BaseViewHolder.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }
}
