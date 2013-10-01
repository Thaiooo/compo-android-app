package com.compo.android.app;

import java.util.Locale;
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

    public static final String REQ_MESSAGE_MATCH = "com.compo.android.app.QuizzActivity.MESSAGE.MATCH";
    public static final String REQ_MESSAGE_GAME = "com.compo.android.app.QuizzActivity.MESSAGE.GAME";

    public static final String RESULT_MESSAGE = "com.compo.android.app.QuizzActivity.MESSAGE.RESULT";
    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

    private static Typeface _font;
    private static Typeface _fontSocrePrinter;
    private TextView _userCredit;
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
	Match selectMatch = (Match) intent.getSerializableExtra(QuizzActivity.REQ_MESSAGE_MATCH);

	_userCredit = (TextView) findViewById(R.id.user_credit);
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
	Play play = (Play) data.getSerializableExtra(QuizzActivity.RESULT_MESSAGE);
	if (play != null) {
	    _mapQuizzToPlay.put(play.getQuizzId(), play);
	    _quizzView.setMapQuizzToPlay(_mapQuizzToPlay);
	}

	switch (requestCode) {
	case EXTRA_MESSAGE_REQUEST_CODE:
	    if (resultCode == RESULT_OK) {
		_quizzView.invalidate();
		User u = UserFactory.getInstance().getUser(QuizzActivity.this);
		_userCredit.setText(Integer.toString(u.getCredit()));
		if (play != null) {
		    Toast.makeText(this, "You have found " + " " + play.getResponse().toUpperCase(Locale.US),
			    Toast.LENGTH_LONG).show();
		}
		break;
	    } else if (resultCode == RESULT_FIRST_USER) {
		// TODO Cas du next
		// Rechercher le match suivant. Dans un job au début de l'activité

		System.out.println("=============> Afficher le suivant");
	    }
	}
    }

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
	@Override
	protected Void doInBackground(Void... params) {
	    User u = UserFactory.getInstance().getUser(QuizzActivity.this);
	    _userCredit.setText(Integer.toString(u.getCredit()));
	    return null;
	}

    }
}
