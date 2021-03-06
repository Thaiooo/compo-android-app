package com.compo.android.app;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.compo.android.app.model.Theme;

public class SelectThemeAdapter extends FragmentStatePagerAdapter {

	private List<Theme> _themes;

	public SelectThemeAdapter(FragmentManager fm, List<Theme> aThemes) {
		super(fm);
		_themes = aThemes;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new ThemeFragment();
		Bundle args = new Bundle();
		args.putSerializable(ThemeFragment.EXTRA_MESSAGE_ARG, _themes.get(i));
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return _themes.size();
	}

}