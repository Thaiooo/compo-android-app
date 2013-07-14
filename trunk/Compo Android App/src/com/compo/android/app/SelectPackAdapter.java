package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Theme;

import java.util.List;
import java.util.Map;

public class SelectPackAdapter extends FragmentStatePagerAdapter {

    private Theme _theme;
    private List<Pack> _packs;
    private Map<Long, PackProgress> _mapPackToProgress;

    public SelectPackAdapter(FragmentManager fm, Theme aTheme, List<Pack> aPacks,
	    Map<Long, PackProgress> aMapPackToProgress) {
	super(fm);
	_theme = aTheme;
	_packs = aPacks;
	_mapPackToProgress = aMapPackToProgress;
    }

    public void setMapPackToProgress(Map<Long, PackProgress> aMapPackToProgress) {
	_mapPackToProgress = aMapPackToProgress;
    }

    @Override
    public Fragment getItem(int i) {
	Fragment fragment = new PackFragment();
	Bundle args = new Bundle();
	args.putSerializable(PackFragment.EXTRA_MESSAGE_THEME, _theme);
	Pack pack = _packs.get(i);
	args.putSerializable(PackFragment.EXTRA_MESSAGE_PACK, pack);
	args.putSerializable(PackFragment.EXTRA_MESSAGE_PACK_PROGRESS, _mapPackToProgress.get(pack.getId()));
	fragment.setArguments(args);
	return fragment;
    }

    @Override
    public int getCount() {
	return _packs.size();
    }

}