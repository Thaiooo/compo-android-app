package com.compo.android.app;

import java.io.IOException;
import java.util.List;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compo.android.app.dao.DataBaseHelper;
import com.compo.android.app.dao.ThemeDao;
import com.compo.android.app.model.Theme;
import com.compo.android.app.utils.FontEnum;

public class MainActivity extends AbstractLSEFragmentActivity {

	public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

	private static Typeface _fontTitle;
	private ViewPager _mViewPager;
	private TextView _activityTitle;
	private LinearLayout _indicatorListLayout;
	private Button _buttonPreview;
	private Button _buttonNext;
	private int _pageSize = 0;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_main;
	}

	@Override
	protected void createDatabse() {
		DataBaseHelper myDbHelper = new DataBaseHelper(this);
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (_fontTitle == null) {
			_fontTitle = Typeface.createFromAsset(getAssets(),
					FontEnum.ACTIVITY_TITLE.getName());
		}

		_mViewPager = (ViewPager) findViewById(R.id.pager);
		_indicatorListLayout = (LinearLayout) findViewById(R.id.theme_indicator_list_layout);
		_buttonPreview = (Button) findViewById(R.id.button_preview);
		_buttonNext = (Button) findViewById(R.id.button_next);

		_activityTitle = (TextView) findViewById(R.id.activity_theme_title);
		_activityTitle.setTypeface(_fontTitle);

		_mViewPager.setOnPageChangeListener(new PageListener());

		new LoadThemeTask().execute();

		Button btnBack = (Button) findViewById(R.id.button_back);
		btnBack.setVisibility(View.INVISIBLE);
		Button btnHome = (Button) findViewById(R.id.button_home);
		btnHome.setVisibility(View.INVISIBLE);
	}

	// @Override
	// protected void onRestart() {
	// super.onRestart();
	// new LoadUserTask().execute();
	// }

	public void previewTheme(View view) {
		_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() - 1, true);

		_indicatorListLayout.removeAllViews();
		for (int i = 0; i < _pageSize; i++) {
			ImageView ball = new ImageView(MainActivity.this);
			if (i == _mViewPager.getCurrentItem()) {
				ball.setImageResource(R.drawable.element_selected);
			} else {
				ball.setImageResource(R.drawable.element_unselected);
			}
			_indicatorListLayout.addView(ball);
		}

		if (_mViewPager.getCurrentItem() == 0) {
			_buttonPreview.setVisibility(View.INVISIBLE);
		}
		_buttonNext.setVisibility(View.VISIBLE);
	}

	public void nextTheme(View view) {
		_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() + 1, true);
		_indicatorListLayout.removeAllViews();
		for (int i = 0; i < _pageSize; i++) {
			ImageView ball = new ImageView(MainActivity.this);
			if (i == _mViewPager.getCurrentItem()) {
				ball.setImageResource(R.drawable.element_selected);
			} else {
				ball.setImageResource(R.drawable.element_unselected);
			}
			_indicatorListLayout.addView(ball);
		}

		if (_mViewPager.getCurrentItem() == (_pageSize - 1)) {
			_buttonNext.setVisibility(View.INVISIBLE);
		}
		_buttonPreview.setVisibility(View.VISIBLE);
	}

	private class LoadThemeTask extends AsyncTask<Void, Void, List<Theme>> {
		@Override
		protected List<Theme> doInBackground(Void... params) {
			ThemeDao dao = new ThemeDao(MainActivity.this);
			List<Theme> themes = dao.getAllTheme();
			return themes;
		}

		@Override
		protected void onPostExecute(final List<Theme> aThemes) {
			SelectThemeAdapter collectionThemeLevelPagerAdapter = new SelectThemeAdapter(
					getSupportFragmentManager(), aThemes);
			_mViewPager.setAdapter(collectionThemeLevelPagerAdapter);

			for (int i = 0; i < aThemes.size(); i++) {
				ImageView ball = new ImageView(MainActivity.this);
				if (i == 0) {
					ball.setImageResource(R.drawable.element_selected);
				} else {
					ball.setImageResource(R.drawable.element_unselected);
				}
				_indicatorListLayout.addView(ball);
			}

			if (aThemes.size() == 0) {
				_buttonNext.setVisibility(View.INVISIBLE);
			}

			_pageSize = aThemes.size();
		}
	}

	private class PageListener extends SimpleOnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			_indicatorListLayout.removeAllViews();
			for (int i = 0; i < _pageSize; i++) {
				ImageView ball = new ImageView(MainActivity.this);
				if (i == position) {
					ball.setImageResource(R.drawable.element_selected);
				} else {
					ball.setImageResource(R.drawable.element_unselected);
				}
				_indicatorListLayout.addView(ball);
			}

			if (_mViewPager.getCurrentItem() == (_pageSize - 1)) {
				_buttonNext.setVisibility(View.INVISIBLE);
				_buttonPreview.setVisibility(View.VISIBLE);
			} else if (_mViewPager.getCurrentItem() == 0) {
				_buttonNext.setVisibility(View.VISIBLE);
				_buttonPreview.setVisibility(View.INVISIBLE);
			} else {
				_buttonNext.setVisibility(View.VISIBLE);
				_buttonPreview.setVisibility(View.VISIBLE);
			}
		}
	}

}
