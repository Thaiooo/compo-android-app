package com.compo.android.app;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.compo.android.app.dao.DataBaseHelper;

public class MainActivity extends Activity {

    private static Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Button buttonPlay = (Button) findViewById(R.id.button_play);
	Button buttonSetting = (Button) findViewById(R.id.button_setting);

	if (font == null) {
	    font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}
	buttonPlay.setTypeface(font);
	buttonSetting.setTypeface(font);

	DataBaseHelper myDbHelper = new DataBaseHelper(this);

	try {
	    myDbHelper.createDataBase();
	} catch (IOException ioe) {
	    throw new Error("Unable to create database");
	}

	try {
	    myDbHelper.openDataBase();
	} catch (SQLException sqle) {
	    throw sqle;
	}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	return false;
    }

    public void play(View view) {
	Intent intent = new Intent(MainActivity.this, SelectPackActivity.class);
	startActivity(intent);

    }

    public void setting(View view) {
	Intent intent = new Intent(MainActivity.this, SettingActivity.class);
	startActivity(intent);
    }

}
