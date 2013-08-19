package com.compo.android.app;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.compo.android.app.dao.MatchDao;
import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.Theme;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class SelectMatchActivity extends AbstractLSEFragmentActivity {

    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.QuizzLevelFragment.MESSAGE.ARG";
    public static final String EXTRA_MESSAGE_QUIZZ = "com.compo.android.app.QuizzLevelFragment.MESSAGE.QUIZZ";
    public static final String EXTRA_MESSAGE_GAME = "com.compo.android.app.QuizzLevelFragment.MESSAGE.GAME";

    private static Typeface _fontTitle;

    private TextView _userCredit;
    private TextView _userPoint;
    // private TextView _theme_name;
    // private TextView _pack_name;
    private GridView _gridview;
    private Theme _selectTheme;
    private Pack _selectPack;
    private Map<Long, Play> _mapQuizzToPlay;
    private SelectMatchAdapter _selSelectMatchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_match);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(), "DrawingGuides.ttf");
	}

	Intent intent = getIntent();
	_selectTheme = (Theme) intent.getSerializableExtra(PackFragment.EXTRA_MESSAGE_THEME);
	_selectPack = (Pack) intent.getSerializableExtra(PackFragment.EXTRA_MESSAGE_PACK);

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);

	Button title = (Button) findViewById(R.id.button_title);
	title.setTypeface(_fontTitle);
	title.setText(_selectTheme.getName() + "\n" + _selectPack.getName());
	// _theme_name = (TextView) findViewById(R.id.theme_name);
	// _theme_name.setTypeface(_fontTitle);
	// _theme_name.setText(_selectTheme.getName());

	// _pack_name = (TextView) findViewById(R.id.pack_name);
	// _pack_name.setText(_selectPack.getName());
	// _pack_name.setTypeface(_fontTitle);

	_gridview = (GridView) findViewById(R.id.quizzGrid);

	new LoadUserTask().execute();

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

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
	@Override
	protected Void doInBackground(Void... params) {
	    User u = UserFactory.getInstance().getUser(SelectMatchActivity.this);
	    if (null != u) {
		_userCredit.setText(u.getCredit() + "");
		_userPoint.setText(u.getPoint() + " pts");
	    }
	    return null;
	}

    }

    private class LoadMatchTask extends AsyncTask<Object, Void, List<Match>> {
	@Override
	protected List<Match> doInBackground(Object... params) {
	    MatchDao dao = new MatchDao(SelectMatchActivity.this);
	    List<Match> quizzList = dao.getAllQuizz(((Pack) params[0]).getId());

	    PlayDao playDao = new PlayDao(SelectMatchActivity.this);
	    User u = UserFactory.getInstance().getUser(SelectMatchActivity.this);
	    _mapQuizzToPlay = playDao.getAllPlay(u.getId());
	    return quizzList;
	}

	@Override
	protected void onPostExecute(final List<Match> aMatchList) {
	    _selSelectMatchAdapter = new SelectMatchAdapter(SelectMatchActivity.this, aMatchList, _mapQuizzToPlay);
	    _gridview.setAdapter(_selSelectMatchAdapter);
	    _gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int aPosition, long id) {
		    Match selectQuizz = aMatchList.get(aPosition);
		    Intent intent = new Intent(SelectMatchActivity.this, QuizzActivity.class);
		    intent.putExtra(EXTRA_MESSAGE_QUIZZ, selectQuizz);
		    intent.putExtra(EXTRA_MESSAGE_GAME, _selectPack);
		    startActivity(intent);

		    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	    });
	}
    }

}
