package com.d.candy.f.awesometimetable.useless;

import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;
import com.d.candy.f.awesometimetable.ui.CircularTimeLineMarker;
import com.d.candy.f.awesometimetable.utils.LogHelper;

/**
 * Created by daichi on 7/11/17.
 */

public class MiniSubjectCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * ViewHolder classes
     */
    public static class SubjectViewHolder extends RecyclerView.ViewHolder {

        private final View mContentRoot;
        private final LinearLayout mLayout;
        private final TextView mName;
        private final TextView mLocation;
        private final CircularTimeLineMarker mMarker;
        private int mSize = 1;

        public SubjectViewHolder(View content_root) {
            super(content_root);
            mContentRoot = content_root;
            mLayout = (LinearLayout) content_root.findViewById(R.id.linear_layout_mini_subject_card_container);
            mName = (TextView) content_root.findViewById(R.id.text_mini_subject_card_title);
            mLocation = (TextView) content_root.findViewById(R.id.text_mini_subject_card_location);
            mMarker = (CircularTimeLineMarker) content_root.findViewById(R.id.tl_marker_mini_subject_card_period_marker);
        }

        public void bind(final EnrollingInfo enrollingInfo, final OnItemClickListener onItemClickListener) {
            // Setup onItemClick listener
            mContentRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClicked(enrollingInfo);
                }
            });
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

        private int mSize = 1;
        private LinearLayout mLayout;
        private CircularTimeLineMarker mMarker;

        public SpacerViewHolder(View content_root) {
            super(content_root);
            mLayout = (LinearLayout) content_root.findViewById(R.id.linear_layout_spacer);
            mMarker = (CircularTimeLineMarker) content_root.findViewById(R.id.tl_marker_mini_spacer_period_marker);
        }
    }

    /**
     * OnItemClickListener Interface
     */
    public interface OnItemClickListener {
        void onItemClicked(EnrollingInfo enrollingInfo);
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
     * WeeklyTimeTable data
     */
    private final WeeklyTimeTable mWeeklyTimeTable;
    private SparseIntArray mHeaderPositions;
    private DayOfWeek[] mDayOfWeekOrder = null;

    private OnItemClickListener mOnItemClickListener = null;

    public MiniSubjectCardAdapter(
            WeeklyTimeTable weeklyTimeTable, DayOfWeek[] dayOfWeekOrder, OnItemClickListener itemClickListener) {

        mWeeklyTimeTable = weeklyTimeTable;
        mDayOfWeekOrder = dayOfWeekOrder;
        mOnItemClickListener = itemClickListener;

        if(dayOfWeekOrder == null) {
            throw new NullPointerException("'dayOfWeekOrder is null object");
        }

        // calculate header positions
        mHeaderPositions = new SparseIntArray(mDayOfWeekOrder.length);
        mHeaderPositions.put(mDayOfWeekOrder[0].toInt(), 0);
        for (int i = 1; i < mDayOfWeekOrder.length; ++i) {
            mHeaderPositions.put(
                    mDayOfWeekOrder[i].toInt(),
                    mWeeklyTimeTable.countSubjectOn(mDayOfWeekOrder[i-1])
                            + mHeaderPositions.get(mDayOfWeekOrder[i-1].toInt())
                            + 1);
        }
    }

    @Override
    public int getItemCount() {
        return mWeeklyTimeTable.countSubject() + mDayOfWeekOrder.length;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)) {
            return VIEW_TYPE_HEADER;
        } else {
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(position);
            int offset = position - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            int id = mWeeklyTimeTable.getSubjectAtOrderOn(dayOfWeek, offset).getID();
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
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(adpPos);
            int offset = adpPos - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            Subject subject = mWeeklyTimeTable.getSubjectAtOrderOn(dayOfWeek, offset);

            // change the height of an item
            SubjectViewHolder sbjHolder = (SubjectViewHolder) holder;
            ViewGroup.LayoutParams layoutParams = sbjHolder.mLayout.getLayoutParams();
            layoutParams.height = layoutParams.height/sbjHolder.mSize*subject.getLength();
            sbjHolder.mLayout.setLayoutParams(layoutParams);
            sbjHolder.mSize = subject.getLength();

            sbjHolder.mName.setText(subject.getName());
            sbjHolder.mLocation
                    .setText(mWeeklyTimeTable.getLocation(subject.getLocationID()).getName());

            // Set up the marker
            sbjHolder.mMarker.setText(String.valueOf(
                    mWeeklyTimeTable.getBeginPeriodAtOrderOn(dayOfWeek, offset)));
            sbjHolder.mMarker.setNumSubMarker(subject.getLength() - 1);
            if(offset == 0) {
                sbjHolder.mMarker.enableDrawRunningOverLineStart(false);
            } else {
                sbjHolder.mMarker.enableDrawRunningOverLineStart(true);
            }
            if(offset == mWeeklyTimeTable.countSubjectOn(dayOfWeek) - 1) {
                sbjHolder.mMarker.enableDrawRunningOverLineEnd(false);
            } else {
                sbjHolder.mMarker.enableDrawRunningOverLineEnd(true);
            }

            sbjHolder.bind(
                    mWeeklyTimeTable.getEnrollingInfoAtOrderOn(dayOfWeek, offset),
                    mOnItemClickListener);

            // Make a Spacer view
        } else if(viewType == VIEW_TYPE_SPACER) {
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(adpPos);
            int offset = adpPos - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            Subject subject = mWeeklyTimeTable.getSubjectAtOrderOn(dayOfWeek, offset);
            SpacerViewHolder spcHolder = (SpacerViewHolder) holder;
            int size = subject.getLength();

            // change the height of a spacer
            ViewGroup.LayoutParams layoutParams = spcHolder.mLayout.getLayoutParams();
            layoutParams.height = layoutParams.height/spcHolder.mSize*size;
            spcHolder.mLayout.setLayoutParams(layoutParams);
            spcHolder.mSize = size;

            // Setup the marker
            spcHolder.mMarker.setText(String.valueOf(
                    mWeeklyTimeTable.getBeginPeriodAtOrderOn(dayOfWeek, offset)));
            if(offset == mWeeklyTimeTable.countSubjectOn(dayOfWeek) - 1) {
                spcHolder.mMarker.enableDrawRunningOverLineEnd(false);
            } else {
                spcHolder.mMarker.enableDrawRunningOverLineEnd(true);
            }

            // Make a Header view
        } else if(viewType == VIEW_TYPE_HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
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

    /**
     * [headerPositionOf'dayOfWeek'] <= 'position' &&
     * 'position' <= [headerPositionOf'dayOfWeek'] + [NumOfSubjectsOn'dayOfWeek']
     *
     * Return a 'dayOfWeek' which meets the requirement above.
     *
     * @param position a certain position in RecyclerView
     * @return A DayOfWeek value
     */
    private DayOfWeek getDayOfWeekContainsPosition(int position) {
        for (DayOfWeek dayOfWeek : mDayOfWeekOrder) {
            int threshold = mHeaderPositions.get(dayOfWeek.toInt())
                    + mWeeklyTimeTable.countSubjectOn(dayOfWeek);

            if(position <= threshold) return dayOfWeek;
        }

        throw new IllegalArgumentException(
                "'position' is an illegal value");
    }
}
