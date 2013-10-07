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

    // private TextView _theme_name;
    // private TextView _pack_name;
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
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(), FontEnum.DRAWING_GUIDES.getName());
	}

	Intent intent = getIntent();
	_selectTheme = (Theme) intent.getSerializableExtra(PackFragment.MESSAGE_THEME);
	_selectPack = (Pack) intent.getSerializableExtra(PackFragment.MESSAGE_CURRENT_PACK);

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

		    // Match nextMatch = null;
		    // if (aPosition == aMatchList.size()) {
		    // nextMatch = aMatchList.get(0);
		    // } else {
		    // nextMatch = aMatchList.get(aPosition + 1);
		    // }

		    // for (int i = aPosition + 1; i < aMatchList.size(); i++) {
		    // Match m = aMatchList.get(i);
		    //
		    // int nbResponse = 0;
		    // for (QuizzPlayer quizz : m.getQuizzs()) {
		    // if (!quizz.isHide()) {
		    // continue;
		    // }
		    // String playerName = quizz.getPlayer().getName();
		    // Play play = _mapQuizzToPlay.get(quizz.getId());
		    // if (play != null && playerName.equals(play.getResponse())) {
		    // nbResponse++;
		    // } else {
		    // break;
		    // }
		    // }
		    //
		    // if (m.getQuizzs().size() != nbResponse) {
		    // nextMatch = m;
		    // }
		    // }

		    Intent intent = new Intent(SelectMatchActivity.this, MatchActivity.class);
		    intent.putExtra(MatchActivity.REQ_MESSAGE_MATCH, selectMatch);
		    // intent.putExtra(QuizzActivity.REQ_MESSAGE_NEXT_MATCH, nextMatch);
		    intent.putExtra(MatchActivity.REQ_MESSAGE_PACK, _selectPack);
		    startActivityForResult(intent, EXTRA_MESSAGE_REQUEST_CODE);

		    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	    });
	}
    }

}
