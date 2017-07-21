package com.d.candy.f.awesometimetable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;
import com.d.candy.f.awesometimetable.ui.CircularTimeLineMarker;

/**
 * Created by daichi on 7/19/17.
 */

public class MyVH {

    public static class BaseViewHolder
            extends RecyclerView.ViewHolder {

        private View mRoot = null;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public BaseViewHolder(View view) {
            super(view);
            mRoot = view;
        }

        /**
         * Call super.bind() in bind() of a child class if you want to set OnItemClickListener
         * to an item view.
         * @param entity
         * @param itemClickListener
         */
        protected void bind(final int position, Entity entity,
                            final OnItemClickListener itemClickListener) {
            // Set OnClickListener to an item view
            if (itemClickListener != null) {
                mRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(position);
                    }
                });
            }
        }

        protected View getContentRoot() {
            return mRoot;
        }

        protected void isEntityTypeValidAndThrowExcepIfNot(Entity entity, EntityType requiredType) {

            if(entity.getEntityType() != requiredType) {
                throw new IllegalArgumentException(
                        "'entity' is not a " + requiredType.toString() + " object");
            }

        }
    }

    public static class SubjectCardViewHolder
            extends BaseViewHolder {

        private TextView mSubjectName;
        private TextView mLocationName;
        private TextView mTeacherName;

        public SubjectCardViewHolder(View view) {
            super(view);

            mSubjectName = (TextView) view.findViewById(R.id.text_subject_card_name);
            mLocationName = (TextView) view.findViewById(R.id.text_subject_card_location);
            mTeacherName = (TextView) view.findViewById(R.id.text_subject_card_teacher);
        }

        @Override
        protected void bind(final int position, Entity entity, OnItemClickListener itemClickListener) {
            super.bind(position, entity, itemClickListener);
            isEntityTypeValidAndThrowExcepIfNot(entity, EntityType.SUBJECT);

            Subject subject = (Subject) entity;
            mSubjectName.setText(subject.getName());
            mLocationName.setText("location ID = " + String.valueOf(subject.getLocationID()));
            mTeacherName.setText("teacher ID = " + String.valueOf(subject.getTeacherID()));
        }
    }

    public static class LocationCardViewHolder
            extends BaseViewHolder {

        private TextView mLocationName;

        public LocationCardViewHolder(View view) {
            super(view);

            mLocationName = (TextView) view.findViewById(R.id.text_location_card_name);
        }

        @Override
        protected void bind(final int position, Entity entity, OnItemClickListener itemClickListener) {
            super.bind(position, entity, itemClickListener);
            isEntityTypeValidAndThrowExcepIfNot(entity, EntityType.LOCATION);

            Location location = (Location) entity;
            mLocationName.setText(location.getName() + " #ID = " + String.valueOf(location.getID()));
        }
    }

    public static class TeacherCardViewHolder
            extends BaseViewHolder {

        private TextView mTeacherName;
        private TextView mLabName;
        private TextView mMail;
        private TextView mPhoneNumber;

        public TeacherCardViewHolder(View view) {
            super(view);

            mTeacherName = (TextView) view.findViewById(R.id.text_teacher_card_name);
            mLabName = (TextView) view.findViewById(R.id.text_teacher_card_lab);
            mMail = (TextView) view.findViewById(R.id.text_teacher_card_mail);
            mPhoneNumber = (TextView) view.findViewById(R.id.text_teacher_card_phone);
        }

        @Override
        protected void bind(final int position, Entity entity, OnItemClickListener itemClickListener) {
            super.bind(position, entity, itemClickListener);
            isEntityTypeValidAndThrowExcepIfNot(entity, EntityType.TEACHER);

            Teacher teacher = (Teacher) entity;
            mTeacherName.setText(teacher.getName() + " #ID = " + String.valueOf(teacher.getID()));
            mLabName.setText("Lab : " + teacher.getLab());
            mMail.setText("Mail : " + teacher.getMail());
            mPhoneNumber.setText("Phone : " + teacher.getPhone());
        }
    }

    public static class AssignmentCardViewHolder
            extends BaseViewHolder {

        private TextView mTitle;
        private TextView mNote;
        private TextView mIsDone;
        private TextView mDeadline;
        private TextView mDeadLineDayOfWeek;

        public AssignmentCardViewHolder(View view) {
            super(view);

            mTitle = (TextView) view.findViewById(R.id.text_assignment_card_title);
            mNote = (TextView) view.findViewById(R.id.text_assignment_card_note);
            mIsDone = (TextView) view.findViewById(R.id.text_assignment_card_is_done);
            mDeadline = (TextView) view.findViewById(R.id.text_assignment_card_deadline_m_d_y);
            mDeadLineDayOfWeek = (TextView) view.findViewById(R.id.text_assignment_card_deadline_d_of_w);
        }

        @Override
        protected void bind(final int position, Entity entity, OnItemClickListener itemClickListener) {
            super.bind(position, entity, itemClickListener);
            isEntityTypeValidAndThrowExcepIfNot(entity, EntityType.ASSIGNMENT);

            Assignment assignment = (Assignment) entity;
            mTitle.setText(assignment.getTitle());
            mNote.setText(assignment.getNote());
            mIsDone.setText("IS DONE => " + String.valueOf(assignment.isDone()));
            mDeadline.setText(
                    String.valueOf(assignment.getDeadlineMonth()) + " / "
                            + String.valueOf(assignment.getDeadlineDay()) + " / "
                            + String.valueOf(assignment.getDeadlineYear()));
            mDeadLineDayOfWeek.setText(assignment.getDeadlineDayOfWeek().toString());
        }
    }

    public static class MiniSubjectCardHolder extends BaseViewHolder {

        private final View mContentRoot;
        private final LinearLayout mLayout;
        private final TextView mName;
        private final TextView mLocation;
        private final CircularTimeLineMarker mMarker;
        private int mSize = 1;

        public MiniSubjectCardHolder(View view) {
            super(view);

            mContentRoot = view;
            mLayout = (LinearLayout) view.findViewById(R.id.linear_layout_mini_subject_card_container);
            mName = (TextView) view.findViewById(R.id.text_mini_subject_card_title);
            mLocation = (TextView) view.findViewById(R.id.text_mini_subject_card_location);
            mMarker = (CircularTimeLineMarker) view.findViewById(R.id.tl_marker_mini_subject_card_period_marker);
        }

        @Override
        protected void bind(int position, Entity entity, OnItemClickListener itemClickListener) {
            super.bind(position, entity, itemClickListener);

            if (entity.getEntityType() != EntityType.SUBJECT) {
                throw new IllegalArgumentException();
            }

            // change the height of an item
            Subject subject = (Subject) entity;
            ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
            layoutParams.height = layoutParams.height/mSize*subject.getLength();
            mLayout.setLayoutParams(layoutParams);
            mSize = subject.getLength();

            // Set data
            mName.setText(subject.getName());
        }

        public void setLocationName(String locationName) {
            mLocation.setText(locationName);
        }

        public final CircularTimeLineMarker getTimeLineMarker() {
            return mMarker;
        }
    }

    public static class HeaderViewHolder extends BaseViewHolder {

        private final TextView mText;

        public HeaderViewHolder(View view) {
            super(view);

            mText = (TextView) view.findViewById(R.id.text_recyc_mini_header_text);
        }

        @Override
        protected void bind(int position, Entity entity, OnItemClickListener itemClickListener) {
            super.bind(position, entity, itemClickListener);
        }

        public void setTitle(String title) {
            mText.setText(title);
        }
    }

    public static class SpacerViewHolder extends BaseViewHolder {

        private int mSize = 1;
        private LinearLayout mLayout;
        private CircularTimeLineMarker mMarker;

        public SpacerViewHolder(View view) {
            super(view);

            mLayout = (LinearLayout) view.findViewById(R.id.linear_layout_spacer);
            mMarker = (CircularTimeLineMarker) view.findViewById(R.id.tl_marker_mini_spacer_period_marker);
        }

        @Override
        protected void bind(int position, Entity entity, OnItemClickListener itemClickListener) {
            super.bind(position, entity, itemClickListener);


            if (entity.getEntityType() != EntityType.SUBJECT) {
                throw new IllegalArgumentException();
            }
            // change the height of a spacer
            Subject subject = (Subject) entity;
            ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
            layoutParams.height = layoutParams.height/mSize*subject.getLength();
            mLayout.setLayoutParams(layoutParams);
            mSize = subject.getLength();
        }

        public final CircularTimeLineMarker getTimeLineMarker() {
            return mMarker;
        }
    }

}
