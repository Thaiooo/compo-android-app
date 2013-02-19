package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class QuizzLevelSelectAdapter extends FragmentStatePagerAdapter {

	public QuizzLevelSelectAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new QuizzLevelFragment();
		Bundle args = new Bundle();
		args.putInt(QuizzLevelFragment.ARG_OBJECT, i + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Simple";
		case 1:
			return "Intermédiaire";

		default:
			return "Difficile";
		}
	}
}