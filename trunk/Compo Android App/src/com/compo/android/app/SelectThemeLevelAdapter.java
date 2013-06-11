package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.compo.android.app.model.Theme;

import java.util.List;

public class SelectThemeLevelAdapter extends FragmentStatePagerAdapter {

    private List<Theme> _themes;

    public SelectThemeLevelAdapter(FragmentManager fm, List<Theme> aThemes) {
        super(fm);
        _themes = aThemes;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new ThemeLevelFragment();
        Bundle args = new Bundle();
        args.putSerializable(ThemeLevelFragment.EXTRA_MESSAGE_ARG, _themes.get(i));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return _themes.size();
    }

}