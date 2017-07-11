package com.d.candy.f.awesometimetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by daichi on 7/11/17.
 */

public class MiniSubjectCardAdapter extends RecyclerView.Adapter<MiniSubjectCardAdapter.ViewHolder> {

    /**
     * ViewHolder class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mName;
        private final TextView mLocation;

        public ViewHolder(View content_root) {
            super(content_root);
            mName = (TextView) content_root.findViewById(R.id.text_mini_subject_card_title);
            mLocation = (TextView) content_root.findViewById(R.id.text_mini_subject_card_location);
        }
    }

    /**
     * ViewTypes
     */
    private static final int VIEW_TYPE_SUBJECT = 0;
    private static final int VIEW_TYPE_HEADER = 1;

    private final TimeTable mTimeTable;

    /**
     *
     */
    private int mCurrentDayOfWeekPosition;

    public MiniSubjectCardAdapter(TimeTable timeTable) {
        mTimeTable = timeTable;
    }

    @Override
    public int getItemCount() {
        return (mTimeTable.getNumSubjectInOneDay()+1)*mTimeTable.getNumSubjectInOneDay();
    }

    @Override
    public int getItemViewType(int position) {
        return (mTimeTable.isPositionDayOfWeek(position))
                ? VIEW_TYPE_HEADER
                : VIEW_TYPE_SUBJECT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content_root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mini_subject_card, parent, false);
        return new ViewHolder(content_root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if(viewType == VIEW_TYPE_SUBJECT) {
            ArrayList<Subject> subjects;
            Subject subject;
            // the offset from a day of week position
            int offset;

            if(mCurrentDayOfWeekPosition < position) {
                subjects = mTimeTable.getSubjectsForDayOfWeekPosition(mCurrentDayOfWeekPosition);
                offset = position-mCurrentDayOfWeekPosition-1;

            } else {
                int prevDayOfWeekPos = mTimeTable.getPreviousDayOfWeekPosition(mCurrentDayOfWeekPosition);
                subjects = mTimeTable.getSubjectsForDayOfWeekPosition(prevDayOfWeekPos);
                offset = position-prevDayOfWeekPos-1;
            }

            if(offset < subjects.size()) {
                subject = subjects.get(offset);
            } else {
                subject = null;
            }

            // Set data to the item
            if(subject != null) {
                holder.mName.setText(subject.getName());
                holder.mLocation.setText(subject.getLocation());
            } else {
                // The item at 'position' is a blank item
                holder.mName.setText("SPACER");
                holder.mLocation.setText(String.valueOf(position));
            }

        } else if(viewType == VIEW_TYPE_HEADER) {
            mCurrentDayOfWeekPosition = position;
            holder.mName.setText("A Day Of Week");
            holder.mLocation.setText(String.valueOf(position));
        }
    }
}
