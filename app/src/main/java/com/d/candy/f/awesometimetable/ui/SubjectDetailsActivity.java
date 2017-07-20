package com.d.candy.f.awesometimetable.ui;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.MainActivity;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;
import com.d.candy.f.awesometimetable.utils.BundleBuilder;
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
        if (!intent.hasExtra(MainActivity.EXTRA_ENROLLING_INFO_ID)) {
            throw new IllegalArgumentException(
                    "'intent' has no value for 'EXTRA_ENROLLING_INFO_ID");
        }

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

        } else {
            // 'id' is null-id
            throw new IllegalArgumentException(
                    "enrolling-info-ID = " + String.valueOf(enrollingInfoID) + " is not available");
        }
    }

    private void displayInfo() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new BundleBuilder()
                .put(EntityType.SUBJECT.toString(), mSubject)
                .put(EntityType.LOCATION.toString(), mLocation)
                .put(EntityType.TEACHER.toString(), mTeacher)
                .put(EntityType.ENROLLING_INFO.toString(), mEnrollingInfo)
                .put(EntityType.ASSIGNMENT.toString(), DataStructureFactory.makeAssignmentListForEnrollingInfo(mEnrollingInfo))
                .build();

        SubjectDetailsFragment fragment = SubjectDetailsFragment
                .newInstance(bundle);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    /**
     * The implementation of SubjectDetailsFragment.InteractionListener
     */
}
