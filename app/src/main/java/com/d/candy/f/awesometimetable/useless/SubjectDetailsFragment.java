package com.d.candy.f.awesometimetable.useless;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.SubjectDetailsCardAdapter;
import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectDetailsFragment extends Fragment {

    // A container activity of fragment must implements this interface!
    public interface InteractionListener {
    }

    private Subject mSubject = null;
    private Location mLocation = null;
    private Teacher mTeacher = null;
    private EnrollingInfo mEnrollingInfo = null;
    private ArrayList<Assignment> mAssignments = null;

    private InteractionListener mInteractionListener = null;

    public SubjectDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment SubjectDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectDetailsFragment newInstance(Bundle args) {
        SubjectDetailsFragment fragment = new SubjectDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && isArgumentsValid(getArguments())) {
            mSubject = (Subject) getArguments().getSerializable(EntityType.SUBJECT.toString());
            mLocation = (Location) getArguments().getSerializable(EntityType.LOCATION.toString());
            mTeacher = (Teacher) getArguments().getSerializable(EntityType.TEACHER.toString());
            mEnrollingInfo = (EnrollingInfo) getArguments().getSerializable(EntityType.ENROLLING_INFO.toString());
            mAssignments = new ArrayList<>(
                    Arrays.asList((Assignment[]) getArguments().getSerializable(EntityType.ASSIGNMENT.toString())));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_subject_details, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyc_subject_details_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SubjectDetailsCardAdapter adapter = getTestAdapter();
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // The container Activity of this fragment must implements InteractionListener interface
        mInteractionListener = (InteractionListener) activity;
    }

    // TODO: this is the test code
    private SubjectDetailsCardAdapter getTestAdapter() {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(mSubject);
        entities.add(mLocation);
        entities.add(mTeacher);

        for (Assignment assignment : mAssignments)
        {
            entities.add(assignment);
        }

        return new SubjectDetailsCardAdapter(entities);
    }

    private boolean isArgumentsValid(Bundle args) {
        return ((args.containsKey(EntityType.SUBJECT.toString())
                && args.containsKey(EntityType.LOCATION.toString())
                && args.containsKey(EntityType.TEACHER.toString())
                && args.containsKey(EntityType.ENROLLING_INFO.toString()))
                // The following arguments are optional
                || args.containsKey(EntityType.ASSIGNMENT.toString()));
    }

}
