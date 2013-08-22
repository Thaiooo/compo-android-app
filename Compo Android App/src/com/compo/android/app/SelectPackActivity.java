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
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class SelectPackActivity extends AbstractLSEFragmentActivity {

    public final static String MESSAGE_SELECTED_PACK = "com.compo.android.app.SelectGameActivity.MESSAGE1";
    private static Typeface _fontTitle;
    private ViewPager _mViewPager;
    private TextView _userCredit;
    private TextView _userPoint;
    private TextView _themeName;
    private Theme _selectTheme;
    private SelectPackAdapter _collectionPacksPagerAdapter;
    private Map<Long, PackProgress> _mapPackToProgress;
    private LinearLayout _packIndicatorListLayout;
    private Button _button_preview;
    private Button _button_next;
    private int pageSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_pack);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(), "DrawingGuides.ttf");
	}

	Intent intent = getIntent();
	_selectTheme = (Theme) intent.getSerializableExtra(ThemeFragment.EXTRA_MESSAGE_ARG);

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);
	_mViewPager = (ViewPager) findViewById(R.id.pager);
	_themeName = (TextView) findViewById(R.id.theme_name);
	_themeName.setTypeface(_fontTitle);
	_themeName.setText(_selectTheme.getName());

	_packIndicatorListLayout = (LinearLayout) findViewById(R.id.pack_indicator_list_layout);
	_button_preview = (Button) findViewById(R.id.button_preview);
	_button_next = (Button) findViewById(R.id.button_next);

	_mViewPager.setOnPageChangeListener(new PageListener());

	new LoadUserTask().execute();
	new LoadPackTask().execute();
    }

    public void previewTheme(View view) {
	_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() - 1, true);

	_packIndicatorListLayout.removeAllViews();
	for (int i = 0; i < pageSize; i++) {
	    ImageView ball = new ImageView(SelectPackActivity.this);
	    if (i == _mViewPager.getCurrentItem()) {
		ball.setImageResource(R.drawable.ball_red);
	    } else {
		ball.setImageResource(R.drawable.ball);
	    }
	    _packIndicatorListLayout.addView(ball);
	}

	if (_mViewPager.getCurrentItem() == 0) {
	    _button_preview.setVisibility(View.INVISIBLE);
	}
	_button_next.setVisibility(View.VISIBLE);
    }

    public void nextTheme(View view) {
	_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() + 1, true);
	_packIndicatorListLayout.removeAllViews();
	for (int i = 0; i < pageSize; i++) {
	    ImageView ball = new ImageView(SelectPackActivity.this);
	    if (i == _mViewPager.getCurrentItem()) {
		ball.setImageResource(R.drawable.ball_red);
	    } else {
		ball.setImageResource(R.drawable.ball);
	    }
	    _packIndicatorListLayout.addView(ball);
	}

	if (_mViewPager.getCurrentItem() == (pageSize - 1)) {
	    _button_next.setVisibility(View.INVISIBLE);
	}
	_button_preview.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
	super.onRestart();
	PackProgressDao packProgressDao = new PackProgressDao(SelectPackActivity.this);
	_mapPackToProgress = packProgressDao.getAllPackProgress(_selectTheme.getId());

	System.out.println("====>" + _mapPackToProgress);

	_collectionPacksPagerAdapter.setMapPackToProgress(_mapPackToProgress);
	// _collectionPacksPagerAdapter.getItem(0).getFragmentManager().;
	// _collectionPacksPagerAdapter.notifyAll();
	// TODO A tester
	_mViewPager.invalidate();
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
	    PackDao packDao = new PackDao(SelectPackActivity.this);
	    List<Pack> packs = packDao.findPacks(_selectTheme);

	    PackProgressDao packProgressDao = new PackProgressDao(SelectPackActivity.this);
	    _mapPackToProgress = packProgressDao.getAllPackProgress(_selectTheme.getId());
	    System.out.println("====>" + _mapPackToProgress);

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
		    ball.setImageResource(R.drawable.ball_red);
		} else {
		    ball.setImageResource(R.drawable.ball);
		}
		_packIndicatorListLayout.addView(ball);
	    }

	    pageSize = aPacks.size();
	}
    }

    private class PageListener extends SimpleOnPageChangeListener {
	@Override
	public void onPageSelected(int position) {
	    super.onPageSelected(position);
	    _packIndicatorListLayout.removeAllViews();
	    for (int i = 0; i < pageSize; i++) {
		ImageView ball = new ImageView(SelectPackActivity.this);
		if (i == position) {
		    ball.setImageResource(R.drawable.ball_red);
		} else {
		    ball.setImageResource(R.drawable.ball);
		}
		_packIndicatorListLayout.addView(ball);
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
