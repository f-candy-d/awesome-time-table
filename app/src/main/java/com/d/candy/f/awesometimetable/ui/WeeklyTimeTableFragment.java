package com.d.candy.f.awesometimetable.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.DataStructureFactory;
import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.MiniSubjectCardAdapter;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyTimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyTimeTableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // RecyclerView
    private LinearLayoutManager mLayoutManager;
    private MiniSubjectCardAdapter mAdapter;
    // Time-Table
    private WeeklyTimeTable mWeeklyTimeTable;


    public WeeklyTimeTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeeklyTimeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyTimeTableFragment newInstance(String param1, String param2) {
        WeeklyTimeTableFragment fragment = new WeeklyTimeTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_weekly_time_table, container, false);

        initTimeTable();
        initRecyclerView(root);

        return root;
    }

    private void initRecyclerView(View root) {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyc_mini_subject_card_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        // TODO: test code
        DayOfWeek[] order = {
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        };
        // adapter
        mAdapter = new MiniSubjectCardAdapter(mWeeklyTimeTable, order);
        recyclerView.setAdapter(mAdapter);
    }

    private void initTimeTable() {
        mWeeklyTimeTable = DataStructureFactory.makeTimeTable(0);
    }
}
