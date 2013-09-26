package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.compo.android.app.model.QuizzPlayer;

public class SuccessDialogActivity extends Activity {
    // public final static String MESSAGE_HINT_TYPE = "com.compo.android.app.SuccessDialogActivity.MESSAGE1";
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
	// HintTypeEnum hintType = (HintTypeEnum) intent.getSerializableExtra(MESSAGE_HINT_TYPE);

	TextView earnCreditValue = (TextView) findViewById(R.id.earn_credit_value);
	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
	}
	earnCreditValue.setTypeface(_font);
	earnCreditValue.setText(Integer.toString(_currentQuizz.getEarnCredit()));

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

}
