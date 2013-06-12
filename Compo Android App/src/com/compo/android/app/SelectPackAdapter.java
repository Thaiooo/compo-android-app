package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Theme;

import java.util.List;

public class SelectPackAdapter extends FragmentStatePagerAdapter {

    private List<Pack> _packs;

    public SelectPackAdapter(FragmentManager fm, List<Pack> aPacks) {
        super(fm);
        _packs = aPacks;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PackFragment();
        Bundle args = new Bundle();
        args.putSerializable(PackFragment.EXTRA_MESSAGE_ARG, _packs.get(i));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return _packs.size();
    }

}