package com.compo.android.app;

import java.util.List;
import java.util.Map;

import android.content.Intent;
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

import com.compo.android.app.dao.PackDao;
import com.compo.android.app.dao.PackProgressDao;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Theme;
import com.compo.android.app.utils.FontEnum;

public class SelectPackActivity extends AbstractLSEFragmentActivity {

	public final static String MESSAGE_SELECTED_PACK = "com.compo.android.app.SelectGameActivity.MESSAGE1";
	public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

	private static Typeface _fontTitle;
	private ViewPager _mViewPager;
	private TextView _activityTitle;
	private Theme _selectTheme;
	private SelectPackAdapter _collectionPacksPagerAdapter;
	private Map<Long, PackProgress> _mapPackToProgress;
	private LinearLayout _indicatorListLayout;
	private Button _buttonPreview;
	private Button _buttonNext;
	private int _pageSize = 0;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_select_pack;
	}

	@Override
	protected void createDatabse() {
		// RAS
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (_fontTitle == null) {
			_fontTitle = Typeface.createFromAsset(getAssets(), FontEnum.ACTIVITY_TITLE.getName());
		}

		Intent intent = getIntent();
		_selectTheme = (Theme) intent.getSerializableExtra(ThemeFragment.EXTRA_MESSAGE_ARG);

		_mViewPager = (ViewPager) findViewById(R.id.pager);
		_activityTitle = (TextView) findViewById(R.id.activity_pack_title);
		_activityTitle.setTypeface(_fontTitle);
		_activityTitle.setText(_selectTheme.getName());

		_indicatorListLayout = (LinearLayout) findViewById(R.id.pack_indicator_list_layout);
		_buttonPreview = (Button) findViewById(R.id.button_preview);
		_buttonNext = (Button) findViewById(R.id.button_next);

		_mViewPager.setOnPageChangeListener(new PageListener());

		new LoadPackTask().execute();
	}

	public void previewTheme(View view) {
		_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() - 1, true);

		_indicatorListLayout.removeAllViews();
		for (int i = 0; i < _pageSize; i++) {
			ImageView ball = new ImageView(SelectPackActivity.this);
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
			ImageView ball = new ImageView(SelectPackActivity.this);
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

	@Override
	protected void onRestart() {
		super.onRestart();
		PackProgressDao packProgressDao = new PackProgressDao(SelectPackActivity.this);
		_mapPackToProgress = packProgressDao.getAllPackProgressByTheme(_selectTheme.getId());
		_collectionPacksPagerAdapter.setMapPackToProgress(_mapPackToProgress);
		_collectionPacksPagerAdapter.notifyDataSetChanged();
	}

	private class LoadPackTask extends AsyncTask<Void, Void, List<Pack>> {
		@Override
		protected List<Pack> doInBackground(Void... params) {
			PackDao packDao = new PackDao(SelectPackActivity.this);
			List<Pack> packs = packDao.findPacks(_selectTheme);

			PackProgressDao packProgressDao = new PackProgressDao(SelectPackActivity.this);
			_mapPackToProgress = packProgressDao.getAllPackProgressByTheme(_selectTheme.getId());
			return packs;
		}

		@Override
		protected void onPostExecute(final List<Pack> aPacks) {
			_collectionPacksPagerAdapter = new SelectPackAdapter(getSupportFragmentManager(), _selectTheme, aPacks,
					_mapPackToProgress);
			_mViewPager.setAdapter(_collectionPacksPagerAdapter);

			for (int i = 0; i < aPacks.size(); i++) {
				ImageView ball = new ImageView(SelectPackActivity.this);
				if (i == 0) {
					ball.setImageResource(R.drawable.element_selected);
				} else {
					ball.setImageResource(R.drawable.element_unselected);
				}
				_indicatorListLayout.addView(ball);
			}

			if (aPacks.size() == 0) {
				_buttonNext.setVisibility(View.INVISIBLE);
			}

			_pageSize = aPacks.size();
		}
	}

	private class PageListener extends SimpleOnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			_indicatorListLayout.removeAllViews();
			for (int i = 0; i < _pageSize; i++) {
				ImageView ball = new ImageView(SelectPackActivity.this);
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
