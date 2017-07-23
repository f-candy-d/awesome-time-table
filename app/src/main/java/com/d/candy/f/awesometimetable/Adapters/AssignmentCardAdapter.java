package com.d.candy.f.awesometimetable.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.MyVH;
import com.d.candy.f.awesometimetable.structure.TimeTable;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

import java.util.ArrayList;

/**
 * Created by daichi on 7/21/17.
 */

public class AssignmentCardAdapter extends EntityCardAdapter {

    /**
     * View types
     */
    private static final int VIEW_TYPE_ASSIGNMENT = 0;
    private static final int VIEW_TYPE_HEADER = 1;

    @NonNull private ArrayList<Assignment> mAssignments;
    private SparseIntArray mHeaderPositions;

    public AssignmentCardAdapter(
            @NonNull final WeeklyTimeTable timeTable)
//            @NonNull ArrayList<Assignment> assignments) {
    {
//        this(timeTable, assignments, null);
        this(timeTable, null);
    }

    public AssignmentCardAdapter(
            @NonNull final WeeklyTimeTable timeTable,
//            @NonNull ArrayList<Assignment> assignments,
            @Nullable MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {
        super(timeTable, onItemClickListener);

//        // noinspection ConstantConditions
//        if (assignments == null) {
//            throw new NullPointerException();
//        }

//        mAssignments = assignments;
        mHeaderPositions = new SparseIntArray();
        mAssignments = timeTable.getAllAssignments();

        init();
    }

    @Override
    public int getItemCount() {
        return mAssignments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ASSIGNMENT;
    }

    @Override
    public MyVH.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_ASSIGNMENT: {
                View view = inflater.inflate(R.layout.assignment_card, parent, false);
                return new MyVH.AssignmentCardViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyVH.BaseViewHolder holder, int position) {
        int adpPos = holder.getAdapterPosition();
        int viewType = getItemViewType(adpPos);

        if (viewType == VIEW_TYPE_ASSIGNMENT) {
            MyVH.AssignmentCardViewHolder vh = (MyVH.AssignmentCardViewHolder) holder;
            vh.bind(position, mAssignments.get(adpPos), getOnItemClickListener());
        }
    }

    private void init() {
        // Calculate header positions
    }
}
