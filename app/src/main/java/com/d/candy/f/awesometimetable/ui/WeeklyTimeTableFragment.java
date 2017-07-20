package com.d.candy.f.awesometimetable.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.RecyclerViewScrollObserver;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;
import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.MiniSubjectCardAdapter;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;
import com.d.candy.f.awesometimetable.utils.LogHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyTimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyTimeTableFragment extends Fragment
        implements MiniSubjectCardAdapter.OnItemClickListener,
        RecyclerViewScrollObserver.MessageListener {

    public interface InteractionListener {
        WeeklyTimeTable onRequireTimeTableData();
        void onLaunchSubjectDetailsViewer(EnrollingInfo enrollingInfo);
        void onListViewScrolled(RecyclerViewScrollObserver.ScrollDirection direction);
    }

    private static final String TAG = LogHelper.makeLogTag(WeeklyTimeTableFragment.class);
    // RecyclerView
    private LinearLayoutManager mLayoutManager;
    private MiniSubjectCardAdapter mAdapter;
    private RecyclerViewScrollObserver mScrollObserver;
    // Time-Table
    private WeeklyTimeTable mWeeklyTimeTable;
    // Callback listener
    private InteractionListener mInteractionListener = null;


    public WeeklyTimeTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WeeklyTimeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyTimeTableFragment newInstance() {
        WeeklyTimeTableFragment fragment = new WeeklyTimeTableFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        // The container Activity of this fragment must implements InteractionListener interface
        setInteractionListener((InteractionListener)getActivity());
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
        // The order of shown items
        DayOfWeek[] order = {
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        };
        // adapter
        mAdapter = new MiniSubjectCardAdapter(mWeeklyTimeTable, order, this);
        recyclerView.setAdapter(mAdapter);

        // Scroll observer
        mScrollObserver = new RecyclerViewScrollObserver(recyclerView);
        mScrollObserver.setMessageListener(this);
        // I think '40' is good so far
        mScrollObserver.setIgnorableScrollDistance(40);
    }

    private void initTimeTable() {
        mWeeklyTimeTable = mInteractionListener.onRequireTimeTableData();
    }

    public void setInteractionListener(InteractionListener listener) {
        mInteractionListener = listener;
    }

    /**
     * Implementations of MiniSubjectCardAdapter.OnItemClickListener interface
     */
    @Override
    public void onItemClicked(EnrollingInfo enrollingInfo) {
        mInteractionListener.onLaunchSubjectDetailsViewer(enrollingInfo);
    }

    /**
     * Implementations of RecyclerViewScrollObserver.MessageListener interface
     */
    @Override
    public void onScrollDirectionChanged(
            RecyclerViewScrollObserver.ScrollDirection newDirection,
            RecyclerViewScrollObserver.ScrollDirection oldDirection) {
        Log.d(TAG, "RecyclerView scroll direction changed => " + newDirection.toString());
        mInteractionListener.onListViewScrolled(newDirection);
    }
}
