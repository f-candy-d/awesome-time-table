package com.d.candy.f.awesometimetable.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.Adapters.EntityCardAdapter;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.MyVH;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

import java.util.ArrayList;

/**
 * Created by daichi on 7/21/17.
 */

public class SubjectAndRelationsCardAdapter extends EntityCardAdapter {

    /**
     * View types
     */
    private static final int VIEW_TYPE_SUBJECT = 0;
    private static final int VIEW_TYPE_TEACHER = 1;
    private static final int VIEW_TYPE_LOCATION = 2;
    private static final int VIEW_TYPE_ASSIGNMENT = 3;


    @NonNull private final ArrayList<Entity> mEntities;

    public SubjectAndRelationsCardAdapter(
            @NonNull final WeeklyTimeTable timeTable,
            @NonNull final ArrayList<Entity> entities) {
        this(timeTable, entities, null);
    }

    public SubjectAndRelationsCardAdapter(
            @NonNull final WeeklyTimeTable timeTable,
            @NonNull final ArrayList<Entity> entities,
            @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {

        super(timeTable, onItemClickListener);

        // noinspection ConstantConditions
        if (entities == null) {
            throw new NullPointerException();
        }

        // Initialization
        mEntities = entities;
    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mEntities.get(position).getEntityType()) {
            case SUBJECT: return VIEW_TYPE_SUBJECT;
            case LOCATION: return VIEW_TYPE_LOCATION;
            case TEACHER: return VIEW_TYPE_TEACHER;
            case ASSIGNMENT: return VIEW_TYPE_ASSIGNMENT;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public MyVH.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == VIEW_TYPE_SUBJECT) {
            view = inflater.inflate(R.layout.subject_card, parent, false);
            return new MyVH.SubjectCardViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOCATION) {
            view = inflater.inflate(R.layout.location_card, parent, false);
            return new MyVH.LocationCardViewHolder(view);
        } else if (viewType == VIEW_TYPE_TEACHER) {
            view = inflater.inflate(R.layout.teacher_card, parent, false);
            return new MyVH.TeacherCardViewHolder(view);
        } else if (viewType == VIEW_TYPE_ASSIGNMENT) {
            view = inflater.inflate(R.layout.assignment_card, parent, false);
            return new MyVH.AssignmentCardViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(MyVH.BaseViewHolder holder, int position) {

        int adpPos = holder.getAdapterPosition();
        int viewType = getItemViewType(adpPos);

        if (viewType == VIEW_TYPE_SUBJECT) {
            MyVH.SubjectCardViewHolder vh = (MyVH.SubjectCardViewHolder) holder;
            vh.bind(adpPos, mEntities.get(adpPos), null);
        } else if (viewType == VIEW_TYPE_LOCATION) {
            MyVH.LocationCardViewHolder vh = (MyVH.LocationCardViewHolder) holder;
            vh.bind(adpPos, mEntities.get(adpPos), null);
        } else if (viewType == VIEW_TYPE_TEACHER) {
            MyVH.TeacherCardViewHolder vh = (MyVH.TeacherCardViewHolder) holder;
            vh.bind(adpPos, mEntities.get(adpPos), null);
        } else if(viewType == VIEW_TYPE_ASSIGNMENT) {
            MyVH.AssignmentCardViewHolder vh = (MyVH.AssignmentCardViewHolder) holder;
            vh.bind(adpPos, mEntities.get(adpPos), null);
        }
    }
}
