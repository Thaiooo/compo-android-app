package com.compo.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.compo.android.app.model.Quizz;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;

public class QuizzActivity extends FragmentActivity {

    // private Pack _selectGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_quizz);

	Intent intent = getIntent();
	Quizz selectQuizz = (Quizz) intent.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_QUIZZ);
	// _selectGame = (Pack) getIntent().getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_GAME);

	QuizzView quizzView = (QuizzView) findViewById(R.id.quizz_view);
	if (quizzView == null) {
	    quizzView = (QuizzView) findViewById(R.id.quizz_view_land);
	}

	quizzView.setQuizz(selectQuizz);

	Team home = null;
	Team away = null;
	for (QuizzPlayer qp : selectQuizz.getQuizzList()) {
	    if (home != null && away != null) {
		break;
	    }
	    if (qp.isHome()) {
		home = qp.getTeam();
	    } else {
		away = qp.getTeam();
	    }
	}
	if (home != null) {
	    TextView teamHome = (TextView) findViewById(R.id.team_home);
	    teamHome.setText(home.getName() + " (" + selectQuizz.getScoreHome() + ")");
	}
	if (away != null) {
	    TextView teamAway = (TextView) findViewById(R.id.team_away);
	    teamAway.setText(away.getName() + " (" + selectQuizz.getScoreAway() + ")");
	}

    }

}
