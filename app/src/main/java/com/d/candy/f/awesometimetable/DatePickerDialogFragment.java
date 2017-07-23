package com.d.candy.f.awesometimetable;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by daichi on 7/23/17.
 */

public class DatePickerDialogFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener mListener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), mListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        mListener = listener;
    }
}
