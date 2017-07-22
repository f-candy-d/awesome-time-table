package com.d.candy.f.awesometimetable.structure;

import android.content.ContentValues;

import com.d.candy.f.awesometimetable.DBContract;

/**
 * Created by daichi on 7/21/17.
 */

public class Notification extends Entity {

    public enum Category {
        NULL(0),
        CANCELLATION(1),
        SUPPLEMENT(2),
        CHANGE(3),
        OTHER(4);

        private final int mID;

        Category(final int id) {
            mID = id;
        }

        public int toInt() {
            return mID;
        }
    }

    private String mTitle;
    private String mNote;
    private int mEnrollingInfoID;
    private Category mCategory;
    // Milliseconds
    private long mDateTimeStart;
    private long mDateTimeEnd;
    private boolean mIsDone;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public int getEnrollingInfoID() {
        return mEnrollingInfoID;
    }

    public void setEnrollingInfoID(int enrollingInfoID) {
        mEnrollingInfoID = enrollingInfoID;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public long getDateTimeStart() {
        return mDateTimeStart;
    }

    public void setDateTimeStart(long dateTimeStart) {
        mDateTimeStart = dateTimeStart;
    }

    public long getDateTimeEnd() {
        return mDateTimeEnd;
    }

    public void setDateTimeEnd(long dateTimeEnd) {
        mDateTimeEnd = dateTimeEnd;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean done) {
        mIsDone = done;
    }

    public Notification() {
        this(DBContract.NotificationEntity.NULL_ID,
                null, null,
                DBContract.SubjectEntity.NULL_ID,
                Category.NULL,
                0, 0, false);
    }

    public Notification(final int id, final String title, final String note,
                        final int enrollingInfoID, final Category category,
                        final long dateTimeStart, final long dateTimeEnd,
                        final boolean isDone) {
        super(id);
        mTitle = title;
        mNote = note;
        mEnrollingInfoID = enrollingInfoID;
        mCategory = category;
        mDateTimeStart = dateTimeStart;
        mDateTimeEnd = dateTimeEnd;
        mIsDone = isDone;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.NOTIFICATION;
    }

    @Override
    public String getAffiliation() {
        return DBContract.NotificationEntity.TABLE_NAME;
    }

    @Override
    public ContentValues toContentValues() {
        return null;
    }
}
