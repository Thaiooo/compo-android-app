package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
