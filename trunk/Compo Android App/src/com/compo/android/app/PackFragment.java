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

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Theme;

public class PackFragment extends Fragment {

    public static final String EXTRA_MESSAGE_PACK = "com.compo.android.app.PackFragment.MESSAGE.PACK";
    public static final String EXTRA_MESSAGE_THEME = "com.compo.android.app.PackFragment.MESSAGE.THEME";
    public static final String EXTRA_MESSAGE_PACK_PROGRESS = "com.compo.android.app.PackFragment.MESSAGE.PACK_PROGRESS";

    private static Typeface _font;

    private TextView _packName;
    private TextView _progress;
    private ImageView _lockImage;
    private Theme _currentTheme;
    private Pack _currentPack;
    private PackProgress _currentPackProgress;

    private View _contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_pack, container, false);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getActivity().getAssets(), "MyLuckyPenny.ttf");
	}

	Bundle args = getArguments();
	_currentTheme = (Theme) args.getSerializable(EXTRA_MESSAGE_THEME);
	_currentPack = (Pack) args.getSerializable(EXTRA_MESSAGE_PACK);
	_currentPackProgress = (PackProgress) args.getSerializable(EXTRA_MESSAGE_PACK_PROGRESS);

	_packName = (TextView) rootView.findViewById(R.id.pack_name);
	_lockImage = (ImageView) rootView.findViewById(R.id.lock_image);
	_progress = (TextView) rootView.findViewById(R.id.progress);
	_contentView = (View) rootView.findViewById(R.id.pack_content_layout_id);

	_packName.setText(_currentPack.getName());
	_packName.setTypeface(_font);

	_progress.setTypeface(_font);

	if (!_currentPack.isLock()) {
	    _lockImage.setVisibility(View.INVISIBLE);
	    _progress.setVisibility(View.VISIBLE);
	    int progress = 0;
	    if (_currentPackProgress != null) {
		progress = _currentPackProgress.getMatch();
	    }
	    _progress.setText(progress + "/" + _currentPack.getMatchs().size());

	    _contentView.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		    Intent intent = new Intent(getActivity(), SelectMatchActivity.class);
		    intent.putExtra(EXTRA_MESSAGE_THEME, _currentTheme);
		    intent.putExtra(EXTRA_MESSAGE_PACK, _currentPack);
		    startActivity(intent);
		    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	    });
	} else {
	    _lockImage.setVisibility(View.VISIBLE);
	    _progress.setVisibility(View.INVISIBLE);
	}

	return rootView;
    }

}