package com.compo.android.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class ResponseDialog extends DialogFragment {

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	// Use the Builder class for convenient dialog construction
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	// Get the layout inflater
	LayoutInflater inflater = getActivity().getLayoutInflater();
	// Inflate and set the layout for the dialog
	// Pass null as the parent view because its going in the dialog layout
	builder.setView(inflater.inflate(R.layout.dialog_response, null))
	// Add action buttons
		.setPositiveButton(R.string.button_check, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int id) {
			mListener.onDialogPositiveClick(ResponseDialog.this);
		    }
		}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
			mListener.onDialogNegativeClick(ResponseDialog.this);
			ResponseDialog.this.getDialog().cancel();
		    }
		});

	// Create the AlertDialog object and return it
	return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);
	// Verify that the host activity implements the callback interface
	try {
	    // Instantiate the NoticeDialogListener so we can send events to the host
	    mListener = (NoticeDialogListener) activity;
	} catch (ClassCastException e) {
	    // The activity doesn't implement the interface, throw exception
	    throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
	}
    }
}