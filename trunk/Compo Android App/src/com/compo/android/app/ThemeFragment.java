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
import com.compo.android.app.utils.FontEnum;

public class ThemeFragment extends Fragment {

    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.ThemeFragment.MESSAGE.ARG";

    private static Typeface _font;

    private TextView _themeName;
    private ImageView _themeImage;
    private View _contentView;
    private ImageView _imageLock;

    private Theme _currentTheme;

    private final static String THEME_PREFIX = "theme_";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_theme, container, false);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getActivity().getAssets(), FontEnum.LUCKY_PENNY.getName());
	}

	_themeName = (TextView) rootView.findViewById(R.id.theme_name);
	_themeImage = (ImageView) rootView.findViewById(R.id.theme_image_id);
	_contentView = (View) rootView.findViewById(R.id.theme_content_layout_id);
	_imageLock = (ImageView) rootView.findViewById(R.id.theme_lock_id);

	Bundle args = getArguments();
	_currentTheme = (Theme) args.getSerializable(EXTRA_MESSAGE_ARG);
	_themeName.setText(_currentTheme.getName());
	_themeName.setTypeface(_font);

	int id = getResources().getIdentifier(THEME_PREFIX + _currentTheme.getCode(), "drawable",
		getActivity().getPackageName());
	if (id != 0) {
	    _themeImage.setImageResource(id);
	} else {
	    _themeImage.setImageResource(R.drawable.theme_world_cup);
	}

	if (_currentTheme.isLock()) {
	    _imageLock.setVisibility(View.VISIBLE);
	    _contentView.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		    Intent intent = new Intent(getActivity(), ThemeDetailsActivity.class);
		    intent.putExtra(ThemeDetailsActivity.MESSAGE_SELECTED_THEME, _currentTheme);
		    startActivity(intent);
		    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	    });
	} else {
	    _imageLock.setVisibility(View.INVISIBLE);
	    _contentView.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		    Intent intent = new Intent(getActivity(), SelectPackActivity.class);
		    intent.putExtra(EXTRA_MESSAGE_ARG, _currentTheme);
		    startActivityForResult(intent, MainActivity.EXTRA_MESSAGE_REQUEST_CODE);
		    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	    });
	}

	return rootView;
    }
}