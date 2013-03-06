package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button buttonPlay = (Button) findViewById(R.id.button_play);
		Button buttonSetting = (Button) findViewById(R.id.button_setting);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"MyLuckyPenny.ttf");
		buttonPlay.setTypeface(font);
		buttonSetting.setTypeface(font);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public void play(View view) {
		Intent intent = new Intent(MainActivity.this, SelectGameActivity.class);
		startActivity(intent);

	}

	public void setting(View view) {
		Intent intent = new Intent(MainActivity.this, SettingActivity.class);
		startActivity(intent);
	}

}
