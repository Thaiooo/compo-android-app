package com.compo.android.app;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.compo.android.app.dao.DataBaseHelper;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    private static Typeface _font;
    private static Typeface _fontTitle;
    private TextView _userCredit;
    private TextView _userPoint;
    private TextView _appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);
	_appTitle = (TextView) findViewById(R.id.app_titre);

	Button buttonPlay = (Button) findViewById(R.id.button_play);
	Button buttonStore = (Button) findViewById(R.id.button_store);
	Button buttonSetting = (Button) findViewById(R.id.button_setting);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}
	buttonPlay.setTypeface(_font);
	buttonStore.setTypeface(_font);
	buttonSetting.setTypeface(_font);

	if (_fontTitle == null) {
	    // Eraser.ttf
	    // HVD_Peace.ttf
	    _fontTitle = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
	}
	_appTitle.setTypeface(_fontTitle);

	DataBaseHelper myDbHelper = new DataBaseHelper(this);

	try {
	    myDbHelper.createDataBase();
	} catch (IOException ioe) {
	    throw new Error("Unable to create database");
	}

	new LoadUserTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	return false;
    }

    public void play(View view) {
	Intent intent = new Intent(MainActivity.this, SelectThemeActivity.class);
	startActivity(intent);
	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void store(View view) {
	Intent intent = new Intent(MainActivity.this, StoreActivity.class);
	startActivity(intent);
    }

    public void setting(View view) {
	Intent intent = new Intent(MainActivity.this, SettingActivity.class);
	startActivity(intent);
    }

    private class LoadUserTask extends AsyncTask<Void, Void, User> {
	@Override
	protected User doInBackground(Void... params) {
	    User u = UserFactory.getInstance().getUser(MainActivity.this);
	    if (u == null) {
		Log.e(TAG, "No user found");
		// TODO: Il faut afficher une alerte?
	    }
	    return u;
	}

	@Override
	protected void onPostExecute(User anUser) {
	    _userCredit.setText(anUser.getCredit() + "");
	    _userPoint.setText(anUser.getPoint() + " pts");
	}
    }

}
