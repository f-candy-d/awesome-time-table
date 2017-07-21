package com.d.candy.f.awesometimetable;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.MainActivity;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.RecyclerViewScrollObserver;
import com.d.candy.f.awesometimetable.EntityCardAdapter;
import com.d.candy.f.awesometimetable.SubjectAndRelationsCardAdapter;
import com.d.candy.f.awesometimetable.SubjectDetailsCardAdapter;
import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;
import com.d.candy.f.awesometimetable.ui.EntityCardListViewerFragment;
import com.d.candy.f.awesometimetable.utils.BundleBuilder;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class SubjectDetailsActivity extends AppCompatActivity
implements EntityCardListViewerFragment.OnInteractionListener {

    private EnrollingInfo mEnrollingInfo = null;
    private Subject mSubject = null;
    private Location mLocation = null;
    private Teacher mTeacher = null;
    private ArrayList<Assignment> mAssignments = null;

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
            mAssignments = new ArrayList<>(
                    Arrays.asList(DataStructureFactory.makeAssignmentListForEnrollingInfo(mEnrollingInfo)));
        } else {
            // 'id' is null-id
            throw new IllegalArgumentException(
                    "enrolling-info-ID = " + String.valueOf(enrollingInfoID) + " is not available");
        }
    }

    private void displayInfo() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EntityCardListViewerFragment fragment = EntityCardListViewerFragment.newInstance();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    /**
     * The implementation of EntityCardListViewerFragment.OnInteractionListener
     */
    @Override
    public void onListItemClicked(int position) {

    }

    @Override
    public void onListScrolled(RecyclerViewScrollObserver.ScrollDirection direction) {

    }

    @Override
    public EntityCardAdapter getListAdapter() {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(mSubject);
        entities.add(mLocation);
        entities.add(mTeacher);

        for (Assignment assignment : mAssignments)
        {
            entities.add(assignment);
        }

        return new SubjectAndRelationsCardAdapter(entities);
    }
}
