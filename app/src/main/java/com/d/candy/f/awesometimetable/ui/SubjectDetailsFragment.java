package com.d.candy.f.awesometimetable.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.SubjectDetailsCardAdapter;
import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectDetailsFragment extends Fragment {

    // A container activity of fragment must implements this interface!
    public interface InteractionListener {

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUBJECT = "subject";
    private static final String ARG_LOCATION = "location";
    private static final String ARG_TEACHER = "teacher";

    private Subject mSubject = null;
    private Location mLocation = null;
    private Teacher mTeacher = null;

    public SubjectDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subject Subject object
     * @return A new instance of fragment SubjectDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectDetailsFragment newInstance(
            Subject subject, Location location, Teacher teacher) {
        SubjectDetailsFragment fragment = new SubjectDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SUBJECT, subject);
        args.putSerializable(ARG_LOCATION, location);
        args.putSerializable(ARG_TEACHER, teacher);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSubject = (Subject) getArguments().getSerializable(ARG_SUBJECT);
            mLocation = (Location) getArguments().getSerializable(ARG_LOCATION);
            mTeacher = (Teacher) getArguments().getSerializable(ARG_TEACHER);
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

    // TODO: this is the test code
    private SubjectDetailsCardAdapter getTestAdapter() {
        ArrayList<Entity> entities = new ArrayList<>(3);
        entities.add(mSubject);
        entities.add(mLocation);
        entities.add(mTeacher);

        return new SubjectDetailsCardAdapter(entities);
    }

}
