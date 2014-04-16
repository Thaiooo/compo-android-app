package com.compo.android.app;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.service.QuizzService;
import com.compo.android.app.service.ServiceResultSave;
import com.compo.android.app.utils.FontEnum;
import com.compo.android.app.utils.UserFactory;

public class ResponseActivity extends AbstractLSEFragmentActivity {
	// private static final String TAG = ResponseActivity.class.getName();

	public static final String EXTRA_MESSAGE_QUIZZ = "com.compo.android.app.ResponseActivity.MESSAGE.QUIZZ";
	public static final String EXTRA_MESSAGE_PLAY = "com.compo.android.app.ResponseActivity.MESSAGE.PLAY";
	public static final String EXTRA_MESSAGE_HOME_COLOR = "com.compo.android.app.ResponseActivity.MESSAGE.HOME.COLOR";

	public static final String EXTRA_MESSAGE_RESULT = "com.compo.android.app.ResponseActivity.MESSAGE.RESULT";
	public static final int EXTRA_MESSAGE_REQUEST_CODE_HINT_DIALOG = 1;
	public static final int EXTRA_MESSAGE_REQUEST_CODE_SUCCESS_DIALOG = 2;

	private static final String JERSEY_PREFIX = "jersey_";
	private static final String SHORT_PREFIX = "short_";
	private static final String SOCK_PREFIX = "sock_";

	private static Typeface _fontButton;
	private static Typeface _fontEditText;
	private EditText _edit;
	private ImageView _matching;
	private ImageView _jersey;
	private ImageView _short;
	private ImageView _sock;
	private QuizzPlayer _currentQuizz;
	private Play _currentPlay;
	private Button _buttonHint;
	private Button _buttonRandom;
	private Button _buttonHalf;
	private Button _buttonResponse;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_response;
	}

	@Override
	protected void createDatabse() {
		// RAS
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		Intent intent = getIntent();
		_currentQuizz = (QuizzPlayer) intent.getSerializableExtra(EXTRA_MESSAGE_QUIZZ);
		_currentPlay = (Play) intent.getSerializableExtra(EXTRA_MESSAGE_PLAY);
		boolean isHomeColor = (Boolean) intent.getSerializableExtra(EXTRA_MESSAGE_HOME_COLOR);

		new LoadUserTask().execute();

		_edit = (EditText) findViewById(R.id.edit_response);
		if (_currentPlay != null) {
			_edit.setText(_currentPlay.getResponse());
			if (StringUtils.isNotBlank(_currentPlay.getResponse())) {
				_edit.setSelection(_currentPlay.getResponse().length());
			}
		}

		int idJersey;
		int idShort;
		int idSock;
		if (isHomeColor) {
			idJersey = getResources().getIdentifier(
					JERSEY_PREFIX + _currentQuizz.getTeam().getHomeJerseyColor().name().toLowerCase(Locale.US),
					"drawable", this.getPackageName());
			idShort = getResources().getIdentifier(
					SHORT_PREFIX + _currentQuizz.getTeam().getHomeShortColor().name().toLowerCase(Locale.US),
					"drawable", this.getPackageName());
			idSock = getResources().getIdentifier(
					SOCK_PREFIX + _currentQuizz.getTeam().getHomeSockColor().name().toLowerCase(Locale.US), "drawable",
					this.getPackageName());
		} else {
			idJersey = getResources().getIdentifier(
					JERSEY_PREFIX + _currentQuizz.getTeam().getAwayJerseyColor().name().toLowerCase(Locale.US),
					"drawable", this.getPackageName());
			idShort = getResources().getIdentifier(
					SHORT_PREFIX + _currentQuizz.getTeam().getAwayShortColor().name().toLowerCase(Locale.US),
					"drawable", this.getPackageName());
			idSock = getResources().getIdentifier(
					SOCK_PREFIX + _currentQuizz.getTeam().getAwaySockColor().name().toLowerCase(Locale.US), "drawable",
					this.getPackageName());
		}

		_jersey = (ImageView) findViewById(R.id.player_jersey);
		_jersey.setImageResource(idJersey);

		_short = (ImageView) findViewById(R.id.player_short);
		_short.setImageResource(idShort);

		_sock = (ImageView) findViewById(R.id.player_sock);
		_sock.setImageResource(idSock);

		_matching = (ImageView) findViewById(R.id.matching_image);

		if (_fontEditText == null) {
			_fontEditText = Typeface.createFromAsset(getAssets(), FontEnum.RESPONSE_CONTENT.getName());
		}
		_edit.setTypeface(_fontEditText);

		if (_fontButton == null) {
			_fontButton = Typeface.createFromAsset(getAssets(), FontEnum.BUTTON.getName());
		}
		Button check = (Button) findViewById(R.id.button_check);
		check.setTypeface(_fontButton);

		_buttonHint = (Button) findViewById(R.id.button_hint);
		_buttonRandom = (Button) findViewById(R.id.button_random);
		_buttonHalf = (Button) findViewById(R.id.button_50);
		_buttonResponse = (Button) findViewById(R.id.button_100);
		if (_currentPlay != null) {
			if (_currentPlay.isUnlockHint()) {
				_buttonHint.setText("unlock");
			}
			if (_currentPlay.isUnlockRandom()) {
				_buttonRandom.setText("unlock");
			}
			if (_currentPlay.isUnlock50Percent()) {
				_buttonHalf.setText("*");
			}
			if (_currentPlay.isUnlockResponse()) {
				_buttonResponse.setText("*");
			}
		}

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.responseLayout);
		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
			}
		});
	}

	@Override
	public void back(View view) {
		Intent newIntent = new Intent();
		newIntent.putExtra(MatchActivity.RESULT_MESSAGE, _currentPlay);
		setResult(RESULT_CANCELED, newIntent);
		finish();
	}

	@Override
	public void onBackPressed() {
		Intent newIntent = new Intent();
		newIntent.putExtra(MatchActivity.RESULT_MESSAGE, _currentPlay);
		setResult(RESULT_CANCELED, newIntent);
		finish();
	}

	public void check(View view) {
		String response = _edit.getText().toString().trim();
		response = StringUtils.lowerCase(response);

		String playerName = _currentQuizz.getPlayer().getName();
		playerName = StringUtils.lowerCase(playerName);

		double distance = StringUtils.getLevenshteinDistance(response, playerName);
		double percent = 100 - (distance / (double) response.length() * 100);

		QuizzService quizzService = new QuizzService(ResponseActivity.this);
		if (percent == 100) {
			ServiceResultSave result = quizzService.saveSuccessResponse(_currentQuizz, _currentPlay, response);
			_currentPlay = result.getPlay();

			Intent successDialogIntent = new Intent(ResponseActivity.this, SuccessDialogActivity.class);
			successDialogIntent.putExtra(SuccessDialogActivity.MESSAGE_QUIZZ_PLAYER, _currentQuizz);
			if (result.isAllQuizzSuccess()) {
				successDialogIntent.putExtra(SuccessDialogActivity.MESSAGE_DISPLAY_NEXT, true);
			} else {
				successDialogIntent.putExtra(SuccessDialogActivity.MESSAGE_DISPLAY_NEXT, false);
			}
			startActivityForResult(successDialogIntent, EXTRA_MESSAGE_REQUEST_CODE_SUCCESS_DIALOG);

		} else {
			if (percent >= 50) {
				_matching.setImageResource(R.drawable.yellow_card);
			} else {
				_matching.setImageResource(R.drawable.red_card);
			}

			_currentPlay = quizzService.saveInccorectResponse(_currentQuizz, _currentPlay, response);
			Intent returnIntent = new Intent();
			returnIntent.putExtra(MatchActivity.RESULT_MESSAGE, _currentPlay);
			setResult(RESULT_CANCELED, returnIntent);
		}

	}

	private void displayHintDialog(HintTypeEnum aType) {
		Intent intent = new Intent(ResponseActivity.this, HintDialogActivity.class);
		intent.putExtra(HintDialogActivity.MESSAGE_HINT_TYPE, aType);
		intent.putExtra(HintDialogActivity.MESSAGE_QUIZZ_PLAYER, _currentQuizz);
		intent.putExtra(HintDialogActivity.MESSAGE_PLAY, _currentPlay);
		startActivityForResult(intent, EXTRA_MESSAGE_REQUEST_CODE_HINT_DIALOG);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	private void displayHint(HintTypeEnum aType) {
		Intent intent = new Intent(ResponseActivity.this, HintDisplayActivity.class);
		intent.putExtra(HintDisplayActivity.MESSAGE_HINT_TYPE, aType);
		intent.putExtra(HintDisplayActivity.MESSAGE_QUIZZ_PLAYER, _currentQuizz);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void openHint(View view) {
		if (_currentPlay != null && _currentPlay.isUnlockHint()) {
			displayHint(HintTypeEnum.HINT);
		} else {
			displayHintDialog(HintTypeEnum.HINT);
		}
	}

	public void openRandom(View view) {
		if (_currentPlay != null && _currentPlay.isUnlockRandom()) {
			displayHint(HintTypeEnum.RANDOM);
		} else {
			displayHintDialog(HintTypeEnum.RANDOM);
		}
	}

	public void open50Percent(View view) {
		if (_currentPlay != null && _currentPlay.isUnlock50Percent()) {
			displayHint(HintTypeEnum.HALF);
		} else {
			displayHintDialog(HintTypeEnum.HALF);
		}
	}

	public void openResponse(View view) {
		if (_currentPlay != null && _currentPlay.isUnlockResponse()) {
			displayHint(HintTypeEnum.RESPONSE);
		} else {
			displayHintDialog(HintTypeEnum.RESPONSE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == EXTRA_MESSAGE_REQUEST_CODE_HINT_DIALOG) {
			// =========================================================================================================
			// Cas du retour de la page Hint dialog
			// =========================================================================================================

			if (data == null) {
				return;
			}
			_currentPlay = (Play) data.getSerializableExtra(ResponseActivity.EXTRA_MESSAGE_RESULT);
			if (_currentPlay != null) {
				if (_currentPlay.isUnlockHint()) {
					_buttonHint.setText("unlock");
				}
				if (_currentPlay.isUnlockRandom()) {
					_buttonRandom.setText("unlock");
				}
				if (_currentPlay.isUnlock50Percent()) {
					_buttonHalf.setText("*");
				}
				if (_currentPlay.isUnlockResponse()) {
					_buttonResponse.setText("*");
				}
			}
		} else {
			System.out.println("===> ici");
			// =========================================================================================================
			// Cas du retour de la page Success dialog
			// =========================================================================================================

			Intent returnIntent = new Intent();
			returnIntent.putExtra(MatchActivity.RESULT_MESSAGE, _currentPlay);

			// 2 cas possibles:
			if (resultCode == RESULT_OK) {
				// Cas 1: OK ou Back
				setResult(RESULT_OK, returnIntent);
			} else {
				// Cas 2: Suivant
				setResult(RESULT_FIRST_USER, returnIntent);
			}

			finish();
		}

	}

	private class LoadUserTask extends AsyncTask<Void, Void, User> {
		@Override
		protected User doInBackground(Void... params) {
			User u = UserFactory.getInstance().getUser(ResponseActivity.this);
			if (u == null) {
				// TODO: Il faut afficher une alerte?
			}
			return u;
		}

		@Override
		protected void onPostExecute(User anUser) {
			// _userCredit.setText(Integer.toString(anUser.getCredit()));
		}
	}

}
