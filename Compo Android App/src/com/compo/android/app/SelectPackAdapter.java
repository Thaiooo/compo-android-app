package com.compo.android.app;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Theme;

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
	args.putSerializable(PackFragment.MESSAGE_THEME, _theme);
	Pack currentPack = _packs.get(i);
	Pack previousPack = null;
	if (i > 0) {
	    previousPack = _packs.get(i - 1);
	}
	args.putSerializable(PackFragment.MESSAGE_CURRENT_PACK, currentPack);
	args.putSerializable(PackFragment.MESSAGE_PREVIOUS_PACK, previousPack);
	args.putSerializable(PackFragment.MESSAGE_CURRENT_PACK_PROGRESS, _mapPackToProgress.get(currentPack.getId()));
	if (previousPack != null) {
	    args.putSerializable(PackFragment.MESSAGE_PREVIOUS_PACK_PROGRESS,
		    _mapPackToProgress.get(previousPack.getId()));
	}
	fragment.setArguments(args);
	return fragment;
    }

    public int getItemPosition(Object item) {
	PackFragment fragment = (PackFragment) item;
	Pack pack = fragment.getCurrentPack();
	int position = _packs.indexOf(pack);

	PackProgress previousPackProgress = null;
	if (position > 0) {
	    Pack previousPack = _packs.get(position - 1);
	    previousPackProgress = _mapPackToProgress.get(previousPack.getId());
	}
	PackProgress progress = _mapPackToProgress.get(pack.getId());
	fragment.refresh(progress, previousPackProgress);
	return position;
    }

    @Override
    public int getCount() {
	return _packs.size();
    }

}