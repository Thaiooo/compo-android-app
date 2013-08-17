package com.compo.android.app;

import java.util.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class QuizzActivity extends FragmentActivity {

    // private static final String TAG = QuizzActivity.class.getName();

    public static final String EXTRA_MESSAGE_RESULT = "com.compo.android.app.QuizzActivity.MESSAGE.RESULT";
    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

    private TextView _userCredit;
    private TextView _userPoint;
    private QuizzView _quizzView;
    private Map<Long, Play> _mapQuizzToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_quizz);

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

	TextView teamHome = (TextView) findViewById(R.id.team_home);
	if (home != null) {
	    teamHome.setText(home.getName() + " (" + selectMatch.getScoreHome() + ")");
	} else {
	    teamHome.setText("");
	}
	TextView teamAway = (TextView) findViewById(R.id.team_away);
	if (away != null) {
	    teamAway.setText(away.getName() + " (" + selectMatch.getScoreAway() + ")");
	} else {
	    teamAway.setText("");
	}
    }

    public void home(View view) {
	Intent intent = new Intent(this, MainActivity.class);
	startActivity(intent);
	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
