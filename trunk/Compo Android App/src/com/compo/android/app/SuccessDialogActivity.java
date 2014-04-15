package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.utils.FontEnum;

public class SuccessDialogActivity extends Activity {
	public final static String MESSAGE_DISPLAY_NEXT = "com.compo.android.app.SuccessDialogActivity.MESSAGE1";
	public final static String MESSAGE_QUIZZ_PLAYER = "com.compo.android.app.SuccessDialogActivity.MESSAGE2";

	private QuizzPlayer _currentQuizz;
	private static Typeface _font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_success_dialog);

		Intent intent = getIntent();
		_currentQuizz = (QuizzPlayer) intent.getSerializableExtra(MESSAGE_QUIZZ_PLAYER);
		boolean displayNextButton = (Boolean) intent.getSerializableExtra(MESSAGE_DISPLAY_NEXT);

		TextView earnCreditValue = (TextView) findViewById(R.id.earn_credit_value);
		if (_font == null) {
			_font = Typeface.createFromAsset(getAssets(), FontEnum.ERASER.getName());
		}
		earnCreditValue.setTypeface(_font);
		earnCreditValue.setText(Integer.toString(_currentQuizz.getEarnCredit()));

		LinearLayout layoutSuccessAndNextButton = (LinearLayout) findViewById(R.id.button_layout);
		LinearLayout layoutSuccessOnlyButton = (LinearLayout) findViewById(R.id.button_success_only_layout);

		if (displayNextButton) {
			layoutSuccessOnlyButton.setVisibility(View.INVISIBLE);
			layoutSuccessAndNextButton.setVisibility(View.VISIBLE);

		} else {
			layoutSuccessOnlyButton.setVisibility(View.VISIBLE);
			layoutSuccessAndNextButton.setVisibility(View.INVISIBLE);

		}

	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void cancel(View view) {
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void success(View view) {
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void next(View view) {
		Intent returnIntent = new Intent();
		setResult(RESULT_FIRST_USER, returnIntent);
		finish();
	}

}
