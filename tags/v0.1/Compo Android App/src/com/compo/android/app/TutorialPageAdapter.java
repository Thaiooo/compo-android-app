package com.compo.android.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TutorialPageAdapter extends FragmentStatePagerAdapter {

    public TutorialPageAdapter(FragmentManager fm) {
	super(fm);
    }

    @Override
    public Fragment getItem(int i) {
	Fragment fragment = new TutorialPageFragment();
	return fragment;
    }

    @Override
    public int getCount() {
	return 3;
    }

}