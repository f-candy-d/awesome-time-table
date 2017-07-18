package com.d.candy.f.awesometimetable.ui;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.MainActivity;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;

public class SubjectDetailsActivity extends AppCompatActivity
implements SubjectDetailsFragment.InteractionListener {

    private Subject mSubject = null;
    private Location mLocation = null;
    private Teacher mTeacher = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        Intent intent = getIntent();
        int id = intent.getIntExtra(
                MainActivity.EXTRA_SUBJECT_ID,
                DBContract.SubjectEntity.NULL_ID);

        collectInfo(id);
        displayInfo();
    }

    private void collectInfo(int id) {
        if(DBContract.SubjectEntity.MIN_USABLE_ID <= id) {
            mSubject = DataStructureFactory.makeSubject(id);
            mLocation = DataStructureFactory.makeLocation(mSubject.getLocationID());
            mTeacher = DataStructureFactory.makeTeacher(mSubject.getTeacherID());

        } else {
            // 'id' is null-id or blank-subject-id
        }
    }

    private void displayInfo() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SubjectDetailsFragment fragment = SubjectDetailsFragment
                .newInstance(mSubject, mLocation, mTeacher);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
