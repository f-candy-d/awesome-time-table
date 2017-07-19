package com.d.candy.f.awesometimetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.structure.Entity;

import java.util.ArrayList;

/**
 * Created by daichi on 7/19/17.
 */

public class SubjectDetailsCardAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * View types
     */
    private static final int VIEW_TYPE_SUBJECT = 0;
    private static final int VIEW_TYPE_TEACHER = 1;
    private static final int VIEW_TYPE_LOCATION = 2;
    private static final int VIEW_TYPE_ASSIGNMENT = 3;

    private ArrayList<Entity> mEntities;

    public SubjectDetailsCardAdapter(ArrayList<Entity> entities) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int adpPos = holder.getAdapterPosition();
        int viewType = getItemViewType(adpPos);

        if (viewType == VIEW_TYPE_SUBJECT) {
            MyVH.SubjectCardViewHolder vh = (MyVH.SubjectCardViewHolder) holder;
            vh.bind(mEntities.get(adpPos), null);
        } else if (viewType == VIEW_TYPE_LOCATION) {
            MyVH.LocationCardViewHolder vh = (MyVH.LocationCardViewHolder) holder;
            vh.bind(mEntities.get(adpPos), null);
        } else if (viewType == VIEW_TYPE_TEACHER) {
            MyVH.TeacherCardViewHolder vh = (MyVH.TeacherCardViewHolder) holder;
            vh.bind(mEntities.get(adpPos), null);
        } else if(viewType == VIEW_TYPE_ASSIGNMENT) {
            MyVH.AssignmentCardViewHolder vh = (MyVH.AssignmentCardViewHolder) holder;
            vh.bind(mEntities.get(adpPos), null);
        }
    }
}
