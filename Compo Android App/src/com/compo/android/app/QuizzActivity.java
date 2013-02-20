package com.compo.android.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.compo.android.app.model.Quizz;

public class QuizzActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quizz);

		ActionBar actionBar = getActionBar();

		Intent intent = getIntent();
		Quizz selectQuizz = (Quizz) intent
				.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE);
		actionBar.setTitle(selectQuizz.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}
