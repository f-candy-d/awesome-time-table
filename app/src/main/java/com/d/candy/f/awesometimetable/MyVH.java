package com.d.candy.f.awesometimetable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;

/**
 * Created by daichi on 7/19/17.
 */

public class MyVH {

    public static class BaseViewHolder
            extends RecyclerView.ViewHolder {

        private View mRoot = null;

        public interface OnItemClickListener {
            void onItemClick();
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
        protected void bind(Entity entity, final OnItemClickListener itemClickListener) {
            // Set OnClickListener to an item view
            if (itemClickListener != null) {
                mRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick();
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
        protected void bind(Entity entity, OnItemClickListener itemClickListener) {
            super.bind(entity, itemClickListener);
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
        protected void bind(Entity entity, OnItemClickListener itemClickListener) {
            super.bind(entity, itemClickListener);
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
        protected void bind(Entity entity, OnItemClickListener itemClickListener) {
            super.bind(entity, itemClickListener);
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
        protected void bind(Entity entity, OnItemClickListener itemClickListener) {
            super.bind(entity, itemClickListener);
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
}
