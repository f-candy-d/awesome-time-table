package com.d.candy.f.awesometimetable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;
import com.d.candy.f.awesometimetable.utils.LogHelper;

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

    /**
     * Class tag for log
     */
    private static final String TAG = LogHelper.makeLogTag(MiniSubjectCardAdapter.class);

    /**
     * The position of the position of a current day-of-week cell
     */
    private int mCurrentDayOfWeekPosition;

    /**
     * WeeklyTimeTable data
     */
    private final WeeklyTimeTable mWeeklyTimeTable;

    private SparseIntArray mHeaderPositions;
    private DayOfWeek[] mDayOfWeekOrder = null;

    public MiniSubjectCardAdapter(WeeklyTimeTable weeklyTimeTable, DayOfWeek[] dayOfWeekOrder) {
        mWeeklyTimeTable = weeklyTimeTable;
        mDayOfWeekOrder = dayOfWeekOrder;

        if(dayOfWeekOrder == null) {
            throw new NullPointerException("'dayOfWeekOrder is null object");
        }

        // calculate header positions
        mHeaderPositions = new SparseIntArray(mDayOfWeekOrder.length);
        mHeaderPositions.put(mDayOfWeekOrder[0].toInt(), 0);
        for (int i = 1; i < mDayOfWeekOrder.length; ++i) {
            mHeaderPositions.put(
                    mDayOfWeekOrder[i].toInt(),
                    mWeeklyTimeTable.countSubjectOn(
                            mDayOfWeekOrder[i-1]) + mHeaderPositions.get(mDayOfWeekOrder[i-1].toInt()) + 1);
        }

        for (DayOfWeek dayOfWeek : mDayOfWeekOrder) {
            Log.d(TAG, dayOfWeek.toString() + " header position -> " + mHeaderPositions.get(dayOfWeek.toInt()));
        }
    }

    @Override
    public int getItemCount() {
//        return (mWeeklyTimeTable.getNumSubjectInOneDay()+1)* mWeeklyTimeTable.getNumSubjectInOneDay();
        return mWeeklyTimeTable.countSubject() + mDayOfWeekOrder.length;
    }

    @Override
    public int getItemViewType(int position) {
//        return (mWeeklyTimeTable.isPositionDayOfWeek(position))
//                ? VIEW_TYPE_HEADER
//                : (mWeeklyTimeTable.isPositionOccupiedByAnySubject(position))
//                   ? VIEW_TYPE_SUBJECT
//                   : VIEW_TYPE_SPACER;

        if(isPositionHeader(position)) {
            return VIEW_TYPE_HEADER;
        } else {
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(position);
            int offset = position - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            int id = mWeeklyTimeTable.getSubjectAtPositionOn(dayOfWeek, offset).getID();
            if(DBContract.SubjectEntity.MIN_USABLE_ID <= id) {
                return VIEW_TYPE_SUBJECT;
            } else {
                return VIEW_TYPE_SPACER;
            }
        }
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
//            ArrayList<Subject> subjects;
//            Subject subject;
//            // The offset from a day of week adpPos
//            int offset;
//
//            // On scroll to the top of the screen from the bottom of that
//            if (mCurrentDayOfWeekPosition < adpPos) {
//                subjects = mWeeklyTimeTable.getSubjectsForDayOfWeekPosition(mCurrentDayOfWeekPosition);
//                offset = adpPos - mCurrentDayOfWeekPosition - 1;
//
//                // On scroll to the top of the screen from the bottom of that
//            } else {
//                int prevDayOfWeekPos = mWeeklyTimeTable.getPreviousDayOfWeekPosition(mCurrentDayOfWeekPosition);
//                subjects = mWeeklyTimeTable.getSubjectsForDayOfWeekPosition(prevDayOfWeekPos);
//                offset = adpPos - prevDayOfWeekPos - 1;
//            }
//
//            if (0 <= offset && offset < subjects.size()) {
//                // Set data to the item
//                SubjectViewHolder sbjHolder = (SubjectViewHolder) holder;
//                subject = subjects.get(offset);
//                sbjHolder.mName.setText(subject.getName());
//                sbjHolder.mLocation.setText(subject.getLocation());
//            }
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(adpPos);
            int offset = adpPos - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            Subject subject = mWeeklyTimeTable.getSubjectAtPositionOn(dayOfWeek, offset);

            SubjectViewHolder sbjHolder = (SubjectViewHolder) holder;
            sbjHolder.mName.setText(subject.getName());
            sbjHolder.mLocation.setText(subject.getLocation());

            // Make a Spacer view
        } else if(viewType == VIEW_TYPE_SPACER) {
//            SpacerViewHolder spcHolder = (SpacerViewHolder) holder;

            // Make a Header view
        } else if(viewType == VIEW_TYPE_HEADER) {
//            mCurrentDayOfWeekPosition = adpPos;
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
//            headerHolder.mText.setText(mWeeklyTimeTable.getDayOfWeekContainsPosition(adpPos).toString());
            headerHolder.mText.setText(getDayOfWeekContainsPosition(adpPos).toString());
        }
    }

    private boolean isPositionHeader(int position) {
        for(int i = 0; i < mDayOfWeekOrder.length; ++i) {
            int headerPos = mHeaderPositions.get(mDayOfWeekOrder[i].toInt());
            if(position == headerPos) return true;
            else if (position < headerPos) break;
        }

        return false;
    }

    private DayOfWeek getDayOfWeekContainsPosition(int position) {
        for (DayOfWeek dayOfWeek : mDayOfWeekOrder) {
            int threshold = mHeaderPositions.get(dayOfWeek.toInt())
                    + mWeeklyTimeTable.countSubjectOn(dayOfWeek);

            Log.d(TAG, dayOfWeek.toString() + " : position= " + String.valueOf(position) + " <= threshold=" + String.valueOf(threshold));
            if(position <= threshold) return dayOfWeek;
        }

        throw new IllegalArgumentException(
                "'position' is an illegal value");
    }
}
