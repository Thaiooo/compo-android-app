package com.compo.android.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.compo.android.app.model.GamePack;
import com.compo.android.app.model.Quizz;

public class QuizzActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quizz);

		QuizzView quizzView = (QuizzView) findViewById(R.id.quizz_view);
		if (quizzView == null) {
			quizzView = (QuizzView) findViewById(R.id.quizz_view_land);
		}

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();

		Quizz selectQuizz = (Quizz) intent
				.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_QUIZZ);

		quizzView.setQuizz(selectQuizz);

		actionBar.setTitle(selectQuizz.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			GamePack selectGame = (GamePack) getIntent().getSerializableExtra(
					QuizzLevelFragment.EXTRA_MESSAGE_GAME);

			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, SelectQuizzActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(SelectGameActivity.EXTRA_MESSAGE, selectGame);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
