package com.d.candy.f.awesometimetable;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.d.candy.f.awesometimetable.utils.BundleBuilder;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;

/**
 * Created by daichi on 7/23/17.
 */

public class PickSubjectDialogFragment extends DialogFragment {

    public interface OnItemSelectListener {
        void onItemSelected(final int index, final CharSequence[] itemList);
    }

    private static final String ARG_ITEM_LIST = "item_list";
    private OnItemSelectListener mListener = null;
    private CharSequence[] mItemList;

    public static PickSubjectDialogFragment newInstance(@NonNull CharSequence[] itemList) {
        PickSubjectDialogFragment fragment = new PickSubjectDialogFragment();
        Bundle bundle = new BundleBuilder().put(ARG_ITEM_LIST, itemList).build();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mItemList = getArguments().getCharSequenceArray(ARG_ITEM_LIST);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("PICK A SUBJECT").setNegativeButton("Close", null)
                .setItems(mItemList,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onItemSelected(which, mItemList);
                        }
                    }
                });

        return builder.create();
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        mListener = onItemSelectListener;
    }
}
