package com.compo.android.app;

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

import com.compo.android.app.dao.ThemeDao;
import com.compo.android.app.model.Theme;

public class SelectThemeActivity extends AbstractLSEFragmentActivity {

    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;
    
    private static Typeface _fontTitle;
    private ViewPager _mViewPager;
    private TextView _activity_theme_title;
    private LinearLayout _themeIndicatorListLayout;
    private Button _button_preview;
    private Button _button_next;
    private int pageSize = 0;

    @Override
    protected int getContentViewId() {
	return R.layout.activity_select_theme;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(), "DrawingGuides.ttf");
	}

	_mViewPager = (ViewPager) findViewById(R.id.pager);
	_themeIndicatorListLayout = (LinearLayout) findViewById(R.id.theme_indicator_list_layout);
	_button_preview = (Button) findViewById(R.id.button_preview);
	_button_next = (Button) findViewById(R.id.button_next);

	_activity_theme_title = (TextView) findViewById(R.id.activity_theme_title);
	_activity_theme_title.setTypeface(_fontTitle);

	_mViewPager.setOnPageChangeListener(new PageListener());

	new LoadThemeTask().execute();
    }

    public void previewTheme(View view) {
	_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() - 1, true);

	_themeIndicatorListLayout.removeAllViews();
	for (int i = 0; i < pageSize; i++) {
	    ImageView ball = new ImageView(SelectThemeActivity.this);
	    if (i == _mViewPager.getCurrentItem()) {
		ball.setImageResource(R.drawable.ball_red);
	    } else {
		ball.setImageResource(R.drawable.ball);
	    }
	    _themeIndicatorListLayout.addView(ball);
	}

	if (_mViewPager.getCurrentItem() == 0) {
	    _button_preview.setVisibility(View.INVISIBLE);
	}
	_button_next.setVisibility(View.VISIBLE);
    }

    public void nextTheme(View view) {
	_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() + 1, true);
	_themeIndicatorListLayout.removeAllViews();
	for (int i = 0; i < pageSize; i++) {
	    ImageView ball = new ImageView(SelectThemeActivity.this);
	    if (i == _mViewPager.getCurrentItem()) {
		ball.setImageResource(R.drawable.ball_red);
	    } else {
		ball.setImageResource(R.drawable.ball);
	    }
	    _themeIndicatorListLayout.addView(ball);
	}

	if (_mViewPager.getCurrentItem() == (pageSize - 1)) {
	    _button_next.setVisibility(View.INVISIBLE);
	}
	_button_preview.setVisibility(View.VISIBLE);
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

	    for (int i = 0; i < aThemes.size(); i++) {
		ImageView ball = new ImageView(SelectThemeActivity.this);
		if (i == 0) {
		    ball.setImageResource(R.drawable.ball_red);
		} else {
		    ball.setImageResource(R.drawable.ball);
		}
		_themeIndicatorListLayout.addView(ball);
	    }

	    if (aThemes.size() == 0) {
		_button_next.setVisibility(View.INVISIBLE);
	    }

	    pageSize = aThemes.size();
	}
    }

    private class PageListener extends SimpleOnPageChangeListener {
	@Override
	public void onPageSelected(int position) {
	    super.onPageSelected(position);
	    _themeIndicatorListLayout.removeAllViews();
	    for (int i = 0; i < pageSize; i++) {
		ImageView ball = new ImageView(SelectThemeActivity.this);
		if (i == position) {
		    ball.setImageResource(R.drawable.ball_red);
		} else {
		    ball.setImageResource(R.drawable.ball);
		}
		_themeIndicatorListLayout.addView(ball);
	    }

	    if (_mViewPager.getCurrentItem() == (pageSize - 1)) {
		_button_next.setVisibility(View.INVISIBLE);
		_button_preview.setVisibility(View.VISIBLE);
	    } else if (_mViewPager.getCurrentItem() == 0) {
		_button_next.setVisibility(View.VISIBLE);
		_button_preview.setVisibility(View.INVISIBLE);
	    } else {
		_button_next.setVisibility(View.VISIBLE);
		_button_preview.setVisibility(View.VISIBLE);
	    }
	}
    }
}
