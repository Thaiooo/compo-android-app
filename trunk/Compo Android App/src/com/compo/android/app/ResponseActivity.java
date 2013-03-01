package com.compo.android.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ResponseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}
