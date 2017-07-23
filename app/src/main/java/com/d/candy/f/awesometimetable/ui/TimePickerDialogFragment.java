package com.d.candy.f.awesometimetable.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by daichi on 7/23/17.
 */

public class TimePickerDialogFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener mListener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        return new TimePickerDialog(getActivity(), mListener,
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener listener) {
        mListener = listener;
    }
}
