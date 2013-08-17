package com.compo.android.app;

import java.util.List;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.compo.android.app.dao.ThemeDao;
import com.compo.android.app.model.Theme;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class SelectThemeActivity extends FragmentActivity {

    private static Typeface _fontTitle;
    private ViewPager _mViewPager;
    private TextView _userCredit;
    private TextView _userPoint;
    private TextView _activity_theme_title;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_theme);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
	}

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);
	_mViewPager = (ViewPager) findViewById(R.id.pager);
	_activity_theme_title = (TextView) findViewById(R.id.activity_theme_title);
	_activity_theme_title.setTypeface(_fontTitle);

	new LoadUserTask().execute();
	new LoadThemeTask().execute();
    }

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
	@Override
	protected Void doInBackground(Void... params) {
	    User u = UserFactory.getInstance().getUser(SelectThemeActivity.this);
	    _userCredit.setText(u.getCredit() + "");
	    _userPoint.setText(u.getPoint() + " pts");
	    return null;
	}
    }

    public void home(View view) {
	Intent intent = new Intent(this, MainActivity.class);
	startActivity(intent);
	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private class LoadThemeTask extends AsyncTask<Void, Void, List<Theme>> {
	@Override
	protected List<Theme> doInBackground(Void... params) {
	    ThemeDao dao = new ThemeDao(SelectThemeActivity.this);
	    List<Theme> themes = dao.getAllTheme();
	    return themes;
	}

	@Override
	protected void onPostExecute(final List<Theme> aThemes) {
	    SelectThemeAdapter collectionThemeLevelPagerAdapter = new SelectThemeAdapter(getSupportFragmentManager(),
		    aThemes);
	    _mViewPager.setAdapter(collectionThemeLevelPagerAdapter);
	}
    }
}
