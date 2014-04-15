package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TutorialPageFragment extends Fragment {

	public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.ThemeFragment.MESSAGE.ARG";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tutorial_page, container, false);
		return rootView;
	}

}