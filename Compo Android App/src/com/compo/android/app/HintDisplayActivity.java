package com.compo.android.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.utils.FontEnum;

public class HintDisplayActivity extends Activity {
    public final static String MESSAGE_HINT_TYPE = "com.compo.android.app.HintActivity.MESSAGE1";
    public final static String MESSAGE_QUIZZ_PLAYER = "com.compo.android.app.HintActivity.MESSAGE2";

    private QuizzPlayer _currentQuizz;
    private static Typeface _font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_hint_display);

	Intent intent = getIntent();
	_currentQuizz = (QuizzPlayer) intent.getSerializableExtra(MESSAGE_QUIZZ_PLAYER);
	HintTypeEnum hintType = (HintTypeEnum) intent.getSerializableExtra(MESSAGE_HINT_TYPE);

	TextView hintTitle = (TextView) findViewById(R.id.hint_title);
	TextView hintValue = (TextView) findViewById(R.id.hint_value);
	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), FontEnum.ERASER.getName());
	}
	hintValue.setTypeface(_font);
	hintTitle.setTypeface(_font);

	String playerName = _currentQuizz.getPlayer().getName();

	switch (hintType) {
	case HINT:
	    hintTitle.setText("Hint");
	    hintValue.setText(_currentQuizz.getHint());
	    break;
	case RANDOM:
	    hintTitle.setText("Used Letters");
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
	    hintValue.setText(randomLetters.toString());
	    break;
	case HALF:
	    hintTitle.setText("50/50");
	    StringBuffer s = new StringBuffer();
	    for (int i = 0; i < playerName.length(); i++) {
		// Que pour les emplacements impaire
		if (i % 2 == 1) {
		    s.append(playerName.charAt(i));
		} else {
		    s.append("?");
		}
	    }

	    hintValue.setText(s.toString());
	    break;
	default:
	    hintTitle.setText("Response");
	    hintValue.setText(playerName);
	    break;
	}

    }

    public void cancel(View view) {
	finish();
    }

}
