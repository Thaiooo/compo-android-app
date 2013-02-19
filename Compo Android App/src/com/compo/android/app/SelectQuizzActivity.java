package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.compo.android.app.model.GamePack;

public class SelectQuizzActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_quizz);

		Intent intent = getIntent();
		GamePack pack = (GamePack) intent
				.getSerializableExtra(SelectGameActivity.EXTRA_MESSAGE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}
