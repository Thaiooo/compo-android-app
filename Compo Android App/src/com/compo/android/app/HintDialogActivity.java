package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class HintDialogActivity extends Activity {
    public final static String MESSAGE_HINT_TYPE = "com.compo.android.app.HintDetailsActivity.MESSAGE1";
    public final static String MESSAGE_QUIZZ_PLAYER = "com.compo.android.app.HintDetailsActivity.MESSAGE2";

    private static Typeface _font;

    private QuizzPlayer _currentQuizz;
    private HintTypeEnum _hintType;
    private User _currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_hint_details);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}

	_currentUser = UserFactory.getInstance().getUser(this);

	Intent intent = getIntent();
	_currentQuizz = (QuizzPlayer) intent.getSerializableExtra(MESSAGE_QUIZZ_PLAYER);
	_hintType = (HintTypeEnum) intent.getSerializableExtra(MESSAGE_HINT_TYPE);

	TextView title = (TextView) findViewById(R.id.hint_title);
	TextView description = (TextView) findViewById(R.id.hint_description);
	TextView price = (TextView) findViewById(R.id.hint_price);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
	}
	title.setTypeface(_font);
	description.setTypeface(_font);
	price.setTypeface(_font);

	switch (_hintType) {
	case HINT:
	    title.setText("Show hint");
	    description.setText("Show the clue sentence of the answer!");
	    price.setText(Integer.toString(_currentQuizz.getCreditToUnlockHint()));
	    break;
	case RANDOM:
	    title.setText("Show used letters");
	    description.setText("Show all the letters of the answer (unordered)");
	    price.setText(Integer.toString(_currentQuizz.getCreditToUnlockRandom()));
	    break;
	case HALF:
	    title.setText("Some letters in order");
	    description.setText("Show some letters of the answer (ordered)");
	    price.setText(Integer.toString(_currentQuizz.getCreditToUnlockHalf()));
	    break;
	default:
	    title.setText("Show full response");
	    description.setText("Answer the question for you!");
	    price.setText(Integer.toString(_currentQuizz.getCreditToUnlockResponse()));
	    break;
	}

    }

    public void unlockPack(View view) {
	int cost = 0;
	switch (_hintType) {
	case HINT:
	    cost = _currentQuizz.getCreditToUnlockHint();
	    break;
	case RANDOM:
	    cost = _currentQuizz.getCreditToUnlockRandom();
	    break;
	case HALF:
	    cost = _currentQuizz.getCreditToUnlockHalf();
	    break;
	default:
	    cost = _currentQuizz.getCreditToUnlockResponse();
	    break;
	}

	if (_currentUser.getCredit() >= cost) {
	    Intent intent = new Intent(HintDialogActivity.this, ShowHintActivity.class);
	    intent.putExtra(ShowHintActivity.MESSAGE_HINT_TYPE, _hintType);
	    intent.putExtra(ShowHintActivity.MESSAGE_QUIZZ_PLAYER, _currentQuizz);
	    startActivity(intent);
	    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

	    // TODO Décrémenter le crédit de l'utilisateur
	    // TODO Updater l'objet play
	} else {
	    Intent intent = new Intent(HintDialogActivity.this, StoreActivity.class);
	    startActivity(intent);
	}
	this.finish();
    }

    public void cancel(View view) {
	this.finish();
    }

}
