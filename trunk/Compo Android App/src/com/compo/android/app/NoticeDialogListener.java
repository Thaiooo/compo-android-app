package com.compo.android.app;

import android.support.v4.app.DialogFragment;

/*
 * The activity that creates an instance of this dialog fragment must implement this interface in order to receive
 * event callbacks. Each method passes the DialogFragment in case the host needs to query it.
 */
public interface NoticeDialogListener {
    public void onDialogPositiveClick(DialogFragment dialog);
    public void onDialogNegativeClick(DialogFragment dialog);
}
