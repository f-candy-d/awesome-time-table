package com.d.candy.f.awesometimetable.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;
import com.d.candy.f.awesometimetable.utils.TimeDateHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditAssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAssignmentFragment extends Fragment
        implements Button.OnClickListener {

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
        void onOkButtonClicked(final Assignment assignment);
        void onCancelButtonClicked();
    }

    private OnInteractionListener mListener;
    private Assignment mNewAssignment;
    // UI
    private EditText mTitle;
    private EditText mNote;
    private Button mPickYMDButton;
    private Button mPickHDButton;
    private Button mPickEnrollingInfoIDButton;
    private Button mCancelButton;
    private Button mOKButton;

    private static final int BTN_ID_PICK_YMD = 0;
    private static final int BTN_ID_PICK_HD = 1;
    private static final int BTN_ID_PICK_ENROLLING_INFO = 2;
    private static final int BTN_ID_CANCEL = 3;
    private static final int BTN_ID_OK = 4;

    public EditAssignmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditAssignmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditAssignmentFragment newInstance(String param1, String param2) {
        EditAssignmentFragment fragment = new EditAssignmentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_assignment, container, false);

        mTitle = (EditText) view.findViewById(R.id.edit_text_assignment_title);
        mNote = (EditText) view.findViewById(R.id.edit_text_assignment_note);
        mPickYMDButton = (Button) view.findViewById(R.id.btn_pick_year_month_day);
        mPickHDButton = (Button) view.findViewById(R.id.btn_pick_hour_min);
        mPickEnrollingInfoIDButton = (Button) view.findViewById(R.id.btn_pick_enrolling_info_id);
        mCancelButton = (Button) view.findViewById(R.id.btn_cancel);
        mOKButton = (Button) view.findViewById(R.id.btn_ok);

        mPickYMDButton.setOnClickListener(this);
        mPickHDButton.setOnClickListener(this);
        mPickEnrollingInfoIDButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mOKButton.setOnClickListener(this);

        mPickYMDButton.setTag(BTN_ID_PICK_YMD);
        mPickHDButton.setTag(BTN_ID_PICK_HD);
        mPickEnrollingInfoIDButton.setTag(BTN_ID_PICK_ENROLLING_INFO);
        mCancelButton.setTag(BTN_ID_CANCEL);
        mOKButton.setTag(BTN_ID_OK);

        initNewAssignment();

        return view;
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
    }

    @Override
    public void onClick(View v) {
        switch ((Integer) v.getTag()) {
            case BTN_ID_PICK_YMD: {
                DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
                dialogFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mNewAssignment.setDeadlineYear(year);
                        mNewAssignment.setDeadlineMonth(month);
                        mNewAssignment.setDeadlineDay(dayOfMonth);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        mNewAssignment.setDeadlineDayOfWeek(DayOfWeek.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
                        TimeDateHelper helper = new TimeDateHelper(year, month, dayOfMonth);
                        mPickYMDButton.setText(helper.getAsString(false));
                    }
                });

                dialogFragment.show(getActivity().getSupportFragmentManager(), "pick_date");
                break;
            }

            case BTN_ID_PICK_HD: {
                TimePickerDialogFragment dialogFragment = new TimePickerDialogFragment();
                dialogFragment.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mPickHDButton.setText(String.valueOf(hourOfDay) + " : " + String.valueOf(minute));
                    }
                });
                dialogFragment.show(getActivity().getSupportFragmentManager(), "pick_time");
                break;
            }

            case BTN_ID_PICK_ENROLLING_INFO: {
                final ArrayList<EnrollingInfo> infos = DataStructureFactory.makeEnrollingInfoList();
                CharSequence[] names = new CharSequence[infos.size()];

                EnrollingInfo info;
                Subject subject;
                for (int i = 0; i < infos.size(); ++i) {
                    info = infos.get(i);
                    subject = DataStructureFactory.makeSubject(info.getSubjectID());
                    names[i] = subject.getName() + " / Period:"
                            + String.valueOf(info.getPeriod()) + " on " + info.getDayOfWeek().toString();
                }

                PickSubjectDialogFragment dialogFragment = PickSubjectDialogFragment.newInstance(names);
                dialogFragment.setOnItemSelectListener(new PickSubjectDialogFragment.OnItemSelectListener() {
                    @Override
                    public void onItemSelected(int index, CharSequence[] itemList) {
                        mNewAssignment.setEnrollingInfoID(infos.get(index).getID());
                        mPickEnrollingInfoIDButton.setText(itemList[index]);
                    }
                });
                dialogFragment.show(getActivity().getSupportFragmentManager(), "pick_subject");
                break;
            }

            case BTN_ID_CANCEL: {
                if (mListener != null) {
                    mListener.onCancelButtonClicked();
                }
            }

            case BTN_ID_OK: {
                if (mListener != null) {
                    mListener.onOkButtonClicked(mNewAssignment);
                    break;
                }
            }
        }
    }

    private void initNewAssignment() {
        mNewAssignment = new Assignment();
        mNewAssignment.setDone(false);
        mNewAssignment.setDeadlineYear(0);
        mNewAssignment.setDeadlineMonth(0);
        mNewAssignment.setDeadlineYear(0);
        mNewAssignment.setDeadlineDayOfWeek(null);
        mNewAssignment.setEnrollingInfoID(DBContract.EnrollingInfoEntity.NULL_ID);
        mNewAssignment.setNote(null);
        mNewAssignment.setTitle(null);
    }
}
