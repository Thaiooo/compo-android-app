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
import android.widget.Toast;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Theme;

public class PackFragment extends Fragment {

    public static final String MESSAGE_CURRENT_PACK = "com.compo.android.app.PackFragment.MESSAGE.CURRENT.PACK";
    public static final String MESSAGE_PREVIOUS_PACK = "com.compo.android.app.PackFragment.MESSAGE.PREVIOUS.PACK";
    public static final String MESSAGE_THEME = "com.compo.android.app.PackFragment.MESSAGE.THEME";
    public static final String MESSAGE_CURRENT_PACK_PROGRESS = "com.compo.android.app.PackFragment.MESSAGE.CURRENT.PACK_PROGRESS";
    public static final String MESSAGE_PREVIOUS_PACK_PROGRESS = "com.compo.android.app.PackFragment.MESSAGE.PREVIOUS.PACK_PROGRESS";

    private static final double UNLOCK_LIMIT = 0.8d;
    private static final String SLASH = "/";

    private static Typeface _font;

    private TextView _packName;
    private TextView _packDescription;
    private TextView _progress;
    private ImageView _lockImage;
    private Theme _currentTheme;
    private Pack _currentPack;
    private Pack _previousPack;
    private PackProgress _currentPackProgress;
    private PackProgress _previousPackProgress;

    private View _contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_pack, container, false);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getActivity().getAssets(), "MyLuckyPenny.ttf");
	}

	Bundle args = getArguments();
	_currentTheme = (Theme) args.getSerializable(MESSAGE_THEME);
	_currentPack = (Pack) args.getSerializable(MESSAGE_CURRENT_PACK);
	_previousPack = (Pack) args.getSerializable(MESSAGE_PREVIOUS_PACK);
	_currentPackProgress = (PackProgress) args.getSerializable(MESSAGE_CURRENT_PACK_PROGRESS);
	_previousPackProgress = (PackProgress) args.getSerializable(MESSAGE_PREVIOUS_PACK_PROGRESS);

	_packName = (TextView) rootView.findViewById(R.id.pack_name);
	_packDescription = (TextView) rootView.findViewById(R.id.pack_desc);
	_lockImage = (ImageView) rootView.findViewById(R.id.lock_image);
	_progress = (TextView) rootView.findViewById(R.id.progress);
	_contentView = (View) rootView.findViewById(R.id.pack_content_layout_id);

	_packName.setText(_currentPack.getName());
	_packName.setTypeface(_font);

	_packDescription.setText(_currentPack.getDescription());
	_packDescription.setTypeface(_font);

	_progress.setTypeface(_font);

	boolean lock = true;
	if (_previousPack != null) {
	    if (_previousPackProgress != null) {
		double percentPreviousPregress = _previousPackProgress.getNumberOfSuccessMatch()
			/ _previousPack.getMatchs().size();
		if (percentPreviousPregress > UNLOCK_LIMIT) {
		    lock = false;
		}
	    }

	} else {
	    lock = false;
	}

	if (!lock) {
	    _lockImage.setVisibility(View.INVISIBLE);
	    _progress.setVisibility(View.VISIBLE);
	    int progress = 0;
	    if (_currentPackProgress != null) {
		progress = _currentPackProgress.getNumberOfSuccessMatch();
	    }
	    StringBuffer buff = new StringBuffer(progress);
	    buff.append(progress);
	    buff.append(SLASH);
	    buff.append(_currentPack.getMatchs().size());
	    _progress.setText(buff.toString());

	    _contentView.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		    Intent intent = new Intent(getActivity(), SelectMatchActivity.class);
		    intent.putExtra(MESSAGE_THEME, _currentTheme);
		    intent.putExtra(MESSAGE_CURRENT_PACK, _currentPack);
		    startActivityForResult(intent, SelectPackActivity.EXTRA_MESSAGE_REQUEST_CODE);
		    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	    });
	} else {
	    _lockImage.setVisibility(View.VISIBLE);
	    _progress.setVisibility(View.INVISIBLE);

	    _contentView.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		    Toast.makeText(getActivity(), "Completed the previous level before", Toast.LENGTH_LONG).show();
		}
	    });
	}

	return rootView;
    }

    public Pack getCurrentPack() {
	return _currentPack;
    }

    public void refresh(PackProgress aPackProgress) {
	_currentPackProgress = aPackProgress;
	int progress = 0;
	if (_currentPackProgress != null) {
	    progress = _currentPackProgress.getNumberOfSuccessMatch();
	}
	StringBuffer buff = new StringBuffer(progress);
	buff.append(progress);
	buff.append(SLASH);
	buff.append(_currentPack.getMatchs().size());
	_progress.setText(buff.toString());

    }

}