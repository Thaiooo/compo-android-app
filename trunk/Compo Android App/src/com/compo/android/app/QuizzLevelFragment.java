package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuizzLevelFragment extends Fragment {

	public static final String ARG_OBJECT = "object";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_quizz_menu,
				container, false);
		Bundle args = getArguments();
		((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer
				.toString(args.getInt(ARG_OBJECT)));
		return rootView;
	}
}