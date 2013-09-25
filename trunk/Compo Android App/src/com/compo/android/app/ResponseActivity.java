package com.compo.android.app;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.compo.android.app.dao.PackDao;
import com.compo.android.app.dao.PackProgressDao;
import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class ResponseActivity extends AbstractLSEFragmentActivity {
    // private static final String TAG = ResponseActivity.class.getName();

    public static final String EXTRA_MESSAGE_QUIZZ_SIZE = "com.compo.android.app.ResponseActivity.MESSAGE.QUIZZ.SIZE";
    public static final String EXTRA_MESSAGE_RESPONSE_SIZE = "com.compo.android.app.ResponseActivity.MESSAGE.RESPONSE.SIZE";
    public static final String EXTRA_MESSAGE_QUIZZ = "com.compo.android.app.ResponseActivity.MESSAGE.QUIZZ";
    public static final String EXTRA_MESSAGE_PLAY = "com.compo.android.app.ResponseActivity.MESSAGE.PLAY";
    public static final String EXTRA_MESSAGE_MATCH_ID = "com.compo.android.app.ResponseActivity.MESSAGE.MATCH.ID";

    public static final String EXTRA_MESSAGE_RESULT = "com.compo.android.app.ResponseActivity.MESSAGE.RESULT";
    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

    private static Typeface _font;
    private EditText _edit;
    private ImageView _matching;
    private Pack _currentPack;
    private QuizzPlayer _currentQuizz;
    private Play _currentPlay;
    private Long _matchId;
    private Integer _nbCorrectResponse;
    private Integer _nbQuizz;
    private User _currentUser;
    private Button _buttonHint;
    private Button _buttonRandom;
    private Button _buttonHalf;
    private Button _buttonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_response);

	Intent intent = getIntent();
	_currentQuizz = (QuizzPlayer) intent.getSerializableExtra(EXTRA_MESSAGE_QUIZZ);
	_currentPlay = (Play) intent.getSerializableExtra(EXTRA_MESSAGE_PLAY);
	_matchId = (Long) intent.getSerializableExtra(EXTRA_MESSAGE_MATCH_ID);
	_nbCorrectResponse = (Integer) intent.getSerializableExtra(EXTRA_MESSAGE_RESPONSE_SIZE);
	_nbQuizz = (Integer) intent.getSerializableExtra(EXTRA_MESSAGE_QUIZZ_SIZE);

	_currentUser = UserFactory.getInstance().getUser(this);

	PackDao packDao = new PackDao(this);
	_currentPack = packDao.findPackByMatch(_matchId);

	_edit = (EditText) findViewById(R.id.edit_responseMMM);
	if (_currentPlay != null) {
	    _edit.setText(_currentPlay.getResponse());
	    if (StringUtils.isNotBlank(_currentPlay.getResponse())) {
		_edit.setSelection(_currentPlay.getResponse().length());
	    }
	}

	_matching = (ImageView) findViewById(R.id.matching_image);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
	}
	_edit.setTypeface(_font);

	Button check = (Button) findViewById(R.id.button_check);
	check.setTypeface(_font);

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
	newIntent.putExtra(QuizzActivity.EXTRA_MESSAGE_RESULT, _currentPlay);
	setResult(RESULT_CANCELED, newIntent);
	finish();
    }

    @Override
    public void onBackPressed() {
	Intent newIntent = new Intent();
	newIntent.putExtra(QuizzActivity.EXTRA_MESSAGE_RESULT, _currentPlay);
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

	PlayDao dao = new PlayDao(ResponseActivity.this);
	Intent newIntent = new Intent();
	System.out.println("NB QUIZZ=====>" + _nbQuizz);
	System.out.println("NB RESPONSE=====>" + _nbCorrectResponse);

	if (percent == 100) {
	    // =========================================
	    // TODO Ceci dans etre dans une transaction
	    // =========================================

	    // MAJ du user
	    User user = UserFactory.getInstance().getUser(ResponseActivity.this);
	    user.setCredit(user.getCredit() + _currentQuizz.getEarnCredit());
	    UserFactory.getInstance().updateUser(ResponseActivity.this);

	    // MAJ du play
	    if (_currentPlay == null) {
		_currentPlay = new Play();
		_currentPlay.setQuizzId(_currentQuizz.getId());
		_currentPlay.setUserId(UserFactory.getInstance().getUser(ResponseActivity.this).getId());
	    }
	    _currentPlay.setResponse(response);
	    _currentPlay.setDateTime(new Date());

	    if (_currentPlay.getId() == 0) {
		dao.add(_currentPlay);
	    } else {
		dao.update(_currentPlay);
	    }

	    // MAJ du pack progress
	    if (_nbQuizz == _nbCorrectResponse + 1) {
		PackProgressDao packProgressDao = new PackProgressDao(ResponseActivity.this);
		PackProgress progress = packProgressDao.find(_currentPack);
		if (progress == null) {
		    progress = new PackProgress();
		    progress.setNumberOfSuccessMatch(1);
		    progress.setPack(_currentPack);
		    packProgressDao.add(progress);
		} else {
		    progress.setNumberOfSuccessMatch(progress.getNumberOfSuccessMatch() + 1);
		    packProgressDao.update(progress);
		}
	    }

	    newIntent.putExtra(QuizzActivity.EXTRA_MESSAGE_RESULT, _currentPlay);
	    setResult(RESULT_OK, newIntent);
	    finish();
	} else {
	    if (percent >= 50) {
		_matching.setImageResource(R.drawable.yellow_card);
	    } else {
		_matching.setImageResource(R.drawable.red_card);
	    }

	    if (_currentPlay == null) {
		_currentPlay = new Play();
		_currentPlay.setDateTime(new Date());
		_currentPlay.setQuizzId(_currentQuizz.getId());
		_currentPlay.setUserId(UserFactory.getInstance().getUser(ResponseActivity.this).getId());
		_currentPlay.setResponse(response);
		dao.add(_currentPlay);

	    } else {
		_currentPlay.setResponse(response);
		_currentPlay.setDateTime(new Date());
		dao.update(_currentPlay);
	    }

	    newIntent.putExtra(QuizzActivity.EXTRA_MESSAGE_RESULT, _currentPlay);
	    setResult(RESULT_CANCELED, newIntent);
	}

    }

    private void displayHintDialog(HintTypeEnum aType) {
	Intent intent = new Intent(ResponseActivity.this, HintDialogActivity.class);
	intent.putExtra(HintDialogActivity.MESSAGE_HINT_TYPE, aType);
	intent.putExtra(HintDialogActivity.MESSAGE_QUIZZ_PLAYER, _currentQuizz);
	intent.putExtra(HintDialogActivity.MESSAGE_PLAY, _currentPlay);
	startActivityForResult(intent, EXTRA_MESSAGE_REQUEST_CODE);
	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void displayHint(HintTypeEnum aType) {
	Intent intent = new Intent(ResponseActivity.this, ShowHintActivity.class);
	intent.putExtra(ShowHintActivity.MESSAGE_HINT_TYPE, aType);
	intent.putExtra(ShowHintActivity.MESSAGE_QUIZZ_PLAYER, _currentQuizz);
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
    }

}
