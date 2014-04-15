package com.compo.android.app;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.compo.android.app.dao.MatchDao;
import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.Theme;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.FontEnum;
import com.compo.android.app.utils.UserFactory;

public class SelectMatchActivity extends AbstractLSEFragmentActivity {

	public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.QuizzLevelFragment.MESSAGE.ARG";
	public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

	private static Typeface _fontTitle;

	private GridView _gridview;
	private Theme _selectTheme;
	private Pack _selectPack;
	private Map<Long, Play> _mapQuizzToPlay;
	private SelectMatchAdapter _selSelectMatchAdapter;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_select_match;
	}

	@Override
	protected void createDatabse() {
		// RAS
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (_fontTitle == null) {
			_fontTitle = Typeface.createFromAsset(getAssets(), FontEnum.ACTIVITY_TITLE.getName());
		}

		Intent intent = getIntent();
		_selectTheme = (Theme) intent.getSerializableExtra(PackFragment.MESSAGE_THEME);
		_selectPack = (Pack) intent.getSerializableExtra(PackFragment.MESSAGE_CURRENT_PACK);

		TextView activityTitle = (TextView) findViewById(R.id.activity_match_title);
		activityTitle.setTypeface(_fontTitle);
		activityTitle.setText(_selectTheme.getName() + " - " + _selectPack.getName());

		_gridview = (GridView) findViewById(R.id.quizzGrid);

		Object[] params = { _selectPack };
		new LoadMatchTask().execute(params);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		PlayDao playDao = new PlayDao(SelectMatchActivity.this);
		User u = UserFactory.getInstance().getUser(SelectMatchActivity.this);
		_mapQuizzToPlay = playDao.getAllPlay(u.getId());
		_selSelectMatchAdapter.setMapQuizzToPlay(_mapQuizzToPlay);
		_selSelectMatchAdapter.notifyDataSetInvalidated();
	}

	private class LoadMatchTask extends AsyncTask<Object, Void, List<Match>> {
		@Override
		protected List<Match> doInBackground(Object... params) {
			MatchDao dao = new MatchDao(SelectMatchActivity.this);
			List<Match> matchList = dao.getAllQuizz(((Pack) params[0]).getId());

			PlayDao playDao = new PlayDao(SelectMatchActivity.this);
			User u = UserFactory.getInstance().getUser(SelectMatchActivity.this);
			_mapQuizzToPlay = playDao.getAllPlay(u.getId());
			return matchList;
		}

		@Override
		protected void onPostExecute(final List<Match> aMatchList) {
			_selSelectMatchAdapter = new SelectMatchAdapter(SelectMatchActivity.this, aMatchList, _mapQuizzToPlay);
			_gridview.setAdapter(_selSelectMatchAdapter);
			_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int aPosition, long id) {
					Match selectMatch = aMatchList.get(aPosition);

					Intent intent = new Intent(SelectMatchActivity.this, MatchActivity.class);
					intent.putExtra(MatchActivity.REQ_MESSAGE_MATCH, selectMatch);
					intent.putExtra(MatchActivity.REQ_MESSAGE_PACK, _selectPack);
					startActivityForResult(intent, EXTRA_MESSAGE_REQUEST_CODE);

					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}
			});
		}
	}

}
