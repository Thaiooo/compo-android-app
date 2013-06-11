package com.compo.android.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.compo.android.app.model.Quizz;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class QuizzActivity extends FragmentActivity {

    // private Pack _selectGame;
    private TextView _userCredit;
    private TextView _userPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        Intent intent = getIntent();
        Quizz selectQuizz = (Quizz) intent.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_QUIZZ);
        // _selectGame = (Pack) getIntent().getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_GAME);

        _userCredit = (TextView) findViewById(R.id.user_credit);
        _userPoint = (TextView) findViewById(R.id.user_point);
        QuizzView quizzView = (QuizzView) findViewById(R.id.quizz_view);
        quizzView.setQuizz(selectQuizz);

        new LoadUserTask().execute();

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

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            User u = UserFactory.getInstance().getUser();
            _userCredit.setText(u.getCredit() + "");
            _userPoint.setText(u.getPoint() + " pts");
            return null;
        }

    }
}
