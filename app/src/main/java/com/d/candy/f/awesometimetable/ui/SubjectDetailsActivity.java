package com.d.candy.f.awesometimetable.ui;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.MainActivity;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;

public class SubjectDetailsActivity extends AppCompatActivity
implements SubjectDetailsFragment.InteractionListener {

    private EnrollingInfo mEnrollingInfo = null;
    private Subject mSubject = null;
    private Location mLocation = null;
    private Teacher mTeacher = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        Intent intent = getIntent();
        int id = intent.getIntExtra(
                MainActivity.EXTRA_ENROLLING_INFO_ID,
                DBContract.SubjectEntity.NULL_ID);

        collectInfo(id);
        displayInfo();
    }

    private void collectInfo(int enrollingInfoID) {
        if(DBContract.EnrollingInfoEntity.NULL_ID != enrollingInfoID) {
            mEnrollingInfo = DataStructureFactory.makeEnrollingInfo(enrollingInfoID);
            mSubject = DataStructureFactory.makeSubject(mEnrollingInfo.getSubjectID());
            mLocation = DataStructureFactory.makeLocation(mSubject.getLocationID());
            mTeacher = DataStructureFactory.makeTeacher(mSubject.getTeacherID());

            Log.d("QQQ", mSubject.getName());
            Log.d("QQQ", mLocation.getName());
            Log.d("QQQ", mTeacher.getName());

        } else {
            // 'id' is null-id
            Log.d("QQQ", "if-null-id block");
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
