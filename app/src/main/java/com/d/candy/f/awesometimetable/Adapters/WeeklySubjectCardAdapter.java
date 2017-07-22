package com.d.candy.f.awesometimetable.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.MyVH;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;
import com.d.candy.f.awesometimetable.ui.CircularTimeLineMarker;

/**
 * Created by daichi on 7/21/17.
 */

public class WeeklySubjectCardAdapter extends EntityCardAdapter {

    /**
     * View types
     */
    private static final int VIEW_TYPE_SUBJECT = 0;
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_SPACER = 2;

    @NonNull private final WeeklyTimeTable mTimeTable;
    @NonNull private final DayOfWeek[] mDayOfWeeksOrder;
    private SparseIntArray mHeaderPositions;

    public WeeklySubjectCardAdapter(@NonNull final WeeklyTimeTable timeTable,
                                    @NonNull DayOfWeek[] dayOfWeeksOrder) {
        this(timeTable, dayOfWeeksOrder, null);
    }

    public WeeklySubjectCardAdapter(@NonNull final WeeklyTimeTable timeTable,
                                    @NonNull DayOfWeek[] dayOfWeeksOrder,
                                    @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {
//        super(timeTable, onItemClickListener);
        super(onItemClickListener);

        // noinspection ConstantConditions
        if (dayOfWeeksOrder == null) {
            throw new NullPointerException();
        }

        // Initialization
        mTimeTable = timeTable;
        mDayOfWeeksOrder = dayOfWeeksOrder;
        init();
    }

    @Override
    public int getItemCount() {
        return mTimeTable.countSubjectOn(mDayOfWeeksOrder) + mDayOfWeeksOrder.length;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)) {
            return VIEW_TYPE_HEADER;
        } else {
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(position);
            int offset = position - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            int id = mTimeTable.getSubjectAtOrderOn(dayOfWeek, offset).getID();
            if(DBContract.SubjectEntity.MIN_USABLE_ID <= id) {
                return VIEW_TYPE_SUBJECT;
            } else {
                return VIEW_TYPE_SPACER;
            }
        }
    }

    @Override
    public MyVH.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_SUBJECT: {
                View content_root = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mini_subject_card, parent, false);
                return new MyVH.MiniSubjectCardHolder(content_root);
            }

            case VIEW_TYPE_HEADER: {
                View content_root = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyc_mini_header, parent, false);
                return new MyVH.HeaderViewHolder(content_root);
            }

            case VIEW_TYPE_SPACER: {
                View content_root = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyc_mini_spacer, parent, false);
                return new MyVH.SpacerViewHolder(content_root);
            }

            default:
                throw new IllegalArgumentException("onCreateViewHolder(): unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(MyVH.BaseViewHolder holder, int position) {

        int adpPos = holder.getAdapterPosition();
        int viewType = getItemViewType(adpPos);

        if (viewType == VIEW_TYPE_SUBJECT) {
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(adpPos);
            int offset = adpPos - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            Subject subject = mTimeTable.getSubjectAtOrderOn(dayOfWeek, offset);
            MyVH.MiniSubjectCardHolder vh = (MyVH.MiniSubjectCardHolder) holder;
            Location location = mTimeTable.getLocation(subject.getLocationID());
            vh.bind(adpPos, subject, location, getOnItemClickListener());

            // Set up the marker
            CircularTimeLineMarker marker = vh.getTimeLineMarker();
            marker.setText(String.valueOf(
                    mTimeTable.getBeginPeriodAtOrderOn(dayOfWeek, offset)));
            marker.setNumSubMarker(subject.getLength() - 1);
            if(offset == 0) {
                marker.enableDrawRunningOverLineStart(false);
            } else {
                marker.enableDrawRunningOverLineStart(true);
            }
            if(offset == mTimeTable.countSubjectOn(dayOfWeek) - 1) {
                marker.enableDrawRunningOverLineEnd(false);
            } else {
                marker.enableDrawRunningOverLineEnd(true);
            }


        } else if (viewType == VIEW_TYPE_SPACER) {
            DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(adpPos);
            int offset = adpPos - mHeaderPositions.get(dayOfWeek.toInt()) - 1;
            Subject subject = mTimeTable.getSubjectAtOrderOn(dayOfWeek, offset);
            MyVH.SpacerViewHolder vh = (MyVH.SpacerViewHolder) holder;
            vh.bind(adpPos, subject, getOnItemClickListener());

            // Setup the marker
            CircularTimeLineMarker marker = vh.getTimeLineMarker();
            marker.setText(String.valueOf(
                    mTimeTable.getBeginPeriodAtOrderOn(dayOfWeek, offset)));
            if(offset == mTimeTable.countSubjectOn(dayOfWeek) - 1) {
                marker.enableDrawRunningOverLineEnd(false);
            } else {
                marker.enableDrawRunningOverLineEnd(true);
            }


        } else if (viewType == VIEW_TYPE_HEADER) {
            MyVH.HeaderViewHolder vh = (MyVH.HeaderViewHolder) holder;
            vh.bind(position, getDayOfWeekContainsPosition(adpPos).toString(), null);
        }
    }

    public EnrollingInfo getEnrollingInfoAtPosition(final int position) {
        DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(position);
        int offset = position - mHeaderPositions.get(dayOfWeek.toInt()) - 1;

        return mTimeTable.getEnrollingInfoAtOrderOn(dayOfWeek, offset);
    }

    private void init() {
        // calculate header positions
        mHeaderPositions = new SparseIntArray(mDayOfWeeksOrder.length);
        mHeaderPositions.put(mDayOfWeeksOrder[0].toInt(), 0);
        for (int i = 1; i < mDayOfWeeksOrder.length; ++i) {
            mHeaderPositions.put(
                    mDayOfWeeksOrder[i].toInt(),
                    mTimeTable.countSubjectOn(mDayOfWeeksOrder[i - 1])
                            + mHeaderPositions.get(mDayOfWeeksOrder[i - 1].toInt())
                            + 1);
        }
    }

    private boolean isPositionHeader(int position) {
        for(int i = 0; i < mDayOfWeeksOrder.length; ++i) {
            int headerPos = mHeaderPositions.get(mDayOfWeeksOrder[i].toInt());
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
        for (DayOfWeek dayOfWeek : mDayOfWeeksOrder) {
            int threshold = mHeaderPositions.get(dayOfWeek.toInt())
                    + mTimeTable.countSubjectOn(dayOfWeek);

            if(position <= threshold) return dayOfWeek;
        }

        throw new IllegalArgumentException(
                "'position' is an illegal value");
    }
}
