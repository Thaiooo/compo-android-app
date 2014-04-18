package com.compo.android.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.service.QuizzService;
import com.compo.android.app.utils.FontEnum;
import com.compo.android.app.utils.UserFactory;

public class HintDialogActivity extends Activity {

	public static final int REQUEST_CODE_DISPLAY_HINT = 1;

	public final static String MESSAGE_HINT_TYPE = "com.compo.android.app.HintDialogActivity.MESSAGE1";
	public final static String MESSAGE_QUIZZ_PLAYER = "com.compo.android.app.HintDialogActivity.MESSAGE2";
	public final static String MESSAGE_PLAY = "com.compo.android.app.HintDialogActivity.MESSAGE4";

	private static Typeface _font;

	private Play _currentPlay;
	private QuizzPlayer _currentQuizz;
	private HintTypeEnum _hintType;
	private User _currentUser;
	private TextView _hintContents;
	private TextView _price;
	private ImageView _imgCoins;
	private Button _btnSubmit;

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

		_btnSubmit = (Button) findViewById(R.id.button_submit);
		_price = (TextView) findViewById(R.id.hint_price);
		_hintContents = (TextView) findViewById(R.id.hint_contents);
		_imgCoins = (ImageView) findViewById(R.id.img_coin);

		title.setTypeface(_font);
		description.setTypeface(_font);
		_price.setTypeface(_font);

		switch (_hintType) {
		case HINT:
			title.setText(getString(R.string.title_help_hint));
			description.setText(getString(R.string.label_desc_help_hint));

			if (_currentPlay != null && _currentPlay.isUnlockHint()) {
				displayHint();
			} else {
				displayPrice(_currentQuizz.getCreditToUnlockHint());
			}
			break;

		case RANDOM:
			title.setText(getString(R.string.title_help_random));
			description.setText(getString(R.string.label_desc_help_random));

			if (_currentPlay != null && _currentPlay.isUnlockRandom()) {
				displayHint();
			} else {
				displayPrice(_currentQuizz.getCreditToUnlockRandom());
			}
			break;

		case HALF:
			title.setText(getString(R.string.title_help_half));
			description.setText(getString(R.string.label_desc_help_half));

			if (_currentPlay != null && _currentPlay.isUnlock50Percent()) {
				displayHint();
			} else {
				displayPrice(_currentQuizz.getCreditToUnlockHalf());
			}
			break;

		default:
			title.setText(getString(R.string.title_help_full));
			description.setText(getString(R.string.label_desc_help_full));

			if (_currentPlay != null && _currentPlay.isUnlockResponse()) {
				displayHint();
			} else {
				displayPrice(_currentQuizz.getCreditToUnlockResponse());
			}
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

			displayHint();

			QuizzService service = new QuizzService(HintDialogActivity.this);
			_currentPlay = service.savePlay(_currentPlay);

		} else {
			Intent intent = new Intent(HintDialogActivity.this, StoreActivity.class);
			startActivity(intent);
			this.finish();
		}
	}

	public void cancel(View view) {
		Intent newIntent = new Intent();
		newIntent.putExtra(ResponseActivity.EXTRA_MESSAGE_RESULT, _currentPlay);

		if (_hintType == HintTypeEnum.RESPONSE && _currentPlay != null && _currentPlay.isUnlockResponse()) {
			String hint = getHint(_hintType);
			newIntent.putExtra(ResponseActivity.EXTRA_MESSAGE_HINT_RESULT, hint);
		}

		setResult(RESULT_OK, newIntent);

		this.finish();
	}

	private void displayPrice(int aPrice) {
		_hintContents.setVisibility(View.INVISIBLE);
		_imgCoins.setVisibility(View.VISIBLE);
		_price.setVisibility(View.VISIBLE);
		_price.setText(Integer.toString(aPrice) + PRICE_SUFFIX);
	}

	private void displayHint() {
		String hint = getHint(_hintType);
		_hintContents.setVisibility(View.VISIBLE);
		_hintContents.setText(hint);
		_imgCoins.setVisibility(View.INVISIBLE);
		_price.setVisibility(View.INVISIBLE);
		_btnSubmit.setVisibility(View.GONE);
	}

	private String getHint(HintTypeEnum hintType) {
		String hint;

		String playerName = _currentQuizz.getPlayer().getName();

		switch (hintType) {
		case HINT:
			hint = _currentQuizz.getHint();
			break;

		case RANDOM:
			char[] tab = playerName.toCharArray();
			List<Character> list = new ArrayList<Character>();
			for (int i = 0; i < tab.length; i++) {
				list.add(tab[i]);
			}

			StringBuffer randomLetters = new StringBuffer();
			Random r = new Random();
			int size = list.size();
			while (size > 0) {
				int index = r.nextInt(size);
				randomLetters.append(list.get(index));
				list.remove(index);
				size = list.size();
			}
			hint = randomLetters.toString();
			break;

		case HALF:
			StringBuffer s = new StringBuffer();
			for (int i = 0; i < playerName.length(); i++) {
				// Que pour les emplacements impaire
				if (i % 2 == 1) {
					s.append(playerName.charAt(i));
				} else {
					s.append("?");
				}
			}
			hint = s.toString();
			break;

		default:
			hint = playerName;
			break;

		}

		return hint;
	}

}
