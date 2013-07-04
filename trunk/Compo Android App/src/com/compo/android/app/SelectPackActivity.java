package com.compo.android.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.compo.android.app.dao.PackDao;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Theme;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

import java.util.List;

public class SelectPackActivity extends FragmentActivity {

    public final static String MESSAGE_SELECTED_PACK = "com.compo.android.app.SelectGameActivity.MESSAGE1";
    private static Typeface _fontTitle;
    private ViewPager _mViewPager;
    private TextView _userCredit;
    private TextView _userPoint;
    private TextView _themeName;
    private Theme _selectTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_pack);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
	}

	Intent intent = getIntent();
	_selectTheme = (Theme) intent.getSerializableExtra(ThemeFragment.EXTRA_MESSAGE_ARG);

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);
	_mViewPager = (ViewPager) findViewById(R.id.pager);
	_themeName = (TextView) findViewById(R.id.theme_name);
	_themeName.setTypeface(_fontTitle);
	_themeName.setText(_selectTheme.getName());

	new LoadUserTask().execute();
	new LoadPackTask().execute();
    }

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
	@Override
	protected Void doInBackground(Void... params) {
	    User u = UserFactory.getInstance().getUser(SelectPackActivity.this);
	    if (u != null) {
		_userCredit.setText(u.getCredit() + "");
		_userPoint.setText(u.getPoint() + " pts");
	    }
	    return null;
	}
    }

    private class LoadPackTask extends AsyncTask<Void, Void, List<Pack>> {
	@Override
	protected List<Pack> doInBackground(Void... params) {
	    PackDao dao = new PackDao(SelectPackActivity.this);
	    List<Pack> packs = dao.findPacks(_selectTheme);
	    return packs;
	}

	@Override
	protected void onPostExecute(final List<Pack> aPacks) {
	    SelectPackAdapter collectionPacksPagerAdapter = new SelectPackAdapter(getSupportFragmentManager(),
		    _selectTheme, aPacks);
	    _mViewPager.setAdapter(collectionPacksPagerAdapter);
	}
    }

}
