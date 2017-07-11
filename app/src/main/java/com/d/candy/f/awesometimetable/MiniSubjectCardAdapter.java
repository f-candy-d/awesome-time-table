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

public class MiniSubjectCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * ViewHolder classes
     */
    public static class SubjectViewHolder extends RecyclerView.ViewHolder {

        private final TextView mName;
        private final TextView mLocation;

        public SubjectViewHolder(View content_root) {
            super(content_root);
            mName = (TextView) content_root.findViewById(R.id.text_mini_subject_card_title);
            mLocation = (TextView) content_root.findViewById(R.id.text_mini_subject_card_location);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView mText;

        public HeaderViewHolder(View content_root) {
            super(content_root);
            mText = (TextView) content_root.findViewById(R.id.text_recyc_mini_header_text);
        }
    }

    public static class SpacerViewHolder extends RecyclerView.ViewHolder {

        public SpacerViewHolder(View content_root) {
            super(content_root);
        }
    }

    /**
     * ViewTypes
     */
    private static final int VIEW_TYPE_SUBJECT = 0;
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_SPACER = 2;

    private final TimeTable mTimeTable;

    /**
     * The position of the position of a current day-of-week cell
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
                : (mTimeTable.isPositionOccupiedByAnySubject(position))
                   ? VIEW_TYPE_SUBJECT
                   : VIEW_TYPE_SPACER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SUBJECT: {
                View content_root = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mini_subject_card, parent, false);
                return new SubjectViewHolder(content_root);
            }

            case VIEW_TYPE_HEADER: {
                View content_root = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyc_mini_header, parent, false);
                return new HeaderViewHolder(content_root);
            }

            case VIEW_TYPE_SPACER: {
                View content_root = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyc_mini_spacer, parent, false);
                return new SpacerViewHolder(content_root);
            }

            default:
                throw new IllegalArgumentException("onCreateViewHolder(): unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int adpPos = holder.getAdapterPosition();
        final int viewType = getItemViewType(adpPos);

        // Make a mini-subject-card or a spacer-card
        if(viewType == VIEW_TYPE_SUBJECT) {
            ArrayList<Subject> subjects;
            Subject subject;
            // The offset from a day of week adpPos
            int offset;

            // On scroll to the top of the screen from the bottom of that
            if (mCurrentDayOfWeekPosition < adpPos) {
                subjects = mTimeTable.getSubjectsForDayOfWeekPosition(mCurrentDayOfWeekPosition);
                offset = adpPos - mCurrentDayOfWeekPosition - 1;

                // On scroll to the top of the screen from the bottom of that
            } else {
                int prevDayOfWeekPos = mTimeTable.getPreviousDayOfWeekPosition(mCurrentDayOfWeekPosition);
                subjects = mTimeTable.getSubjectsForDayOfWeekPosition(prevDayOfWeekPos);
                offset = adpPos - prevDayOfWeekPos - 1;
            }

            if (0 <= offset && offset < subjects.size()) {
                // Set data to the item
                SubjectViewHolder sbjHolder = (SubjectViewHolder) holder;
                subject = subjects.get(offset);
                sbjHolder.mName.setText(subject.getName());
                sbjHolder.mLocation.setText(subject.getLocation());
            }

            // Make a Spacer view
        } else if(viewType == VIEW_TYPE_SPACER) {
//            SpacerViewHolder spcHolder = (SpacerViewHolder) holder;

            // Make a Header view
        } else if(viewType == VIEW_TYPE_HEADER) {
            mCurrentDayOfWeekPosition = adpPos;
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.mText.setText("A Day Of Week");
        }
    }
}
