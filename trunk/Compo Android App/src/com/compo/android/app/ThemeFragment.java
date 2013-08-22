package com.compo.android.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.model.Theme;

public class ThemeFragment extends Fragment {

    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.ThemeFragment.MESSAGE.ARG";

    private static Typeface _font;

    private TextView _themeName;
    private ImageView _themeImage;
    private Theme _currentTheme;
    private View _contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_theme, container, false);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getActivity().getAssets(), "MyLuckyPenny.ttf");
	}

	_themeName = (TextView) rootView.findViewById(R.id.theme_name);
	_themeImage = (ImageView) rootView.findViewById(R.id.theme_image_id);
	_contentView = (View) rootView.findViewById(R.id.theme_content_layout_id);

	Bundle args = getArguments();
	_currentTheme = (Theme) args.getSerializable(EXTRA_MESSAGE_ARG);
	_themeName.setText(_currentTheme.getName());
	_themeName.setTypeface(_font);

	int id = getResources().getIdentifier("theme_" + _currentTheme.getCode(), "drawable",
		getActivity().getPackageName());
	if (id != 0) {
	    _themeImage.setImageResource(id);
	} else {
	    _themeImage.setImageResource(R.drawable.theme_world_cup);
	}

	_contentView.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		Intent intent = new Intent(getActivity(), SelectPackActivity.class);
		intent.putExtra(EXTRA_MESSAGE_ARG, _currentTheme);
		startActivity(intent);
		getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	    }
	});

	return rootView;
    }

}