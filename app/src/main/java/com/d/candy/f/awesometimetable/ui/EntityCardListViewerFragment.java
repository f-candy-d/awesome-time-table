package com.d.candy.f.awesometimetable.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.MyVH;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.RecyclerViewScrollObserver;
import com.d.candy.f.awesometimetable.SubjectCardAdapter;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EntityCardListViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntityCardListViewerFragment extends Fragment
        implements RecyclerViewScrollObserver.MessageListener,
        MyVH.BaseViewHolder.OnItemClickListener {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnInteractionListener {
        SubjectCardAdapter getListAdapter();
        void onListScrolled(final RecyclerViewScrollObserver.ScrollDirection direction);
        void onListItemClicked(final int position);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnInteractionListener mListener;
    private SubjectCardAdapter mListAdapter;
    private WeeklyTimeTable mTimeTable;

    public EntityCardListViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntityCardListViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntityCardListViewerFragment newInstance(String param1, String param2) {
        EntityCardListViewerFragment fragment = new EntityCardListViewerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_entity_card_list_viewer, container, false);

        // Initialization
        init();
        initUI(root);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            mListener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mListAdapter = null;
        mTimeTable = null;
    }

    public void delegateAdapterMessageListener(boolean yes) {
        if (yes) {
            mListAdapter.setOnItemClickListener(this);
        } else {
            mListAdapter.setOnItemClickListener(null);
        }
    }

    private void init() {
        mListAdapter = mListener.getListAdapter();
        if (mListAdapter == null) {
            throw new NullPointerException();
        }

        mTimeTable = mListAdapter.getTimeTable();
    }

    private void initUI(View root) {
        // RecyclerView
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyc_subject_card_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Adapter
        recyclerView.setAdapter(mListAdapter);
        // If you want to handle OnItemClick event from the adapter by yourself,
        // set OnItemClickListener before onCreate() of this fragment is called
        if(mListAdapter.isOnItemClickListenerNull()) {
            mListAdapter.setOnItemClickListener(this);
        }

        // Scroll observer
        RecyclerViewScrollObserver scrollObserver = new RecyclerViewScrollObserver(recyclerView);
        scrollObserver.setMessageListener(this);
        // I think '40' is good so far...
        scrollObserver.setIgnorableScrollDistance(40);
    }

    /**
     * Implementation of RecyclerViewScrollObserver.MessageListener interface
     */
    @Override
    public void onScrollDirectionChanged(
            RecyclerViewScrollObserver.ScrollDirection newDirection,
            RecyclerViewScrollObserver.ScrollDirection oldDirection) {
        // Pass a scroll change message to the host activity
        mListener.onListScrolled(newDirection);
    }

    /**
     * Implementation of SubjectCardAdapter.OnItemClickListener interface
     */
    @Override
    public void onItemClick(int position) {
        mListener.onListItemClicked(position);
    }
}
