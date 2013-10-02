package com.compo.android.app;

import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;
import com.compo.android.app.model.User;
import com.compo.android.app.service.QuizzService;
import com.compo.android.app.utils.UserFactory;

public class MatchActivity extends AbstractLSEFragmentActivity {

    // private static final String TAG = QuizzActivity.class.getName();

    public static final String REQ_MESSAGE_MATCH = "com.compo.android.app.QuizzActivity.MESSAGE.MATCH";
    public static final String REQ_MESSAGE_PACK = "com.compo.android.app.QuizzActivity.MESSAGE.PACK";

    public static final String RESULT_MESSAGE = "com.compo.android.app.QuizzActivity.MESSAGE.RESULT";
    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

    private static Typeface _font;
    private static Typeface _fontSocrePrinter;
    private MatchView _matchView;
    private Map<Long, Play> _mapQuizzToPlay;
    private Match _currentMatch;
    private Pack _currentPack;

    @Override
    protected int getContentViewId() {
	return R.layout.activity_match;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}

	if (_fontSocrePrinter == null) {
	    _fontSocrePrinter = Typeface.createFromAsset(getAssets(), "light_led_board.ttf");
	}

	PlayDao playDao = new PlayDao(this);
	User u = UserFactory.getInstance().getUser(MatchActivity.this);
	_mapQuizzToPlay = playDao.getAllPlay(u.getId());

	Intent intent = getIntent();
	_currentMatch = (Match) intent.getSerializableExtra(MatchActivity.REQ_MESSAGE_MATCH);
	_currentPack = (Pack) intent.getSerializableExtra(MatchActivity.REQ_MESSAGE_PACK);

	_matchView = (MatchView) findViewById(R.id.quizz_view);
	_matchView.setQuizz(_currentMatch);
	_matchView.setMapQuizzToPlay(_mapQuizzToPlay);

	Team home = null;
	Team away = null;
	for (QuizzPlayer qp : _currentMatch.getQuizzs()) {
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
	matchDetail.setText(_currentMatch.getName());

	TextView teamAway = (TextView) findViewById(R.id.match_teams);
	teamAway.setTypeface(_font);
	if (away != null && home != null) {
	    teamAway.setText(home.getName() + " - " + away.getName());
	} else {
	    teamAway.setText("");
	}

	Button scorePrinter = (Button) findViewById(R.id.score_printer);
	scorePrinter.setTypeface(_fontSocrePrinter);
	scorePrinter.setText(_currentMatch.getScoreHome() + " - " + _currentMatch.getScoreAway());

    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (data == null) {
	    return;
	}
	Play play = (Play) data.getSerializableExtra(MatchActivity.RESULT_MESSAGE);
	if (play != null) {
	    _mapQuizzToPlay.put(play.getQuizzId(), play);
	    _matchView.setMapQuizzToPlay(_mapQuizzToPlay);
	}

	User u = UserFactory.getInstance().getUser(MatchActivity.this);
	_userCredit.setText(Integer.toString(u.getCredit()));

	switch (requestCode) {
	case EXTRA_MESSAGE_REQUEST_CODE:
	    if (resultCode == RESULT_OK) {
		_matchView.invalidate();

		if (play != null) {
		    Toast.makeText(this, "You have found " + " " + play.getResponse().toUpperCase(Locale.US),
			    Toast.LENGTH_LONG).show();
		}
		break;
	    } else if (resultCode == RESULT_FIRST_USER) {
		// TODO Cas du next
		// Rechercher le match suivant. Dans un job au début de l'activité
		QuizzService service = new QuizzService(this);
		Match nextMatch = service.getNexMatch(_currentPack, _currentMatch);

		System.out.println("=============> Afficher le suivant");
	    }
	}
    }

}
