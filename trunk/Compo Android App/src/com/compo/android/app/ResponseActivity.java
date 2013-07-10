package com.compo.android.app;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class ResponseActivity extends Activity {
    // private static final String TAG = ResponseActivity.class.getName();

    public static final String EXTRA_MESSAGE_QUIZZ = "com.compo.android.app.ResponseActivity.MESSAGE.QUIZZ";
    public static final String EXTRA_MESSAGE_PLAY = "com.compo.android.app.ResponseActivity.MESSAGE.PLAY";

    private static Typeface _font;
    private EditText edit;
    private EditText _matching;
    private QuizzPlayer _currentQuizz;
    private Play _currentPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_response);

	Intent intent = getIntent();
	_currentQuizz = (QuizzPlayer) intent.getSerializableExtra(EXTRA_MESSAGE_QUIZZ);
	_currentPlay = (Play) intent.getSerializableExtra(EXTRA_MESSAGE_PLAY);

	edit = (EditText) findViewById(R.id.edit_response);
	if (_currentPlay != null) {
	    edit.setText(_currentPlay.getResponse());
	}

	_matching = (EditText) findViewById(R.id.matching);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
	}
	edit.setTypeface(_font);
    }

    public void check(View view) {
	String response = edit.getText().toString().trim();
	response = StringUtils.lowerCase(response);

	String playerName = _currentQuizz.getPlayer().getName();
	playerName = StringUtils.lowerCase(playerName);

	double distance = StringUtils.getLevenshteinDistance(response, playerName);
	double percent = 100 - (distance / (double) response.length() * 100);

	PlayDao dao = new PlayDao(ResponseActivity.this);
	Intent newIntent = new Intent();
	if (percent == 100) {
	    // =========================================
	    // TODO Ceci dans etre dans une transaction
	    // =========================================

	    // MAJ du user
	    User user = UserFactory.getInstance().getUser(ResponseActivity.this);
	    user.setPoint(user.getPoint() + 1);
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

	    newIntent.putExtra(QuizzActivity.EXTRA_MESSAGE_RESULT, _currentPlay);
	    setResult(RESULT_OK, newIntent);
	    finish();
	} else {
	    _matching.setText(Integer.toString((int) percent) + " %");

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
}
