package com.compo.android.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.model.Theme;

public class ThemeLevelFragment extends Fragment {

    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.ThemeLevelFragment.MESSAGE.ARG";

    private static Typeface _font;
    private TextView _themeName;
    private ImageView _themeImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View rootView = inflater.inflate(R.layout.fragment_theme, container, false);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getActivity().getAssets(), "MyLuckyPenny.ttf");
	}

	_themeName = (TextView) rootView.findViewById(R.id.theme_name);
	_themeImage = (ImageView) rootView.findViewById(R.id.theme_image_id);

	Bundle args = getArguments();
	Theme theme = (Theme) args.getSerializable(EXTRA_MESSAGE_ARG);
	_themeName.setText(theme.getName());
	_themeName.setTypeface(_font);

	int id = getResources().getIdentifier(theme.getCode(), "drawable", getActivity().getPackageName());
	if (id != 0) {
	    _themeImage.setImageResource(id);
	} else {
	    _themeImage.setImageResource(R.drawable.world_cup);
	}

	return rootView;
    }

}