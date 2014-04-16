package com.compo.android.app;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.service.QuizzService;
import com.compo.android.app.utils.FontEnum;
import com.compo.android.app.utils.UserFactory;

public class HintDialogActivity extends Activity {
	public final static String MESSAGE_HINT_TYPE = "com.compo.android.app.HintDetailsActivity.MESSAGE1";
	public final static String MESSAGE_QUIZZ_PLAYER = "com.compo.android.app.HintDetailsActivity.MESSAGE2";
	public final static String MESSAGE_PLAY = "com.compo.android.app.HintDetailsActivity.MESSAGE4";

	private static Typeface _font;

	private Play _currentPlay;
	private QuizzPlayer _currentQuizz;
	private HintTypeEnum _hintType;
	private User _currentUser;

	// TODO A externaliser
	private static final String PRICE_SUFFIX = " credits";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hint_dialog);

		if (_font == null) {
			_font = Typeface.createFromAsset(getAssets(), FontEnum.DIALOG_TITLE.getName());
		}

		_currentUser = UserFactory.getInstance().getUser(this);

		Intent intent = getIntent();
		_currentPlay = (Play) intent.getSerializableExtra(MESSAGE_PLAY);
		_currentQuizz = (QuizzPlayer) intent.getSerializableExtra(MESSAGE_QUIZZ_PLAYER);
		_hintType = (HintTypeEnum) intent.getSerializableExtra(MESSAGE_HINT_TYPE);

		TextView title = (TextView) findViewById(R.id.hint_title);
		TextView description = (TextView) findViewById(R.id.hint_description);
		TextView price = (TextView) findViewById(R.id.hint_price);

		title.setTypeface(_font);
		description.setTypeface(_font);
		price.setTypeface(_font);

		switch (_hintType) {
		case HINT:
			title.setText(getString(R.string.title_help_hint));
			description.setText(getString(R.string.label_desc_help_hint));
			price.setText(Integer.toString(_currentQuizz.getCreditToUnlockHint()) + PRICE_SUFFIX);
			break;
		case RANDOM:
			title.setText(getString(R.string.title_help_random));
			description.setText(getString(R.string.label_desc_help_random));
			price.setText(Integer.toString(_currentQuizz.getCreditToUnlockRandom()) + PRICE_SUFFIX);
			break;
		case HALF:
			title.setText(getString(R.string.title_help_half));
			description.setText(getString(R.string.label_desc_help_half));
			price.setText(Integer.toString(_currentQuizz.getCreditToUnlockHalf()) + PRICE_SUFFIX);
			break;
		default:
			title.setText(getString(R.string.title_help_full));
			description.setText(getString(R.string.label_desc_help_full));
			price.setText(Integer.toString(_currentQuizz.getCreditToUnlockResponse()) + PRICE_SUFFIX);
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
			Intent intent = new Intent(HintDialogActivity.this, HintDisplayActivity.class);
			intent.putExtra(HintDisplayActivity.MESSAGE_HINT_TYPE, _hintType);
			intent.putExtra(HintDisplayActivity.MESSAGE_QUIZZ_PLAYER, _currentQuizz);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

			// TODO Decrementer le credit de l'utilisateur

			// Update de l'objet play
			if (_currentPlay == null) {
				_currentPlay = new Play();
				_currentPlay.setQuizzId(_currentQuizz.getId());
				_currentPlay.setUserId(_currentUser.getId());
			}
			switch (_hintType) {
			case HINT:
				_currentPlay.setUnlockHint(true);
				break;
			case RANDOM:
				_currentPlay.setUnlockRandom(true);
				break;
			case HALF:
				_currentPlay.setUnlock50Percent(true);
				break;
			default:
				_currentPlay.setUnlockResponse(true);
				break;
			}
			_currentPlay.setDateTime(new Date());

			QuizzService service = new QuizzService(HintDialogActivity.this);
			_currentPlay = service.savePlay(_currentPlay);

			// Pour repercuter la maj sur l'ecran ResponseActivity
			Intent newIntent = new Intent();
			newIntent.putExtra(ResponseActivity.EXTRA_MESSAGE_RESULT, _currentPlay);
			setResult(RESULT_OK, newIntent);

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
