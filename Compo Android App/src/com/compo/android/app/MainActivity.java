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
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.dao.DataBaseHelper;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.FontEnum;
import com.compo.android.app.utils.UserFactory;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    private static Typeface _font;
    private TextView _userCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	ImageView imgBack = (ImageView) findViewById(R.id.image_back);
	imgBack.setVisibility(View.INVISIBLE);
	ImageView imgHome = (ImageView) findViewById(R.id.image_home);
	imgHome.setVisibility(View.INVISIBLE);

	_userCredit = (TextView) findViewById(R.id.user_credit);

	Button buttonPlay = (Button) findViewById(R.id.button_play);
	Button buttonStore = (Button) findViewById(R.id.button_store);
	Button buttonSetting = (Button) findViewById(R.id.button_setting);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), FontEnum.LUCKY_PENNY.getName());
	}
	buttonPlay.setTypeface(_font);
	buttonStore.setTypeface(_font);
	buttonSetting.setTypeface(_font);

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

    @Override
    protected void onRestart() {
	super.onRestart();
	new LoadUserTask().execute();
    }

    public void play(View view) {
	Intent intent = new Intent(MainActivity.this, SelectThemeActivity.class);
	startActivity(intent);
	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void store(View view) {
	Intent intent = new Intent(MainActivity.this, StoreActivity.class);
	startActivityForResult(intent, 1);
    }

    public void setting(View view) {
	Intent intent = new Intent(MainActivity.this, SettingActivity.class);
	startActivityForResult(intent, 1);
    }

    public void tutorial(View view) {
	Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
	startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	new LoadUserTask().execute();
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
	    _userCredit.setText(Integer.toString(anUser.getCredit()));
	}
    }

}
