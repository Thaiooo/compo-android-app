package com.compo.android.app;

import java.util.Map;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class QuizzActivity extends AbstractLSEFragmentActivity {

    // private static final String TAG = QuizzActivity.class.getName();

    public static final String EXTRA_MESSAGE_RESULT = "com.compo.android.app.QuizzActivity.MESSAGE.RESULT";
    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

    private static Typeface _font;
    private static Typeface _fontSocrePrinter;
    private TextView _userCredit;
    private TextView _userPoint;
    private QuizzView _quizzView;
    private Map<Long, Play> _mapQuizzToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_quizz);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}

	if (_fontSocrePrinter == null) {
	    _fontSocrePrinter = Typeface.createFromAsset(getAssets(), "light_led_board.ttf");
	}

	PlayDao playDao = new PlayDao(this);
	User u = UserFactory.getInstance().getUser(QuizzActivity.this);
	_mapQuizzToPlay = playDao.getAllPlay(u.getId());

	Intent intent = getIntent();
	Match selectMatch = (Match) intent.getSerializableExtra(SelectMatchActivity.EXTRA_MESSAGE_QUIZZ);

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);
	_quizzView = (QuizzView) findViewById(R.id.quizz_view);
	_quizzView.setQuizz(selectMatch);
	_quizzView.setMapQuizzToPlay(_mapQuizzToPlay);

	new LoadUserTask().execute();

	Team home = null;
	Team away = null;
	for (QuizzPlayer qp : selectMatch.getQuizzs()) {
	    if (home != null && away != null) {
		break;
	    }
	    if (qp.isHome()) {
		home = qp.getTeam();
	    } else {
		away = qp.getTeam();
	    }
	}

	TextView matchDetail = (TextView) findViewById(R.id.match_details);
	matchDetail.setTypeface(_font);
	matchDetail.setText(selectMatch.getName());

	TextView teamAway = (TextView) findViewById(R.id.match_teams);
	teamAway.setTypeface(_font);
	if (away != null && home != null) {
	    teamAway.setText(home.getName() + " - " + away.getName());
	} else {
	    teamAway.setText("");
	}

	Button scorePrinter = (Button) findViewById(R.id.score_printer);
	scorePrinter.setTypeface(_fontSocrePrinter);
	scorePrinter.setText(selectMatch.getScoreHome() + " - " + selectMatch.getScoreAway());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (data == null) {
	    return;
	}
	Play play = (Play) data.getSerializableExtra(QuizzActivity.EXTRA_MESSAGE_RESULT);

	switch (requestCode) {
	case EXTRA_MESSAGE_REQUEST_CODE:

	    _mapQuizzToPlay.put(play.getQuizzId(), play);
	    _quizzView.setMapQuizzToPlay(_mapQuizzToPlay);

	    if (resultCode == RESULT_OK) {
		_quizzView.invalidate();
		User u = UserFactory.getInstance().getUser(QuizzActivity.this);
		_userCredit.setText(u.getCredit() + "");
		_userPoint.setText(u.getPoint() + " pts");
		Toast.makeText(this, "You have found " + " " + play.getResponse(), Toast.LENGTH_LONG).show();
		break;
	    }
	}
    }

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
	@Override
	protected Void doInBackground(Void... params) {
	    User u = UserFactory.getInstance().getUser(QuizzActivity.this);
	    _userCredit.setText(u.getCredit() + "");
	    _userPoint.setText(u.getPoint() + " pts");
	    return null;
	}

    }
}
