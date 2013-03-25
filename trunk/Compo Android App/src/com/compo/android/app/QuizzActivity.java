package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Quizz;

public class QuizzActivity extends Activity {

    private Pack _selectGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_quizz);

	QuizzView quizzView = (QuizzView) findViewById(R.id.quizz_view);
	if (quizzView == null) {
	    quizzView = (QuizzView) findViewById(R.id.quizz_view_land);
	}

	Intent intent = getIntent();

	Quizz selectQuizz = (Quizz) intent.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_QUIZZ);
	_selectGame = (Pack) getIntent().getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_GAME);

	quizzView.setQuizz(selectQuizz);
    }

}
